package com.codetest.app.web

import com.codetest.app.auth.AuthenticatedUser
import com.codetest.auth.UserDetails
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.{GetMapping, PathVariable, PostMapping, RequestBody, RestController}

/**
 * HTTP entry points for the exercise. Routes, parameter binding and the
 * auth hook-up are wired for you — request/response shapes and the
 * implementation are up to you.
 *
 * You decide:
 *
 *  - what the request body for `POST /emails` looks like (implement the `SendEmailRequest` class)
 *  - what the response body looks like (replace `_` in `ResponseEntity[_]` and return the appropriate status)
 *  - where the persistence + delivery logic lives
 */
@RestController
class EmailController {

  @PostMapping(Array("/emails"))
  def send(
      @AuthenticatedUser caller: UserDetails,
      @RequestBody request: SendEmailRequest
  ): ResponseEntity[_] = {
    // TODO (candidate): Implement this flow endpoint end-2-end
    throw new UnsupportedOperationException("POST /emails not implemented")
  }

  @GetMapping(Array("/users/{userId}/emails"))
  def listSentBy(
      @AuthenticatedUser caller: UserDetails,
      @PathVariable("userId") userId: String
  ): ResponseEntity[_] = {
    // TODO (candidate): Implement this flow endpoint end-2-end
    throw new UnsupportedOperationException("GET /users/{userId}/emails not implemented")
  }
}
