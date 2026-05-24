package com.codetest.app.web;

import java.time.Instant;
import java.util.UUID;

public record EmailResponse(
        UUID id,
        UUID senderId,
        String senderEmail,
        String recipient,
        String subject,
        String body,
        String status,
        Instant createdAt) {
}