package org.example.player.email;

import jakarta.mail.MessagingException;

sealed interface EmailSender permits EmailService {
    void send(String to, String sender, String from) throws MessagingException;
}
