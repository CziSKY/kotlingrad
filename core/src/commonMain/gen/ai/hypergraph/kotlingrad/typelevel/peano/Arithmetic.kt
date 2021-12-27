// This file was generated by Shipshape
@file:Suppress("UNUSED_PARAMETER", "UNCHECKED_CAST")

package ai.hypergraph.kotlingrad.typelevel.peano

import kotlin.jvm.JvmName

open class S<X: S<X>>(val x: S<X>?)
object O: S<O>(null)
fun S<*>.toInt(i: Int = 0): Int = x?.toInt(i + 1) ?: i

val S1 = S(O)
val S2 = S1.plus1()
val S3 = S2.plus1()
val S4 = S3.plus1()
val S5 = S4.plus1()
val S6 = S5.plus1()
val S7 = S6.plus1()
val S8 = S7.plus1()
val S9 = S8.plus1()
val S10 = S9.plus1()
val S11 = S10.plus1()
val S12 = S11.plus1()
val S13 = S12.plus1()
val S14 = S13.plus1()
val S15 = S14.plus1()
val S16 = S15.plus1()

fun <W: S<*>, X: S<W>> W.plus1(): X = S(this) as X
fun <W: S<*>, X: S<W>> X.minus1(): W = x as W
fun <W: S<*>, X: S<S<W>>> W.plus2(): X = plus1().plus1()
fun <W: S<*>, X: S<S<W>>> X.minus2(): W = minus1().minus1()
fun <W: S<*>, X: S<S<S<W>>>> W.plus3(): X = plus2().plus1()
fun <W: S<*>, X: S<S<S<W>>>> X.minus3(): W = minus2().minus1()
fun <W: S<*>, X: S<S<S<S<W>>>>> W.plus4(): X = plus3().plus1()
fun <W: S<*>, X: S<S<S<S<W>>>>> X.minus4(): W = minus3().minus1()
fun <W: S<*>, X: S<S<S<S<S<W>>>>>> W.plus5(): X = plus4().plus1()
fun <W: S<*>, X: S<S<S<S<S<W>>>>>> X.minus5(): W = minus4().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<W>>>>>>> W.plus6(): X = plus5().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<W>>>>>>> X.minus6(): W = minus5().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<W>>>>>>>> W.plus7(): X = plus6().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<W>>>>>>>> X.minus7(): W = minus6().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<W>>>>>>>>> W.plus8(): X = plus7().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<W>>>>>>>>> X.minus8(): W = minus7().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<W>>>>>>>>>> W.plus9(): X = plus8().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<W>>>>>>>>>> X.minus9(): W = minus8().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>> W.plus10(): X = plus9().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>> X.minus10(): W = minus9().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>> W.plus11(): X = plus10().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>> X.minus11(): W = minus10().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>> W.plus12(): X = plus11().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>> X.minus12(): W = minus11().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>> W.plus13(): X = plus12().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>> X.minus13(): W = minus12().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>> W.plus14(): X = plus13().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>> X.minus14(): W = minus13().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>>> W.plus15(): X = plus14().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>>> X.minus15(): W = minus14().minus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>>>> W.plus16(): X = plus15().plus1()
fun <W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>>>> X.minus16(): W = minus15().minus1()

@JvmName("n+0") operator fun <W: S<*>> W.plus(x: O) = this
@JvmName("0+n") operator fun <X: S<*>> O.plus(x: X) = x
@JvmName("n+1") operator fun <W: S<*>, X: S<O>> W.plus(x: X) = plus1()
@JvmName("1+n") operator fun <W: S<*>, X: S<O>> X.plus(x: W) = x.plus1()
@JvmName("n-1") operator fun <W: S<*>, X: S<W>, Y: S<O>> X.minus(y: Y) = minus1()
@JvmName("n÷1") operator fun <W: S<*>, X: S<O>> W.div(x: X) = this
@JvmName("n*1") operator fun <W: S<*>, X: S<O>> W.times(x: X) = this
@JvmName("1*n") operator fun <W: S<O>, X: S<*>> W.times(x: X) = x
@JvmName("n*0") operator fun <W: S<*>> W.times(x: O) = O
@JvmName("0*n") operator fun <X: S<*>> O.times(x: X) = O

