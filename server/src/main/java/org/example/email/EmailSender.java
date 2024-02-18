package org.example.email;

import javax.mail.MessagingException;

public interface EmailSender {

    void send(String to, String sender, String from) throws MessagingException;
}
