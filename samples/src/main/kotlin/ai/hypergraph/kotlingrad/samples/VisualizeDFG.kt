package ai.hypergraph.kotlingrad.samples

import ai.hypergraph.kotlingrad.api.*

fun main() {
  val t = (1 + x * 2 - 3 + y + z / y).d(y).d(x) + z / y * 3 - 2 + sin(x)
//  val t = (1 + x * 2 + z / y).d(y).d(x) + z / y * 3 - 4 * (y pow y).d(y)
  t.saveToFile("dataflow.svg")
//  t.show()
}