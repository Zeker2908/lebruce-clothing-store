package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import ru.lebruce.store.abstracts.AbstractEmailContext;
import ru.lebruce.store.domain.model.PendingUser;

public interface EmailService {
    void sendConfirmationEmail(AbstractEmailContext email) throws MessagingException;

    public AbstractEmailContext confirmEmailContext(PendingUser pendingUser, String confirmationToken);

}
