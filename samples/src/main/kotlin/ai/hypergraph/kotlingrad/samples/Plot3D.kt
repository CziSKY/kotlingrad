package ai.hypergraph.kotlingrad.samples

import ai.hypergraph.kotlingrad.api.*
import org.jzy3d.analysis.*
import org.jzy3d.chart.factories.AWTChartComponentFactory
import org.jzy3d.colors.*
import org.jzy3d.colors.colormaps.ColorMapRainbow
import org.jzy3d.maths.Range
import org.jzy3d.plot3d.builder.*
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid
import org.jzy3d.plot3d.rendering.canvas.Quality.Advanced

object Plot3D: AbstractAnalysis() {
  override fun init() {
    // Define a function to plot
    val mapper = object: Mapper() {
      override fun f(xc: Double, yc: Double): Double {
          val Z = x * x + pow(y, 2)
          val Z10 = Z * 10
          val sinZ = sin(Z10)
          val sinZ_10 = sinZ / 10
          val dZ_dx = d(sinZ_10) / d(x)
          val d2Z_dxdy = d(dZ_dx) / d(y)
          val d3Z_d2xdy = d(d2Z_dxdy) / d(x)

          return d3Z_d2xdy(xc, yc).toDouble()
        }
    }

    // Define range and precision for the function to plot
    val range = Range(-1f, 1f)
    val steps = 400

    // Create the object to represent the function over the given range.
    val grid = OrthonormalGrid(range, steps, range, steps)
    val surface = Builder.buildOrthonormal(grid, mapper)
    with(surface) {
      colorMapper = ColorMapper(ColorMapRainbow(),
        bounds.zmin.toDouble(),
        bounds.zmax.toDouble(),
        Color(1f, 1f, 1f, .5f))

      faceDisplayed = true
      wireframeDisplayed = false
    }

    // Create a chart
    chart = AWTChartComponentFactory.chart(Advanced, getCanvasType())
    chart.scene.graph.add(surface)
  }
}

fun main() = AnalysisLauncher.open(Plot3D)