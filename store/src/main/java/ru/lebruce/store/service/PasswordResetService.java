package ru.lebruce.store.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lebruce.store.domain.dto.SetPasswordRequest;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.domain.model.User;

@Service
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public void resetPassword(String token, SetPasswordRequest passwordRequest) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getToken(token);
        passwordResetTokenService.expires(passwordResetToken);
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        userService.saveUser(user);

        passwordResetTokenService.delete(passwordResetToken);
    }

    @Transactional
    @Async("taskExecutor")
    public void resetPasswordRequest(User user) throws MessagingException {
        var token = passwordResetTokenService.generateToken(user);
        var emailContext = emailService.resetPasswordEmailContext(user, token.getToken());
        emailService.sendEmail(emailContext);
    }
}
