package ai.hypergraph.shipshape

import org.intellij.lang.annotations.Language

@Language("kt")
val preamble = """
// This file was generated by Shipshape
@file:Suppress("ObjectPropertyName", "ClassName", "PropertyName", "NonAsciiCharacters", "FunctionName")
package ai.hypergraph.kotlingrad.typelevel.chinese

import kotlin.jvm.JvmName

sealed class 数<丁, 己: 数<丁, 己>>(open val 中: 丁? = null) {
  val 零 get() = 零(this as 己)
  val 一 get() = 一(this as 己)
  val 二 get() = 二(this as 己)
  val 三 get() = 三(this as 己)
  val 四 get() = 四(this as 己)
  val 五 get() = 五(this as 己)
  val 六 get() = 六(this as 己)
  val 七 get() = 七(this as 己)
  val 八 get() = 八(this as 己)
  val 九 get() = 九(this as 己)

  override fun equals(other: Any?) = toString() == other.toString()
  override fun hashCode() = this::class.hashCode() + 中.hashCode()
  override fun toString() = (中 ?: "").toString() + this::class.simpleName
}

open class 零<丁>(override val 中: 丁? = null) : 数<丁, 零<丁>>(中) { companion object: 零<无>() }
open class 一<丁>(override val 中: 丁? = null) : 数<丁, 一<丁>>(中) { companion object: 一<无>() }
open class 二<丁>(override val 中: 丁? = null) : 数<丁, 二<丁>>(中) { companion object: 二<无>() }
open class 三<丁>(override val 中: 丁? = null) : 数<丁, 三<丁>>(中) { companion object: 三<无>() }
open class 四<丁>(override val 中: 丁? = null) : 数<丁, 四<丁>>(中) { companion object: 四<无>() }
open class 五<丁>(override val 中: 丁? = null) : 数<丁, 五<丁>>(中) { companion object: 五<无>() }
open class 六<丁>(override val 中: 丁? = null) : 数<丁, 六<丁>>(中) { companion object: 六<无>() }
open class 七<丁>(override val 中: 丁? = null) : 数<丁, 七<丁>>(中) { companion object: 七<无>() }
open class 八<丁>(override val 中: 丁? = null) : 数<丁, 八<丁>>(中) { companion object: 八<无>() }
open class 九<丁>(override val 中: 丁? = null) : 数<丁, 九<丁>>(中) { companion object: 九<无>() }

object 无: 数<无, 无>(null)

val 十: 十型 = 一.零
val 十一: 十一型 = 一.一
val 十二: 十二型 = 一.二
val 十三: 十三型 = 一.三
val 十四: 十四型 = 一.四
val 十五: 十五型 = 一.五
val 十六: 十六型 = 一.六
val 十七: 十七型 = 一.七
val 十八: 十八型 = 一.八
val 十九: 十九型 = 一.九
val 二十: 二十型 = 二.零
val 二十一: 二十一型 = 二.一

val z2a: Map<String, String> = mapOf(
  "零" to "0",
  "一" to "1",
  "二" to "2",
  "三" to "3",
  "四" to "4",
  "五" to "5",
  "六" to "6",
  "七" to "7",
  "八" to "8",
  "九" to "9",
  "十" to "",
  "百" to "",
)

val a2z = z2a.entries.associate { (k, v) -> v to k }

// TODO: https://cs.github.com/?scopeName=All+repos&scope=&q=%E9%9B%B6+%E4%B8%80+%E4%BA%8C+%E4%B8%89+%E5%9B%9B+%E4%BA%94+%E5%85%AD+%E4%B8%83+%E5%85%AB+%E4%B9%9D+Arabic++language%3AJava
fun String.toArabic() = map { if (this in z2a) z2a[this]!! else this }.joinToString("")
fun String.toChinese() = map { if (this in a2z) a2z[this]!! else this }.joinToString("")

typealias 一型 = 一<无>
typealias 二型 = 二<无>
typealias 三型 = 三<无>
typealias 四型 = 四<无>
typealias 五型 = 五<无>
typealias 六型 = 六<无>
typealias 七型 = 七<无>
typealias 八型 = 八<无>
typealias 九型 = 九<无>
typealias 十型 = 零<一<无>>
typealias 十一型 = 一<一<无>>
typealias 十二型 = 二<一<无>>
typealias 十三型 = 三<一<无>>
typealias 十四型 = 四<一<无>>
typealias 十五型 = 五<一<无>>
typealias 十六型 = 六<一<无>>
typealias 十七型 = 七<一<无>>
typealias 十八型 = 八<一<无>>
typealias 十九型 = 九<一<无>>
typealias 二十型 = 零<二<无>>
typealias 二十一型 = 一<二<无>>

tailrec fun 数<*, *>?.toInt(i: Int = 0, j: Int = 1): Int =
  if (this == null) i else (中 as 数<*, *>?).toInt(i + this::class.simpleName!!.toArabic().toInt() * j, 10 * j)
"""

