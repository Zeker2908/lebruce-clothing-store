package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import ru.lebruce.store.domain.dto.EmailContext;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.domain.model.User;

public interface EmailService {
    void sendEmail(EmailContext email) throws MessagingException;

    EmailContext confirmEmailContext(PendingUser pendingUser, String token);

    EmailContext resetPasswordEmailContext(User user, String token);

}
