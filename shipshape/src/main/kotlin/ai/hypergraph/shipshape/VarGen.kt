package ai.hypergraph.shipshape

import org.intellij.lang.annotations.Language

@Language("kt")
fun genTypeLevelVariables() = """
// This file was generated by Shipshape

package ai.hypergraph.kotlingrad.typelevel.arity

import ai.hypergraph.kaliningraph.graphs.*
import kotlin.jvm.JvmName
import kotlin.reflect.KProperty

sealed class XO // Represents either a bound or unbound variable
abstract class XX: XO() // Represents an unbound variable
abstract class OO: XO() // Represents a bound variable

interface IEx<X: IEx<X>> {
  val exs: Array<out X>
  val op: Op?
  val name: String?
  // Call a function with all free variables bound - must bind all free variables or this will throw an error
  fun <N: Number> call(vararg vrb: VrB<N>): N = (inv<N, OOO>(*vrb) as Nt<N>).value

  // Partially evaluate a function by binding only a subset of the free variables, returning another function
  fun <N: Number, Q> inv(vararg bnds: VrB<N>): Q =
          (if (exs.isEmpty()) this
          else exs.map { it.inv<N, Q>(*bnds) as X }
                  .reduce { it, acc -> apply(acc, it) }) as Q

  // Combine two Xs using op - returns a function if either is a function, otherwise returns a value
  fun apply(me: X, that: X): X
}

open class Ex<V1: XO, V2: XO, V3: XO> constructor(
        override val op: Op? = null,
        override val name: String? = null,
        override vararg val exs: Ex<*, *, *>,
): IEx<Ex<*, *, *>> {
  open operator fun getValue(n: Nothing?, property: KProperty<*>): Ex<V1, V2, V3> = Ex(op, property.name, *exs)
  override fun toString() = exs.joinToString("${'$'}op") {
    if (op in arrayOf(Ops.prod, Ops.ratio) && it.op in arrayOf(Ops.sum, Ops.sub)) "(${'$'}it)" else "${'$'}it"
  }.let { name?.run { "${'$'}name = ${'$'}it" } ?: it }

  override fun apply(me: Ex<*, *, *>, that: Ex<*, *, *>) =
    if (op == null) that else if (me is Nt<*> && that is Nt<*>)
      when (op) {
        Ops.sum -> Nt(me.value.toDouble() + that.value.toDouble())
        Ops.sub -> Nt(me.value.toDouble() - that.value.toDouble())
        Ops.prod -> Nt(me.value.toDouble() * that.value.toDouble())
        Ops.ratio -> Nt(me.value.toDouble() / that.value.toDouble())
        else -> TODO()
      } else Ex(op, null, me, that)
}

open class Nt<T: Number>(val value: T): Ex<OO, OO, OO>() {
  override fun toString() = value.toString()
}

val x by V1()
val y by V2()
val z by V3()

open class V1 internal constructor(name: String = "v1"): Vr<XX, OO, OO>(name) {
  override fun getValue(n: Nothing?, property: KProperty<*>) = V1(property.name)
}

open class V2 internal constructor(name: String = "v2"): Vr<OO, XX, OO>(name) {
  override fun getValue(n: Nothing?, property: KProperty<*>) = V2(property.name)
}

open class V3 internal constructor(name: String = "v3"): Vr<OO, OO, XX>(name) {
  override fun getValue(n: Nothing?, property: KProperty<*>) = V3(property.name)
}

class V1Bnd<N: Number> internal constructor(vr: V1, value: N): VrB<N>(vr, value)
class V2Bnd<N: Number> internal constructor(vr: V2, value: N): VrB<N>(vr, value)
class V3Bnd<N: Number> internal constructor(vr: V3, value: N): VrB<N>(vr, value)

infix fun <N: Number> V1.to(n: N) = V1Bnd(this, n)
infix fun <N: Number> V2.to(n: N) = V2Bnd(this, n)
infix fun <N: Number> V3.to(n: N) = V3Bnd(this, n)

open class VrB<N: Number>(open val vr: Vr<*, *, *>, val value: N)
sealed class Vr<R: XO, S: XO, T: XO>(override val name: String): Ex<R, S, T>() {
  override fun <T: Number, Q> inv(vararg bnds: VrB<T>): Q =
    bnds.associate { it.vr::class to it.value }
      .let { it[this::class]?.let { Nt(it) } ?: this } as Q

  override fun toString() = name
}

${genTypeAliases()}

/**
 * The following code is a type-level encoding of the 3-element graded poset.
 *
 * For combination, i.e. any arithmetical operation, where P is a constant:
 *
 *        |    P      x      y      z      xy      xz      yz      xyz
 *    ----------------------------------------------------------------
 *    P   |    P      x      y      z      xy      xz      yz      xyz
 *    x   |    x      x      xy     xz     xy      xz      xyz     xyz
 *    y   |    y      xy     y      yz     xy      xyz     yz      xyz
 *    z   |    z      xz     yz     z      xyz     xz      yz      xyz
 *    xy  |    xy     xy     xy     xyz    xy      xyz     xyz     xyz
 *    xz  |    xz     xz     xyz    xz     xyz     xz      xyz     xyz
 *    yz  |    yz     xyz    yz     yz     xyz     xyz     yz      xyz
 *    xyz |    xyz    xyz    xyz    xyz    xyz     xyz     xyz     xyz
 *
 * Can be viewed as a Hasse Diagram: https://en.wikipedia.org/wiki/Hasse_diagram
 *
 * For application/invocation, where P is a constant:
 *
 *       |     P      x      y      z      xy      xz      yz      xyz
 *   -----------------------------------------------------------------
 *   P   |     P
 *   x   |            P                    y       z               yz
 *   y   |                   P             x               z       xz
 *   z   |                          P              x       y       xy
 *   xy  |            y      x             P                       z
 *   xz  |            z             x              P               y
 *   yz  |                   z      y                      P       x
 *   xyz |            yz     xz     xy     z       y       x       P
 *   
 * TODO: Possible to reduce space complexity via a subtype machine?
 * https://arxiv.org/pdf/2109.03950.pdf
 */

${genFuns()}
${genInvokes()}
""".trimMargin()

