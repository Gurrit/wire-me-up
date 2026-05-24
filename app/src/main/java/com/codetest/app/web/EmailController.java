package com.codetest.app.web;

import com.codetest.app.auth.AuthenticatedUser;
import com.codetest.app.service.EmailService;
import com.codetest.auth.UserDetails;
import com.codetest.app.domain.Email;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP entry points for the exercise. Routes, parameter binding and the
 * auth hook-up are wired for you — request/response shapes and the
 * implementation are up to you.
 *
 * You decide:
 *
 * * what the request body for {@code POST /emails} looks like (implement the
 * {@code SendEmailRequest} class)
 * * what the response body looks like (replace {@code ?} in
 * {@code ResponseEntity<?>} and return the appropriate status)
 * * where the persistence + delivery logic lives
 *
 */
@RestController
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping("/emails")
    public ResponseEntity<EmailResponse> send(
            @AuthenticatedUser UserDetails caller,
            @RequestBody SendEmailRequest request) {

        Email saved = service.send(caller, request);

        return ResponseEntity.status(201)
                .body(toResponse(saved));
    }

    @GetMapping("/users/{userId}/emails")
    public ResponseEntity<List<EmailResponse>> listSentBy(
            @AuthenticatedUser UserDetails caller,
            @PathVariable("userId") String userId,
            @RequestParam(name = "status", required = false) String status) {

        if (!caller.id().equals(userId)) {
            return ResponseEntity.status(401).build();
        }

        List<EmailResponse> result = service.list(userId, status)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(result);
    }

    private EmailResponse toResponse(Email e) {
        return new EmailResponse(
                e.getId(),
                e.getSenderId(),
                e.getSenderEmail(),
                e.getRecipient(),
                e.getSubject(),
                e.getBody(),
                e.getStatus().name(),
                e.getCreatedAt());
    }
}