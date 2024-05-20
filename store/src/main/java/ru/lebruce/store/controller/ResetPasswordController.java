package ru.lebruce.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lebruce.store.domain.model.PasswordResetToken;
import ru.lebruce.store.service.PasswordResetTokenService;

@Controller
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final PasswordResetTokenService passwordResetTokenService;

    @GetMapping
    public String resetPasswordPage(@RequestParam String token, Model model) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getToken(token);
        passwordResetTokenService.expires(passwordResetToken);
        model.addAttribute("token", token);
        return "reset-password";
    }
}
