package edu.umontreal.kotlingrad.samples

import edu.umontreal.kotlingrad.experimental.*
import edu.umontreal.kotlingrad.experimental.DoublePrecision.invoke
import edu.umontreal.kotlingrad.experimental.DoublePrecision.magnitude
import edu.umontreal.kotlingrad.experimental.DoublePrecision.pow
import edu.umontreal.kotlingrad.utils.step
import org.nield.kotlinstatistics.standardDeviation
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import kotlin.random.Random
import kotlin.streams.toList

fun main() = with(DoublePrecision) {
  val lossHistoryCumulative = mutableListOf<List<Pair<Int, Double>>>()
  val models = mutableListOf<Vec<DReal, D30>>()
  val surrogates = mutableListOf<SFun<DReal>>()
  for (expNum in 0..100) {
    val oracle = ExpressionGenerator.scaledRandomBiTree(5, maxX, maxY)
    val model = learnExpression(lossHistoryCumulative, oracle)
    models += model
    surrogates += testPolynomial(model, oracle)

    if (expNum % 10 == 0) {
      ObjectOutputStream(FileOutputStream("checkpoint.hist")).use { it.writeObject(lossHistoryCumulative) }
      ObjectOutputStream(FileOutputStream("model.hist")).use { it.writeObject(model) }
      ObjectOutputStream(FileOutputStream("surrogate.hist")).use { it.writeObject(surrogates) }
    }
  }
}

fun DoublePrecision.testPolynomial(weights: Vec<DReal, D30>, targetEq: SFun<DReal>): SFun<DReal> {
  val model = decodePolynomial(weights)
  val trueError = (model - targetEq) pow 2
  val numSteps = 100
  val budget = batchSize.i * 100
  val trueErrors = List(budget) { rand.nextDouble(-maxX, maxX) }.map { Pair(it, trueError(it).toDouble()) }.toMap()
  val maxError = trueErrors.entries.maxBy { it.value }
  val avgError = trueErrors.values.average().also { println("Mean true error: $it") }
  val stdError = trueErrors.values.standardDeviation().also { println("StdDev true error: $it") }

  val chunked: List<Pair<Vec<DReal, D30>, Vec<DReal, D30>>> = trueErrors.entries.chunked(batchSize.i)
    .map { chunk -> Pair(Vec(batchSize) { chunk[it].key }, Vec(batchSize) { chunk[it].value }) }
  val surrogateLoss = attack(weights, chunked)
//  plotVsOracle(trueError, surrogateLoss)
  val adErrors = sampleAndAscend(surrogateLoss)

  println("StdDevs from Mean, Random Efficiency, Adversarial Efficiency")
  for (i in 0..numSteps) {
    val stdDevs = 3.0 * i / numSteps
    val threshold = avgError + stdDevs * stdError

    val seffPG = trueErrors.values.parallelStream().filter { threshold <= it }.count().toDouble() / budget
    val seffAD = adErrors.parallelStream().filter { threshold <= trueError(it).toDouble() }.count().toDouble() / budget

    println("${stdDevs}, $seffPG, $seffAD")
  }
  return surrogateLoss
}

private fun DoublePrecision.sampleAndAscend(surrogateLoss: SFun<DReal>) =
  (0..100).toList().parallelStream().flatMap {
    var proposals = Vec(paramSize) { sampleInputs(it) }
    val batchLoss = proposals.map { surrogateLoss(it) }.magnitude()
    val dx = batchLoss.d(x).d(x)

    var momentum = Vec(paramSize) { 0.0 }
    for (step in 0..1000) {
      val dxs = proposals.map { wrap(dx(it).toDouble()) }
      momentum = (beta * momentum + (1 - beta) * dxs)()
      proposals = (proposals + alpha * momentum)()
    }

    proposals.contents.map { it.toDouble() }.stream()
  }.toList()

private fun DoublePrecision.attack(
  weights: Vec<DReal, D30>, chunked: List<Pair<Vec<DReal, D30>, Vec<DReal, D30>>>
): SFun<DReal> {
  val model = decodePolynomial(weights)
  var newWeights = weights
  var update = Vec(paramSize) { 0.0 }

  chunked.forEachIndexed { i, chunk ->
    val batchInputs = arrayOf(xBatchIn to chunk.first, label to chunk.second)
    val batchLoss = squaredLoss(*batchInputs)

    val weightGrads = batchLoss.d(theta)(theta to newWeights)
    update = (beta * update + (1 - beta) * weightGrads)()
    newWeights = (newWeights - alpha * update)()
  }

  val adModel = decodePolynomial(newWeights)
  val surrogateLoss = (model - adModel) pow 2

  return surrogateLoss
}

