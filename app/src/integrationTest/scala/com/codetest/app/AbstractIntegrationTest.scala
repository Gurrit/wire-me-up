package com.codetest.app

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.jdbc.core.JdbcTemplate

/**
 * Base class for full-stack integration tests.
 *
 * What you get by extending this class:
 *   - The full Spring context boots on a random port, Flyway runs the
 *     migration against the in-memory H2 instance, and the
 *     [[TestRestTemplate]] is preconfigured to point at it — just
 *     call `http.exchange("/emails", ...)` and so on.
 *   - The `emails` table is truncated before every test, so you
 *     can assume a clean slate.
 *   - [[JdbcTemplate]] is exposed if you want to seed or assert
 *     on rows directly.
 *   - Constants for the pre-baked test users / tokens (see the README
 *     for the originals).
 *   - [[bearer]] for building an `Authorization` header without ceremony.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTest {

  protected val ALICE_ID = "00000000-0000-0000-0000-000000000001"
  protected val BOB_ID   = "00000000-0000-0000-0000-000000000002"

  protected val ALICE_TOKEN =
    "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0." +
    "eyJzdWIiOiIwMDAwMDAwMC0wMDAwLTAwMDAtMDAwMC0wMDAwMDAwMDAwMDEiLCJ1c2VybmFtZSI6ImFsaWNlIiwiZW1haWwiOiJhbGljZUBleGFtcGxlLmNvbSIsImV4cCI6NDEwMjQ0NDgwMH0."

  protected val BOB_TOKEN =
    "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0." +
    "eyJzdWIiOiIwMDAwMDAwMC0wMDAwLTAwMDAtMDAwMC0wMDAwMDAwMDAwMDIiLCJ1c2VybmFtZSI6ImJvYiIsImVtYWlsIjoiYm9iQGV4YW1wbGUuY29tIiwiZXhwIjo0MTAyNDQ0ODAwfQ."

  protected val EXPIRED_TOKEN =
    "eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0." +
    "eyJzdWIiOiIwMDAwMDAwMC0wMDAwLTAwMDAtMDAwMC0wMDAwMDAwMDAwMDEiLCJ1c2VybmFtZSI6ImFsaWNlIiwiZW1haWwiOiJhbGljZUBleGFtcGxlLmNvbSIsImV4cCI6MTcwMDAwMDAwMH0."

  @Autowired
  protected var http: TestRestTemplate = _

  @Autowired
  protected var jdbc: JdbcTemplate = _

  @BeforeEach
  def resetEmails(): Unit = jdbc.execute("DELETE FROM emails")

  protected def bearer(token: String): HttpHeaders = {
    val headers = new HttpHeaders()
    headers.setBearerAuth(token)
    headers
  }
}
