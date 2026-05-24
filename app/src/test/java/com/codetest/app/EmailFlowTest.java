package com.codetest.app;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmailFlowTest {

        private static final String TOKEN = "Bearer eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0."
                        + "eyJzdWIiOiIwMDAwMDAwMC0wMDAwLTAwMDAtMDAwMC0wMDAwMDAwMDAwMDEiLC"
                        + "J1c2VybmFtZSI6ImFsaWNlIiwiZW1haWwiOiJhbGljZUBleGFtcGxlLmNvbSIs"
                        + "ImV4cCI6NDEwMjQ0NDgwMH0.";

        @Autowired
        private MockMvc mvc;

        @Test
        void sendEmail_thenListEmails_returnsPersistedEmail() throws Exception {
                String requestBody = """
                                {
                                  "to": "bob@example.com",
                                  "subject": "hello",
                                  "body": "integration test"
                                }
                                """;

                mvc.perform(post("/emails")
                                .header("Authorization", TOKEN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.status").value("SENT"))
                                .andExpect(jsonPath("$.recipient").value("bob@example.com"));

                mvc.perform(get("/users/00000000-0000-0000-0000-000000000001/emails")
                                .header("Authorization", TOKEN))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].subject").value("hello"))
                                .andExpect(jsonPath("$[0].status").value("SENT"));
        }

        @Test
        void sendEmail_withExpiredToken_returns401() throws Exception {

                String expiredToken = "Bearer eyJhbGciOiJub25lIiwidHlwIjoiSldUIn0."
                                + "eyJzdWIiOiIwMDAwMDAwMC0wMDAwLTAwMDAtMDAwMC0wMDAwMDAwMDAwMDEiLC"
                                + "J1c2VybmFtZSI6ImFsaWNlIiwiZW1haWwiOiJhbGljZUBleGFtcGxlLmNvbSIs"
                                + "ImV4cCI6MTcwMDAwMDAwMH0.";

                String requestBody = """
                                {
                                  "to": "bob@example.com",
                                  "subject": "hello",
                                  "body": "expired token test"
                                }
                                """;

                mvc.perform(post("/emails")
                                .header("Authorization", expiredToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isUnauthorized());
        }

        @Test
        void sendEmail_withMalformedToken_returns401() throws Exception {

                String malformedToken = "Bearer malformed";

                String requestBody = """
                                {
                                  "to": "bob@example.com",
                                  "subject": "hello",
                                  "body": "malformed token test"
                                }
                                """;

                mvc.perform(post("/emails")
                                .header("Authorization", malformedToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                                .andExpect(status().isUnauthorized());
        }
}