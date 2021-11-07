package ai.hypergraph.shipshape

import org.intellij.lang.annotations.Language


@Language("kt")
fun genTypeLevelVariables() = """
// This file was generated by Shipshape

package ai.hypergraph.kotlingrad.typelevel

import ai.hypergraph.kaliningraph.circuits.*
import ai.hypergraph.kotlingrad.typelevel.*

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
    " N.$op(e:" + genEx().let { "$it) = $it" } +
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
          .let { if (it == "OOO") "call<N>" else "inv<N, $it>" } +
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