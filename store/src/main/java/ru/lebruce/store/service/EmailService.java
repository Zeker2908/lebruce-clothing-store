package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import ru.lebruce.store.abstracts.AbstractEmailContext;

import java.io.FileNotFoundException;

public interface EmailService {
    void sendMail(AbstractEmailContext email) throws MessagingException;

    void sendSimpleEmail(String toAddress, String subject, String message);

    void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException;
}
