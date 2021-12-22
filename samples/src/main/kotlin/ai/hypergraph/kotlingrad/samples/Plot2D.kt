@file:Suppress("NonAsciiCharacters", "LocalVariableName")

package ai.hypergraph.kotlingrad.samples

import ai.hypergraph.kaliningraph.visualization.show
import ai.hypergraph.kotlingrad.api.*

import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.plot.PlotSvgExport
import jetbrains.letsPlot.geom.*
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.intern.toSpec
import jetbrains.letsPlot.label.ggtitle
import java.io.File

val X_RANGE = -10.0..10.0

fun main() {
  // TODO: https://en.wikipedia.org/wiki/Chebyshev_polynomials
  // TODO: https://en.wikipedia.org/wiki/Gegenbauer_polynomials

  // https://en.wikipedia.org/wiki/Hermite_polynomials
  val y0 = exp(-x * x / 2)

  val hermite = plot2D(X_RANGE, *derivatives(y0, x))

  val y1 = sin(sin(sin(x))) / x + sin(x) * x + cos(x) + x

  val sinusoid = plot2D(X_RANGE, *derivatives(y1, x))

  val y2 = sigmoid(x)

  val sigmoid = plot2D(X_RANGE, *derivatives(y2, x))

  hermite.saveAs("hermite.svg").show()
  sinusoid.saveAs("sinusoid.svg").show()
  sigmoid.saveAs("sigmoid.svg").show()
}

private fun derivatives(y: SFun<DReal>, x: SVar<DReal>): Array<SFun<DReal>> {
    val `dy∕dx` = d(y) / d(x)
    val `d²y∕dx²` = d(`dy∕dx`) / d(x)
    val `d³y∕dx³` = d(`d²y∕dx²`) / d(x)
    val `d⁴y∕dx⁴` = d(`d³y∕dx³`) / d(x)
    val `d⁵y∕dx⁵` = d(`d⁴y∕dx⁴`) / d(x)

//  println("""y=$y
//               dy/dx=$`dy∕dx`
//               d²y/dx²=$`d²y∕dx²`
//               d³y/dx³=$`d³y∕dx³`
//               d⁴y/dx⁴=$`d⁴y∕dx⁴`""".trimIndent())

    return arrayOf(y, `dy∕dx`, `d²y∕dx²`, `d³y∕dx³`, `d⁴y∕dx⁴`, `d⁵y∕dx⁵`)
  }

private fun plot2D(
  range: ClosedFloatingPointRange<Double>,
  vararg funs: SFun<DReal>
): String {
  val labels = arrayOf("y", "dy/dx", "d²y/x²", "d³y/dx³", "d⁴y/dx⁴", "d⁵y/dx⁵")
  val xs = (range step 0.0087).toList()
  val ys = funs.map { xs.map { xv -> it(x to xv).toDouble() } }
  val data = (labels.zip(ys) + ("x" to xs)).toMap()
  val colors = listOf("dark_green", "gray", "black", "red", "orange", "dark_blue")
  val geoms = labels.zip(colors).map { geomPath(size = 2.0, color = it.second) { x = "x"; y = it.first } }
  val plot = geoms.foldRight(ggplot(data)) { it, acc -> acc + it } + ggtitle("Derivatives of y=${funs[0]}")

  val plotSpec = plot.toSpec()
  val plotSize = DoubleVector(1000.0, 500.0)

  return PlotSvgExport.buildSvgImageFromRawSpecs(plotSpec, plotSize)
}

fun String.saveAs(filename: String) =
  File("$resourcesPath/$filename").also { it.writeText(this) }