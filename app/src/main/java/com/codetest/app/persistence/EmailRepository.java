package com.codetest.app.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.codetest.app.domain.Email;
import com.codetest.app.domain.EmailStatus;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class EmailRepository {

    private final JdbcTemplate jdbc;

    public EmailRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(Email email) {
        jdbc.update("""
                    INSERT INTO emails (
                        id, sender_id, sender_email,
                        recipient, subject, body,
                        status, created_at
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                email.getId(),
                email.getSenderId(),
                email.getSenderEmail(),
                email.getRecipient(),
                email.getSubject(),
                email.getBody(),
                email.getStatus().name(),
                Timestamp.from(email.getCreatedAt()));
    }

    public List<Email> findBySender(UUID senderId) {
        return jdbc.query("""
                    SELECT * FROM emails
                    WHERE sender_id = ?
                    ORDER BY created_at DESC
                """, mapper(), senderId);
    }

    public List<Email> findBySenderAndStatus(UUID senderId, String status) {
        return jdbc.query("""
                    SELECT * FROM emails
                    WHERE sender_id = ? AND status = ?
                    ORDER BY created_at DESC
                """, mapper(), senderId, status);
    }

    private RowMapper<Email> mapper() {
        return (rs, rowNum) -> {
            Email e = new Email();
            e.setId(rs.getObject("id", UUID.class));
            e.setSenderId(rs.getObject("sender_id", UUID.class));
            e.setSenderEmail(rs.getString("sender_email"));
            e.setRecipient(rs.getString("recipient"));
            e.setSubject(rs.getString("subject"));
            e.setBody(rs.getString("body"));
            e.setStatus(EmailStatus.valueOf(rs.getString("status")));
            e.setCreatedAt(rs.getTimestamp("created_at").toInstant());
            return e;
        };
    }
}