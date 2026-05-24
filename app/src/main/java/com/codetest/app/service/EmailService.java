package com.codetest.app.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.codetest.app.domain.Email;
import com.codetest.app.domain.EmailStatus;
import com.codetest.app.persistence.EmailRepository;
import com.codetest.app.web.SendEmailRequest;
import com.codetest.auth.UserDetails;
import com.codetest.email.EmailClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository repo;
    private final EmailClient emailClient;

    @Value("${email-config.url}")
    private String relayUrl;

    @Value("${email-config.username}")
    private String relayUsername;

    @Value("${email-config.password}")
    private String relayPassword;

    public EmailService(
            EmailRepository repo,
            EmailClient emailClient) {
        this.repo = repo;
        this.emailClient = emailClient;
    }

    public Email send(UserDetails caller, SendEmailRequest req) {

        Email email = new Email();

        email.setId(UUID.randomUUID());
        email.setSenderId(UUID.fromString(caller.id()));
        email.setSenderEmail(caller.email());
        email.setRecipient(req.to());
        email.setSubject(req.subject());
        email.setBody(req.body());
        email.setCreatedAt(Instant.now());

        try {

            emailClient.send(
                    relayUrl,
                    relayUsername,
                    relayPassword,
                    caller.email(),
                    req.to(),
                    req.subject(),
                    req.body());

            email.setStatus(EmailStatus.SENT);

        } catch (Exception e) {

            log.error("Failed to send email", e);

            email.setStatus(EmailStatus.FAILED);
        }

        repo.save(email);

        return email;
    }

    public List<Email> list(String callerId, String status) {

        UUID senderId = UUID.fromString(callerId);

        if (status == null || status.isBlank()) {
            return repo.findBySender(senderId);
        }

        return repo.findBySenderAndStatus(
                senderId,
                status.toUpperCase());
    }
}