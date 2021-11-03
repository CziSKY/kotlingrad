// This file was generated by Shipshape

package ai.hypergraph.kotlingrad.typelevel

import ai.hypergraph.kaliningraph.circuits.*
import ai.hypergraph.kotlingrad.typelevel.*

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

@JvmName("plus:___") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<OO, OO, OO>) = Ex<V0, V1, V2>(this, e, op = Ops.`+`)
@JvmName("plus:__t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<OO, OO, XX>) = Ex<V0, V1, XX>(this, e, op = Ops.`+`)
@JvmName("plus:_t_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<OO, XX, OO>) = Ex<V0, XX, V2>(this, e, op = Ops.`+`)
@JvmName("plus:_tt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<OO, XX, XX>) = Ex<V0, XX, XX>(this, e, op = Ops.`+`)
@JvmName("plus:t__") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<XX, OO, OO>) = Ex<XX, V1, V2>(this, e, op = Ops.`+`)
@JvmName("plus:t_t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<XX, OO, XX>) = Ex<XX, V1, XX>(this, e, op = Ops.`+`)
@JvmName("plus:tt_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<XX, XX, OO>) = Ex<XX, XX, V2>(this, e, op = Ops.`+`)
@JvmName("plus:ttt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(e: Ex<XX, XX, XX>) = Ex<XX, XX, XX>(this, e, op = Ops.`+`)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> N.plus(e:Ex<V0, V1, V2>) = Ex<V0, V1, V2>(Nt(this), e, op = Ops.`+`)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.plus(n: N) = Ex<V0, V1, V2>(this, Nt(n), op = Ops.`+`)

@JvmName("minus:___") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<OO, OO, OO>) = Ex<V0, V1, V2>(this, e, op = Ops.`-`)
@JvmName("minus:__t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<OO, OO, XX>) = Ex<V0, V1, XX>(this, e, op = Ops.`-`)
@JvmName("minus:_t_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<OO, XX, OO>) = Ex<V0, XX, V2>(this, e, op = Ops.`-`)
@JvmName("minus:_tt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<OO, XX, XX>) = Ex<V0, XX, XX>(this, e, op = Ops.`-`)
@JvmName("minus:t__") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<XX, OO, OO>) = Ex<XX, V1, V2>(this, e, op = Ops.`-`)
@JvmName("minus:t_t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<XX, OO, XX>) = Ex<XX, V1, XX>(this, e, op = Ops.`-`)
@JvmName("minus:tt_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<XX, XX, OO>) = Ex<XX, XX, V2>(this, e, op = Ops.`-`)
@JvmName("minus:ttt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(e: Ex<XX, XX, XX>) = Ex<XX, XX, XX>(this, e, op = Ops.`-`)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> N.minus(e:Ex<V0, V1, V2>) = Ex<V0, V1, V2>(Nt(this), e, op = Ops.`-`)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.minus(n: N) = Ex<V0, V1, V2>(this, Nt(n), op = Ops.`-`)

@JvmName("times:___") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<OO, OO, OO>) = Ex<V0, V1, V2>(this, e, op = Ops.prod)
@JvmName("times:__t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<OO, OO, XX>) = Ex<V0, V1, XX>(this, e, op = Ops.prod)
@JvmName("times:_t_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<OO, XX, OO>) = Ex<V0, XX, V2>(this, e, op = Ops.prod)
@JvmName("times:_tt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<OO, XX, XX>) = Ex<V0, XX, XX>(this, e, op = Ops.prod)
@JvmName("times:t__") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<XX, OO, OO>) = Ex<XX, V1, V2>(this, e, op = Ops.prod)
@JvmName("times:t_t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<XX, OO, XX>) = Ex<XX, V1, XX>(this, e, op = Ops.prod)
@JvmName("times:tt_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<XX, XX, OO>) = Ex<XX, XX, V2>(this, e, op = Ops.prod)
@JvmName("times:ttt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(e: Ex<XX, XX, XX>) = Ex<XX, XX, XX>(this, e, op = Ops.prod)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> N.times(e:Ex<V0, V1, V2>) = Ex<V0, V1, V2>(Nt(this), e, op = Ops.prod)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.times(n: N) = Ex<V0, V1, V2>(this, Nt(n), op = Ops.prod)

@JvmName("div:___") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<OO, OO, OO>) = Ex<V0, V1, V2>(this, e, op = Ops.ratio)
@JvmName("div:__t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<OO, OO, XX>) = Ex<V0, V1, XX>(this, e, op = Ops.ratio)
@JvmName("div:_t_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<OO, XX, OO>) = Ex<V0, XX, V2>(this, e, op = Ops.ratio)
@JvmName("div:_tt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<OO, XX, XX>) = Ex<V0, XX, XX>(this, e, op = Ops.ratio)
@JvmName("div:t__") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<XX, OO, OO>) = Ex<XX, V1, V2>(this, e, op = Ops.ratio)
@JvmName("div:t_t") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<XX, OO, XX>) = Ex<XX, V1, XX>(this, e, op = Ops.ratio)
@JvmName("div:tt_") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<XX, XX, OO>) = Ex<XX, XX, V2>(this, e, op = Ops.ratio)
@JvmName("div:ttt") operator fun <V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(e: Ex<XX, XX, XX>) = Ex<XX, XX, XX>(this, e, op = Ops.ratio)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> N.div(e:Ex<V0, V1, V2>) = Ex<V0, V1, V2>(Nt(this), e, op = Ops.ratio)
operator fun <N: Number, V0: XO, V1: XO, V2: XO> Ex<V0, V1, V2>.div(n: N) = Ex<V0, V1, V2>(this, Nt(n), op = Ops.ratio)
