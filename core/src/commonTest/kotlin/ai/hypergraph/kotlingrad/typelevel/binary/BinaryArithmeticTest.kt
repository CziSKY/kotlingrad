package ai.hypergraph.kotlingrad.typelevel.binary

import ai.hypergraph.kotlingrad.typelevel.binary.arithmetic.*
import kotlin.test.*

/**
./gradlew :kotlingrad:jvmTest --tests "ai.hypergraph.kotlingrad.typelevel.binary.BinaryArithmeticTest"
*/
class BinaryArithmeticTest {
  @Test
  fun testBooleanArithmetic() {
    val t = T.F
      .let { it + T.F }
      .let { it + T.F.F }
      .let { it + T.T }
      .let { it + T.F }
      .let { it - T.F }
      .let { it + T.F }
      .let { it + T.F }
      .let { it + T }

    assertEquals(T.F.F.F.F, t)
  }
}