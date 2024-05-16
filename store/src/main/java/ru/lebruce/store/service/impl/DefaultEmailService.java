package ru.lebruce.store.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import ru.lebruce.store.abstracts.AbstractEmailContext;
import ru.lebruce.store.domain.model.PendingUser;
import ru.lebruce.store.service.EmailService;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class DefaultEmailService implements EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${app.url}")
    private String appUrl;

    @Override
    public void sendMail(AbstractEmailContext email) throws MessagingException {
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
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        emailSender.send(mimeMessage);
    }

    public void sendConfirmationEmail(PendingUser user, String confirmationToken) {
        String subject = "Подтверждение email";
        String confirmationUrl = appUrl + "/api/v1/auth/confirm?token=" + confirmationToken;
        String message = "Для подтверждения email перейдите по ссылке: " + confirmationUrl;
        String to = user.getEmail();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(message);
        email.setTo(to);

        emailSender.send(email);
    }
}