@JvmName("n+2") operator fun <W: S<S<O>>, X: S<*>> X.plus(x: W) = plus2()
@JvmName("n+3") operator fun <W: S<S<S<O>>>, X: S<*>> X.plus(x: W) = plus3()
@JvmName("n+4") operator fun <W: S<S<S<S<O>>>>, X: S<*>> X.plus(x: W) = plus4()
@JvmName("n+5") operator fun <W: S<S<S<S<S<O>>>>>, X: S<*>> X.plus(x: W) = plus5()
@JvmName("n+6") operator fun <W: S<S<S<S<S<S<O>>>>>>, X: S<*>> X.plus(x: W) = plus6()
@JvmName("n+7") operator fun <W: S<S<S<S<S<S<S<O>>>>>>>, X: S<*>> X.plus(x: W) = plus7()
@JvmName("n+8") operator fun <W: S<S<S<S<S<S<S<S<O>>>>>>>>, X: S<*>> X.plus(x: W) = plus8()
@JvmName("n+9") operator fun <W: S<S<S<S<S<S<S<S<S<O>>>>>>>>>, X: S<*>> X.plus(x: W) = plus9()
@JvmName("n+10") operator fun <W: S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus10()
@JvmName("n+11") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus11()
@JvmName("n+12") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus12()
@JvmName("n+13") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus13()
@JvmName("n+14") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus14()
@JvmName("n+15") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus15()
@JvmName("n+16") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<*>> X.plus(x: W) = plus16()

@JvmName("n-2") operator fun <V: S<S<O>>, W: S<*>, X: S<S<W>>> X.minus(v: V) = minus2()
@JvmName("n-3") operator fun <V: S<S<S<O>>>, W: S<*>, X: S<S<S<W>>>> X.minus(v: V) = minus3()
@JvmName("n-4") operator fun <V: S<S<S<S<O>>>>, W: S<*>, X: S<S<S<S<W>>>>> X.minus(v: V) = minus4()
@JvmName("n-5") operator fun <V: S<S<S<S<S<O>>>>>, W: S<*>, X: S<S<S<S<S<W>>>>>> X.minus(v: V) = minus5()
@JvmName("n-6") operator fun <V: S<S<S<S<S<S<O>>>>>>, W: S<*>, X: S<S<S<S<S<S<W>>>>>>> X.minus(v: V) = minus6()
@JvmName("n-7") operator fun <V: S<S<S<S<S<S<S<O>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<W>>>>>>>> X.minus(v: V) = minus7()
@JvmName("n-8") operator fun <V: S<S<S<S<S<S<S<S<O>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<W>>>>>>>>> X.minus(v: V) = minus8()
@JvmName("n-9") operator fun <V: S<S<S<S<S<S<S<S<S<O>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<W>>>>>>>>>> X.minus(v: V) = minus9()
@JvmName("n-10") operator fun <V: S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>> X.minus(v: V) = minus10()
@JvmName("n-11") operator fun <V: S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>> X.minus(v: V) = minus11()
@JvmName("n-12") operator fun <V: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>> X.minus(v: V) = minus12()
@JvmName("n-13") operator fun <V: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>> X.minus(v: V) = minus13()
@JvmName("n-14") operator fun <V: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>> X.minus(v: V) = minus14()
@JvmName("n-15") operator fun <V: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>>> X.minus(v: V) = minus15()
@JvmName("n-16") operator fun <V: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, W: S<*>, X: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<W>>>>>>>>>>>>>>>>> X.minus(v: V) = minus16()

