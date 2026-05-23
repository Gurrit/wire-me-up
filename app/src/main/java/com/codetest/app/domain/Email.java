package com.codetest.app.domain;

import java.time.Instant;
import java.util.UUID;

public class Email {
    private UUID id;
    private UUID senderId;
    private String senderEmail;
    private String recipient;
    private String subject;
    private String body;
    private EmailStatus status;
    private Instant createdAt;
}