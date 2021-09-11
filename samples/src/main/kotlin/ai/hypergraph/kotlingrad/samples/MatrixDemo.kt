package ai.hypergraph.kotlingrad.samples

import ai.hypergraph.kotlingrad.api.*
import ai.hypergraph.kotlingrad.shapes.*

fun main() {
  val f = x pow 2
  println(f(x to 3.0))
  println("f(x) = $f")
  val df_dx = f.d(x)
  println("f'(x) = $df_dx")

  val g = x pow x
  println("g(x) = $g")
  val dg_dx = g.d(x)
  println("g'(x) = $dg_dx")

  val h = x + y
  println("h(x) = $h")
  val dh_dx = h.d(x)
  println("h'(x) = $dh_dx")

  val vf1 = Vec(y + x, y * 2)
  val bh = x * vf1
  val vf2 = Vec(x, y)
  val q = vf1 + vf2
  val r = q(x to 1.0, y to 2.0)
  println("r: $r")

  val mf1 = Mat2x1(y * y, x * y)

  val mf2 = Mat1x2(vf2)

  val qr = mf2 * Vec(x, y)

  val mf3 = Mat3x2(x, x, y, x, x, x)
  val mf4 = Mat2x2(vf2, vf2)
  val mf5 = Mat2x2(
    y * y, x * x,
    x * y, y * y
  )
  val mf6 = mf4 * mf5 * mf1

  println(mf1 * mf2) // 2*1 x 1*2
//    println(mf1 * vf1) // 2*1 x 2
  println(mf2 * vf1) // 1*2 x 2
  println(mf3 * vf1) // 3*2 x 2
//    println(mf3 * mf3) // 3*2 x 3*2
  oldMatDemo()
}


fun oldMatDemo() {
  // Inferred type: Mat<Int, D1, D3>
  val a = DReal.Mat1x3(1.0, 2.0, 3.0)
  println("a = $a")
  // Inferred type: Mat<Int, D3, D1>
  val b = a.ᵀ
  println("b = $b")
  // Inferred type: Mat<Int, D1, D1>
  val c = a * b
  println("c = ab = $c")

// Does not compile, inner dimension mismatch
//  a * a
//  b * b

// Does not compile, incompatible shape
//  val b_ = Mat(D3, D1, 1, 2)
//  val c_ = Mat(D3, D1, 1, 2, 3, 4)

// Does not compile, incompatible shape
//  val b_ = Mat(D2, D1, 1)

  // Inferred type: Mat<Int, D2, D3>
  val d = DReal.Mat2x3(
    1.0, 2.0, 3.0,
    4.0, 5.0, 6.0
  )
  println("d = $d}")

  // Inferred type: Mat<Int, D3, D2>
  val e = DReal.Mat3x2(
    1.0, 2.0,
    3.0, 4.0,
    5.0, 6.0
  )
  println("e = $e")

  // Inferred type: Mat<Int, D2, D2>
  val f = d * e
  println("f = de = $f")

// Does not compile, inner dimension mismatch
//  e * b
//  f * b

// Does not compile, incompatible size
//  val d_: Mat<DReal, D2, D3> = Mat2x3(
//    1, 2, 3,
//    4, 5
//  )

  // Inferred type: Mat<Int, D3, D3>
  val g = DReal.Mat3x3(
    1.0, 2.0, 3.0,
    4.0, 5.0, 6.0,
    7.0, 8.0, 9.0
  )
  println("g = $g")

  // Inferred type: Mat<Int, D3, D3>
  val h = DReal.Mat3x3(
    1.0, 2.0, 3.0,
    4.0, 5.0, 6.0,
    7.0, 8.0, 9.0
  )
  println("h = $g")

  // Inferred type: Mat<Int, D3, D3>
  val i = g * h
  println("i = gh = $i")
  val j = i * i
  println("j = ii = $j")
  val k = i * b
  println("k = ib = $j")

// Does not compile, inner dimension mismatch
//  i * f
//  i * d

  // Inferred type: Mat<Int, D4, D4>
  val l = DReal.Mat4x4(
    1.0, 2.0, 3.0, 4.0,
    5.0, 6.0, 7.0, 8.0,
    9.0, 0.0, 0.0, 0.0,
    9.0, 0.0, 0.0, 0.0
  )
  println("l = $l")

  // Inferred type: Mat<Int, D4, D3>
  val m = DReal.Mat4x3(
    1.0, 1.0, 1.0, 2.0,
    2.0, 2.0, 3.0, 3.0,
    3.0, 4.0, 4.0, 4.0
  )

  // Inferred type: Mat<Int, D4, D3>
  val lm = l * m
  println("lm = $lm")

// Does not compile, inner dimension mismatch
//  lm * f
//  ln * d

  val o = DReal.Mat9x9(
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0,
    1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0
  )

  println(o * o - (o + o))

//  val p = Mat(D1, D100){ 0.0 }
//  // Type-checked matrix operations are still possible after unsafe construction
//  val q = p.transpose()
//  q * p
// Does not compile, inner dimension mismatch
//  q * f

  val t = DReal.Mat1x1(1.0)
  println("t: $t")
}