fun genAbacus() =
"""
$preamble 
${genChinese()}
""".let { it + "\n// Total lines: ${it.lines().size + 1}" }

fun main() {
  println(genAbacus())
}

fun genChinese(): String =
  ((0..99) * (1..9)).flatMap { (l, r) ->
      val (a, b, c) = Triple(l.toString(), r.toString(), (l + r).toString())
      listOf(TLFun(a, "+", b, c), TLFun(c, "-", b, a))
    }
    .toSortedSet(compareBy<TLFun> { it.op }
      .thenBy { it.right.toInt() }
      .thenBy { it.left.length }
      .thenBy { it.left })
//    .also { println(it) }
    .joinToString("\n", "\n", "\n")
    .lines().distinct()
    .joinToString("\n", "\n", "\n") +
  (0..99)
    .map { i -> i.nontrivialDivisors(99) { i % it == 0 && it > 1 && it != 10 } }
    .flatten().let {
      val eqs = it.flatMap { (a, b, c) ->
        val (a, b, c) = Triple( "$a", "$b", "$c")
        listOf(TLFun(a, "/", b, c), TLFun(c, "*", b, a))
      }
      eqs.joinToString("\n")
    }

class TLFun(val left: String, val op: String, val right: String, val result: String) {
  val l2r = if (op in listOf("/", "*") || left.length != result.length) "无$left" to "无$result"
  else left.zip(result).mapNotNull { (ai, ci) -> if (ai == ci) null else (ai to ci) }
    .fold("丁" to "丁") { acc, it -> (acc.first + it.first) to (acc.second + it.second) }

  val funName = op.a2z().codify()
  val jvmName: String = (if(l2r.first == "无") "口" else "") + l2r.first.a2z() + funName + right.a2z()
  val recType = l2r.first.a2z().codify()
  val typeParam = if("<丁>" in recType) "<丁>" else ""
  val argType = right.a2z().codify("无")
  val resType = l2r.second.a2z().codify()
  val initial = if ("无" in recType) "" else l2r.first.drop(1).indices.joinToString("!!.") { "中" }
  val constructor = l2r.second.drop(1).a2z().codify(initial, '(', ')')

//  override fun hashCode() = toString().hashCode()
//  override fun equals(other: Any?) = toString() == other.toString()
//  override fun compareTo(other: TLFun) = comparator.compare(this, other)

  @Language("kt")
  override fun toString() =
    """@JvmName("$jvmName") infix fun $typeParam $recType.$funName(甲: $argType): $resType = $constructor"""
}

val z2a: Map<String, String> = mapOf(
//  "0" to "0", "1" to "1", "2" to "2", "3" to "3", "4" to "4",
//  "5" to "5", "6" to "6", "7" to "7", "8" to "8", "9" to "9",
//  "+" to "+", "-" to "-", "/" to "/", "*" to "*",
  "零" to "0", "一" to "1", "二" to "2", "三" to "3", "四" to "4",
  "五" to "5", "六" to "6", "七" to "7", "八" to "8", "九" to "9",
  "十" to "", "百" to "", "千" to "",
  "加" to "+", "减" to "-", "除" to "/", "乘" to "*"
)

val a2z = z2a.entries.associate { (k, v) -> v to k }

fun String.codify(initial: String = "", lb: Char = '<', rb: Char = '>') =
  fold(initial) { acc, it -> if(acc.isEmpty() && lb != '(') "$it" else "$it$lb$acc$rb" }

fun String.a2z(skipPlaces: Boolean = true) =
  mapIndexed { i, c ->
    val t = a2z.getOrElse("$c") { "$c" }
    if (skipPlaces) t
    else when (length - i) {
      4 -> "${t}千"
      3 -> "${t}百"
      2 -> "${t}十"
      else -> t
    }
  }.joinToString("")