val numVars = 3

private fun genTypeAliases() =
  allBinaryArraysOfLen(numVars).joinToString("\n", "", "\n") { bools ->
    "typealias " + bools.joinToString("") { if (it) "X" else "O" } + " = " +
      genEx(bools.map { if (it) "XX" else "OO" })
  }

private fun genFuns() =
  listOf(
    // TODO: Bind to
    "plus" to "sum",
    "minus" to "sub",
    "times" to "prod",
    "div" to "ratio"
  ).joinToString("\n") { (op, sop) -> genVarOp(op, sop) }

private fun genVarOp(op: String, sop: String) =
  allBinaryArraysOfLen(numVars).joinToString("\n", "", "\n") { bools ->
    bools.joinToString("", "@JvmName(\"$op:", "\")") { if (it) "t" else "_" } +
      bools.indices.joinToString(", ", " operator fun <", "> ") { "V$it: XO" } +
      genEx() +
      ".$op(e: " +
      bools.joinToString("") { if (it) "X" else "O" } +
      ") = " +
      genEx(bools.mapIndexed { i, b -> if (b) "XX" else "V$i" }) +
      "(Ops.$sop, null, this, e)"
  } + genConstOpEx(op, sop) + genExOpConst(op, sop)

private fun genEx(params: List<String> = (0 until numVars).map { "V$it" }) =
  "Ex" + params.joinToString(", ", "<", ">")

private fun genConstOpEx(op: String, sop: String) =
  "operator fun " +
    (0 until numVars).joinToString(", ", "<N: Number, ", ">") { "V$it: XO" } +
    " N.$op(e: " + genEx().let { "$it) = $it" } +
    "(Ops.$sop, null, Nt(this), e)\n"

private fun genExOpConst(op: String, sop: String) =
  "operator fun " +
    (0 until numVars).joinToString(", ", "<N: Number, ", "> ") { "V$it: XO" } +
    genEx().let { "$it.$op(n: N) = $it" } +
    "(Ops.$sop, null, this, Nt(n))\n"

/**
 * Generates all possible p-adic partial applications of an [numVars]-dimensional function, e.g., supports all the following invocations:
 *
 * f_abc := a + b + c
 * f(b to 1) -> f_ab
 * f(a to 1) -> f_bc
 * f(a to 1, b to 2) -> f_c
 * f(a to 1, c to 2) -> f_b
 * f(a to 1, b to 2, c to 3) -> 6
 *
 * It is possible to further reduce the space complexity with vanilla currying if we only want to support monadic partial application, i.e.:
 *
 * val g = f(a to 1)
 * val h = g(b to 1)
 * val i = h(c to 1) == f(a to 1)(b to 1)(c to 1)
 */
fun genInvokes(allFreeVariableConfigurations: Set<List<Boolean>> = allBinaryArraysOfLen(numVars).toSet() - setOf(listOf(false, false, false))) =
  allFreeVariableConfigurations.joinToString("\n") { fvConfig ->
    // Generate all subsets of the unbound variables in each free variable configuration
    val allFreeVariableSubsets: Set<Set<Int>> = fvConfig.indices.filter { fvConfig[it] }.map { it + 1 }.powerset() - setOf(emptySet())
    allFreeVariableSubsets.joinToString("\n") { fvs ->
      "@JvmName(\"i:" + fvConfig.joinToString("") { if (it) "t" else "_" } + "\") operator fun <N: Number> " +
        fvConfig.joinToString("") { if (it) "X" else "O" } + ".invoke(" +
        fvs.joinToString(", ") { "v$it: V${it}Bnd<N>" } +
        ") = " +
        fvConfig.mapIndexed { i, b -> val isBound = (i + 1) in fvs; !(!b || isBound) }.joinToString("") { if (it) "X" else "O" }
          .let { if (it == "OOO") "call" else "inv<N, $it>" } +
        fvs.joinToString(", ", "(", ")") { "v$it" }
    }
  }

fun allBinaryArraysOfLen(len: Int): List<List<Boolean>> =
  (0 until 2.shl(len - 1)).map { Integer.toBinaryString(it) }
    .map { it.padStart(len, '0').map { it != '0' } }

fun <T> Collection<T>.powerset(): Set<Set<T>> = powerset(this, setOf(setOf()))

private tailrec fun <T> powerset(left: Collection<T>, acc: Set<Set<T>>): Set<Set<T>> = when {
  left.isEmpty() -> acc
  else -> powerset(left.drop(1), acc + acc.map { it + left.first() })
}