val rand = Random(2L)
const val maxX = 1.0
const val maxY = 1.0
const val alpha = 0.01 // Step size
const val beta = 0.9   // Momentum
const val totalEpochs = 20
const val epochSize = 10
const val testSplit = 0.2 // Hold out test
val batchSize = D30
val paramSize = D30
val theta = DoublePrecision.Var30("theta")
val xBatchIn = DoublePrecision.Var30("xBatchIn")
val label = DoublePrecision.Var30("y")
val encodedInput = xBatchIn.sVars.vMap { row -> DoublePrecision.Vec(paramSize) { col -> row pow (col + 1) } }
val pred = encodedInput * theta
val squaredLoss = (pred - label).magnitude()
val interval: Double = (maxX - maxX * testSplit) / batchSize.i

// Samples inputs randomly, but spaced evenly
// -maxX |----Train Split----|----Test Split----|----Train Split----| +maxX
fun sampleInputs(i: Int) = (if(i % 2 == 0) -1 else 1) *
  (testSplit * maxX + rand.nextDouble(i * interval, (i + 2) * interval))

/* https://en.wikipedia.org/wiki/Polynomial_regression#Matrix_form_and_calculation_of_estimates
 *  __  __    __                      __  __  __
 * | y_1 |   | 1  x_1  x_1^2 ... x_1^m | | w_1 |
 * | y_2 |   | 1  x_2  x_2^2 ... x_2^m | | w_2 |
 * | y_3 | = | 1  x_3  x_3^2 ... x_3^m | | w_3 |
 * |  :  |   | :   :     :   ...   :   | |  :  |
 * | y_n |   | 1  x_n  x_n^2 ... x_n^m | | w_n |
 * |__ __|   |__                     __| |__ __|
 */

fun DoublePrecision.decodePolynomial(weights: Vec<DReal, D30>) =
  Vec(paramSize) { x pow (it + 1) } dot weights

private fun DoublePrecision.learnExpression(
  lossHistoryCumulative: MutableList<List<Pair<Int, Double>>>,
  targetEq: SFun<DReal>): Vec<DReal, D30> {
  var weightsNow = Vec(paramSize) { rand.nextDouble(-1.0, 1.0) }

  var totalLoss = 0.0
  var totalTime = 0L
  var momentum = Vec(paramSize) { 0.0 }
  val lossHistory = mutableListOf<Pair<Int, Double>>()
  var weightMap: Array<Pair<Fun<DReal>, Any>>

  for (epochs in 1..(epochSize * totalEpochs)) {
    totalTime += System.nanoTime()
    val xInputs = Vec(batchSize, ::sampleInputs)
    val targets = xInputs.map { targetEq(it) }

    val batchInputs = arrayOf(xBatchIn to xInputs, label to targets())
    val batchLoss = squaredLoss(*batchInputs)

    weightMap = arrayOf(theta to weightsNow)

    totalLoss += batchLoss(*weightMap).toDouble() / xInputs.size
    val weightGrads = batchLoss.d(theta)

    momentum = (beta * momentum + (1 - beta) * weightGrads)(*weightMap)()
    weightsNow = (weightsNow - alpha * momentum)()

    totalTime -= System.nanoTime()
    if (epochs % epochSize == 0) {
//      plotVsOracle(targetEq, decodePolynomial(weightsNow))
//      println("Average loss at ${epochs / epochSize} / $totalEpochs epochs: ${totalLoss / epochSize}")
//      println("Average time: " + -totalTime.toDouble() / (epochSize * 1000000) + "ms")
//      println("Weights: $weightsNow")
      lossHistory += epochs / epochSize to totalLoss / epochSize
//      plotLoss(lossHistory)
      totalLoss = 0.0
      totalTime = 0L
    }
  }

  plotLoss(lossHistory)
//    println("Final weights: $weightsNow")
  lossHistoryCumulative += lossHistory

  return weightsNow
}

private fun plotLoss(lossHistory: MutableList<Pair<Int, Double>>) {
  mapOf("Epochs" to lossHistory.map { it.first },
    "Average Loss" to lossHistory.map { it.second }
  ).plot2D("Training Loss", "polynomial_regression_loss.svg")
}

private fun DoublePrecision.plotVsOracle(oracle: SFun<DReal>, model: SFun<DReal>) {
  val t = ((-1.0..1.0) step 0.01).toList()
  mapOf("x" to t,
    "y" to t.map { oracle(it).toDouble() },
    "z" to t.map { model(it).toDouble() }
  ).plot2D("Oracle vs. Model", "compare_outputs.svg")
}