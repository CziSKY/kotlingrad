package edu.umontreal.kotlingrad.samples

import edu.umontreal.kotlingrad.experimental.*
import kotlin.random.Random

@Suppress("NonAsciiCharacters")
class MLP<T: SFun<T>>(
  val x: Var<T> = Var("x"),
  val y: Var<T> = Var("y"),
  val p1v: VVar<T, D3> = VVar("p1v", D3),
  val p2v: MVar<T, D3, D3> = MVar("p2v", D3, D3),
  val p3v: VVar<T, D3> = VVar("p3v", D3)
) {
  operator fun invoke(p1: VFun<T, D3>, p2: MFun<T, D3, D3>, p3: VFun<T, D3>) =
    asFun()(p1v to p1)(p2v to p2)(p3v to p3)

  fun asFun(): SFun<T> {
    val layer1 = layer(p1v * x)
    val layer2 = layer(p2v * layer1)
    val output = layer2 dot p3v
    val lossFun = (output - y) pow Two()
    return lossFun
  }

  private fun layer(x: VFun<T, D3>): VFun<T, D3> = x.map { sigmoid(it) }
}

fun <T: SFun<T>> sigmoid(x: SFun<T>) = One<T>() / (One<T>() + E<T>().pow(-x))

fun main() = with(DoublePrecision) {
  val rand = java.util.Random()
  var w1: VFun<DReal, D3> = Vec(D3) { rand.nextDouble() }
  var w2: MFun<DReal, D3, D3> = Mat(D3, D3) { rand.nextDouble() }
  var w3: VFun<DReal, D3> = Vec(D3) { rand.nextDouble() }

  val oracle = { it: Double -> it * it }
  val drawSample = { Random.nextDouble().let { Pair(it, oracle(it)) } }
  val mlp = MLP<DReal>()
  val mlpf = mlp.asFun()

  var epochs = 1
  var totalLoss = 0.0
  val α = wrap(0.01)

  do {
    val (X, Y) = drawSample()
    val fixInputs = mlpf(mlp.x to X, mlp.y to Y)(*constants)
    val loss = fixInputs(mlp.p1v to w1)(mlp.p2v to w2)(mlp.p3v to w3)(*constants)

    totalLoss += loss.toDouble()

    val dw1 = loss.d(mlp.p1v)
    val dw2 = loss.d(mlp.p2v)
    val dw3 = loss.d(mlp.p3v)

    val t1 = dw1(mlp.p1v to w1)(mlp.p2v to w2)(mlp.p3v to w3)(*constants)
    val t2 = dw2(mlp.p1v to w1)(mlp.p2v to w2)(mlp.p3v to w3)(*constants)
    val t3 = dw3(mlp.p1v to w1)(mlp.p2v to w2)(mlp.p3v to w3)(*constants)

    w1 += α * t1
    w2 += α * t2
    w3 += α * t3

    if (epochs % 100 == 0) {
      println("Average loss at ${epochs / 10} epochs: ${totalLoss / 100}")
      totalLoss = 0.0
    }
  } while (epochs++ < 1000)
}
