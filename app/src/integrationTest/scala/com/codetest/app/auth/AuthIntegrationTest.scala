package com.codetest.app.auth

import com.codetest.app.AbstractIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.{HttpEntity, HttpMethod, HttpStatus}

/**
 * Worked example showing how to write integration tests against the
 * wired-up Spring context. The tests below exercise the auth bridge,
 * which is already implemented for you — feel free to delete them once
 * you've written your own coverage for the endpoints.
 */
class AuthIntegrationTest extends AbstractIntegrationTest {

  @Test
  def rejects_request_without_bearer_token(): Unit = {
    val response = http.exchange(
      "/users/" + ALICE_ID + "/emails",
      HttpMethod.GET,
      HttpEntity.EMPTY,
      classOf[String]
    )
    assertThat(response.getStatusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
  }

  @Test
  def rejects_request_with_expired_token(): Unit = {
    val response = http.exchange(
      "/users/" + ALICE_ID + "/emails",
      HttpMethod.GET,
      new HttpEntity[Nothing](bearer(EXPIRED_TOKEN)),
      classOf[String]
    )
    assertThat(response.getStatusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
  }
}
