package ai.hypergraph.shipshape

import org.intellij.lang.annotations.Language

val range = 2..16

@Language("kt")
fun genPeanoArithmetic() = """
  // This file was generated by Shipshape
package ai.hypergraph.kotlingrad.typelevel.peano

import kotlin.jvm.JvmName

open class S<X: S<X>>(val x: S<X>?)
object O: S<O>(null)
fun S<*>.toInt(i: Int = 0): Int = x?.toInt(i + 1) ?: i

${genAliases()}

${genConsts()}

${genArithmetic()}
""".trimMargin()

fun genAliases(): String =
  "val S1 = S(O)\n" +
  range.joinToString("\n") { "val S$it = S${it-1}.plus1()" }

fun genConsts(): String =
  """
    @Suppress("UNCHECKED_CAST")
    fun <W: S<*>, X: S<W>> W.plus1(): X = S(this) as X
    @Suppress("UNCHECKED_CAST")
    fun <W: S<*>, X: S<W>> X.minus1(): W = x as W
  """.trimIndent() +
  range.joinToString( "\n", "\n" ) {
    """
      fun <W: S<*>, X: ${genChurchNat(it, "W")}> W.plus$it(): X = plus${it-1}().plus1()
      fun <W: S<*>, X: ${genChurchNat(it, "W")}> X.minus$it(): W = minus${it-1}().minus1()
    """.trimIndent()
  }

fun genArithmetic(
  ops: Map<Pair<String, String>, (Int, Int) -> Int> = mapOf(
    "plus" to "+" to { a, b -> a + b },
    "times" to "*" to { a, b -> a * b },
    "minus" to "-" to { a, b -> a - b },
    "div" to "÷" to { a, b -> a / b },
  )
) =
  genSpecials() +
    (range * range * ops.entries).filter { (a, b, c) -> c.value(a, b) in range }
      .joinToString("\n", "\n") { (a, b, c) ->
        val sa = genChurchNat(a, "O")
        val sb = genChurchNat(b, "O")
        val res = c.value(a, b)
//        val sres = genChurchNat(res, "O")
        val op = c.key.second
        val name = c.key.first
//        "@JvmName(\"$a$op$b\") operator fun <W: $sa, X: $sb, Y: $sres> W.$name(x: X): Y = $name$b()"
        "@JvmName(\"$a$op$b\") operator fun <W: $sa, X: $sb> W.$name(x: X) = S${res}"
      }

operator fun IntRange.times(s: IntRange) =
  flatMap { l -> s.map { r -> l to r }.toSet() }.toSet()

operator fun <T, Y, Z> Set<Pair<T, Y>>.times(s: Set<Z>): Set<Triple<T, Y, Z>> =
  flatMap { (l, ll) -> s.map { r -> Triple(l, ll, r) }.toSet() }.toSet()

fun genSpecials() =
  """
    @JvmName("n+0") operator fun <W: S<*>, X: S<O>> W.plus(x: X) = this
    @JvmName("0+n") operator fun <W: S<O>, X: S<*>> W.plus(x: X) = x
    @JvmName("n÷1") operator fun <W: S<*>, X: S<O>> W.div(x: X) = this
    @JvmName("n*1") operator fun <W: S<*>, X: S<O>> W.times(x: X) = this
    @JvmName("1*n") operator fun <W: S<O>, X: S<*>> W.times(x: X) = x
    @JvmName("n*0") operator fun <W: S<*>> W.times(x: O) = this
    @JvmName("0*n") operator fun <X: S<*>> O.times(x: X) = x
  """.trimIndent()

tailrec fun genChurchNat(i: Int, prev: String = "K"): String =
  if (i == 0) prev else genChurchNat(i - 1, "S<$prev>")