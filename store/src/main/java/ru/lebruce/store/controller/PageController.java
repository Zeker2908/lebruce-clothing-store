package ru.lebruce.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.service.ConfirmationEmailService;
import ru.lebruce.store.service.PasswordResetTokenService;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class PageController {
    private final ConfirmationEmailService confirmationEmailService;
    private final PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam String token, Model model) {
        confirmationEmailService.confirmEmail(token);
        return "confirmation";
    }

    @GetMapping("/password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getToken(token);
        passwordResetTokenService.expires(passwordResetToken);
        model.addAttribute("token", token);
        return "reset-password";
    }
}
