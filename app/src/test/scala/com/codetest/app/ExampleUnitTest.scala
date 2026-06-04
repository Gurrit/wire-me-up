package com.codetest.app

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/** Placeholder so the unit-test source root isn't empty. Delete me. */
class ExampleUnitTest {

  @Test
  def framework_is_wired_up(): Unit =
    assertThat("hello".toUpperCase).isEqualTo("HELLO")
}