@JvmName("2*2") operator fun <W: S<S<O>>, X: S<S<O>>> W.times(x: X) = S4
@JvmName("2*3") operator fun <W: S<S<O>>, X: S<S<S<O>>>> W.times(x: X) = S6
@JvmName("2*4") operator fun <W: S<S<O>>, X: S<S<S<S<O>>>>> W.times(x: X) = S8
@JvmName("2*5") operator fun <W: S<S<O>>, X: S<S<S<S<S<O>>>>>> W.times(x: X) = S10
@JvmName("2*6") operator fun <W: S<S<O>>, X: S<S<S<S<S<S<O>>>>>>> W.times(x: X) = S12
@JvmName("2*7") operator fun <W: S<S<O>>, X: S<S<S<S<S<S<S<O>>>>>>>> W.times(x: X) = S14
@JvmName("2*8") operator fun <W: S<S<O>>, X: S<S<S<S<S<S<S<S<O>>>>>>>>> W.times(x: X) = S16
@JvmName("3*2") operator fun <W: S<S<S<O>>>, X: S<S<O>>> W.times(x: X) = S6
@JvmName("3*3") operator fun <W: S<S<S<O>>>, X: S<S<S<O>>>> W.times(x: X) = S9
@JvmName("3*4") operator fun <W: S<S<S<O>>>, X: S<S<S<S<O>>>>> W.times(x: X) = S12
@JvmName("3*5") operator fun <W: S<S<S<O>>>, X: S<S<S<S<S<O>>>>>> W.times(x: X) = S15
@JvmName("4*2") operator fun <W: S<S<S<S<O>>>>, X: S<S<O>>> W.times(x: X) = S8
@JvmName("4÷2") operator fun <W: S<S<S<S<O>>>>, X: S<S<O>>> W.div(x: X) = S2
@JvmName("4*3") operator fun <W: S<S<S<S<O>>>>, X: S<S<S<O>>>> W.times(x: X) = S12
@JvmName("4*4") operator fun <W: S<S<S<S<O>>>>, X: S<S<S<S<O>>>>> W.times(x: X) = S16
@JvmName("5*2") operator fun <W: S<S<S<S<S<O>>>>>, X: S<S<O>>> W.times(x: X) = S10
@JvmName("5÷2") operator fun <W: S<S<S<S<S<O>>>>>, X: S<S<O>>> W.div(x: X) = S2
@JvmName("5*3") operator fun <W: S<S<S<S<S<O>>>>>, X: S<S<S<O>>>> W.times(x: X) = S15
@JvmName("6*2") operator fun <W: S<S<S<S<S<S<O>>>>>>, X: S<S<O>>> W.times(x: X) = S12
@JvmName("6÷2") operator fun <W: S<S<S<S<S<S<O>>>>>>, X: S<S<O>>> W.div(x: X) = S3
@JvmName("6÷3") operator fun <W: S<S<S<S<S<S<O>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S2
@JvmName("7*2") operator fun <W: S<S<S<S<S<S<S<O>>>>>>>, X: S<S<O>>> W.times(x: X) = S14
@JvmName("7÷2") operator fun <W: S<S<S<S<S<S<S<O>>>>>>>, X: S<S<O>>> W.div(x: X) = S3
@JvmName("7÷3") operator fun <W: S<S<S<S<S<S<S<O>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S2
@JvmName("8*2") operator fun <W: S<S<S<S<S<S<S<S<O>>>>>>>>, X: S<S<O>>> W.times(x: X) = S16
@JvmName("8÷2") operator fun <W: S<S<S<S<S<S<S<S<O>>>>>>>>, X: S<S<O>>> W.div(x: X) = S4
@JvmName("8÷3") operator fun <W: S<S<S<S<S<S<S<S<O>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S2
@JvmName("8÷4") operator fun <W: S<S<S<S<S<S<S<S<O>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S2
@JvmName("9÷2") operator fun <W: S<S<S<S<S<S<S<S<S<O>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S4
@JvmName("9÷3") operator fun <W: S<S<S<S<S<S<S<S<S<O>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S3
@JvmName("9÷4") operator fun <W: S<S<S<S<S<S<S<S<S<O>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S2
@JvmName("10÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S5
@JvmName("10÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S3
@JvmName("10÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S2
@JvmName("10÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S2
@JvmName("11÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S5
@JvmName("11÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S3
@JvmName("11÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S2
@JvmName("11÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S2
@JvmName("12÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S6
@JvmName("12÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S4
@JvmName("12÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S3
@JvmName("12÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S2
@JvmName("12÷6") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>, X: S<S<S<S<S<S<O>>>>>>> W.div(x: X) = S2
@JvmName("13÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S6
@JvmName("13÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S4
@JvmName("13÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S3
@JvmName("13÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S2
@JvmName("13÷6") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>, X: S<S<S<S<S<S<O>>>>>>> W.div(x: X) = S2
@JvmName("14÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S7
@JvmName("14÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S4
@JvmName("14÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S3
@JvmName("14÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S2
@JvmName("14÷6") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<S<S<S<S<S<O>>>>>>> W.div(x: X) = S2
@JvmName("14÷7") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>, X: S<S<S<S<S<S<S<O>>>>>>>> W.div(x: X) = S2
@JvmName("15÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S7
@JvmName("15÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S5
@JvmName("15÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S3
@JvmName("15÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S3
@JvmName("15÷6") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<S<S<S<S<S<O>>>>>>> W.div(x: X) = S2
@JvmName("15÷7") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>, X: S<S<S<S<S<S<S<O>>>>>>>> W.div(x: X) = S2
@JvmName("16÷2") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<O>>> W.div(x: X) = S8
@JvmName("16÷3") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<S<O>>>> W.div(x: X) = S5
@JvmName("16÷4") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<S<S<O>>>>> W.div(x: X) = S4
@JvmName("16÷5") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<S<S<S<O>>>>>> W.div(x: X) = S3
@JvmName("16÷6") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<S<S<S<S<O>>>>>>> W.div(x: X) = S2
@JvmName("16÷7") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<S<S<S<S<S<O>>>>>>>> W.div(x: X) = S2
@JvmName("16÷8") operator fun <W: S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<S<O>>>>>>>>>>>>>>>>, X: S<S<S<S<S<S<S<S<O>>>>>>>>> W.div(x: X) = S2