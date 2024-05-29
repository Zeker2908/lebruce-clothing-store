package ru.lebruce.store.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.lebruce.store.domain.dto.EmailContext;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.service.EmailService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${app.url}")
    private String appUrl;

    @Override
    public void sendEmail(EmailContext email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariables(email.getContext());
        String emailContent = templateEngine.process(email.getTemplateLocation(), context);

        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setFrom(email.getFrom());
        mimeMessageHelper.setText(emailContent, true);
        emailSender.send(message);
    }

    @Override
    public EmailContext confirmEmailContext(PendingUser pendingUser, String confirmationToken) {
        Map<String, Object> context = new HashMap<>();
        context.put("firstName", pendingUser.getFirstName());
        context.put("verificationURL", appUrl + "confirm?token=" + confirmationToken);
        return EmailContext.builder()
                .from(from)
                .to(pendingUser.getUsername())
                .subject("Подтверждение email")
                .email(pendingUser.getUsername())
                .context(context)
                .templateLocation("confirmation-email.html")
                .build();
    }

    @Override
    public EmailContext resetPasswordEmailContext(User user, String token) {
        Map<String, Object> context = new HashMap<>();
        context.put("firstName", user.getFirstName());
        context.put("verificationURL", appUrl + "password?token=" + token);
        return EmailContext.builder()
                .from(from)
                .to(user.getUsername())
                .subject("Сброс пароля")
                .email(user.getUsername())
                .context(context)
                .templateLocation("reset-password-mail.html")
                .build();
    }

    @Override
    public EmailContext setUsernmaeEmailContext(User user, String token, String newEmail) {
        Map<String, Object> context = new HashMap<>();
        context.put("firstName", user.getFirstName());
        context.put("verificationURL", appUrl + "username?token=" + token);
        context.put("email", newEmail);
        return EmailContext.builder()
                .from(from)
                .to(user.getUsername())
                .subject("Изменение почты")
                .email(user.getUsername())
                .context(context)
                .templateLocation("set-username-mail.html")
                .build();
    }

}
