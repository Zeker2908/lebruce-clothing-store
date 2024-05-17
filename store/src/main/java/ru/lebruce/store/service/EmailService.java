package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import ru.lebruce.store.domain.dto.EmailContext;
import ru.lebruce.store.domain.model.PendingUser;

public interface EmailService {
    void sendConfirmationEmail(EmailContext email) throws MessagingException;

    public EmailContext confirmEmailContext(PendingUser pendingUser, String confirmationToken);

}
