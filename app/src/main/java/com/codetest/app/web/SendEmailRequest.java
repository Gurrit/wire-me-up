package com.codetest.app.web;

/** Request body for {@code POST /emails}. */
public record SendEmailRequest(
                String from, String to, String subject, String body) {
}
