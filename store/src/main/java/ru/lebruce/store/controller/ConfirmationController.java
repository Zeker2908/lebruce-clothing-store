package ru.lebruce.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lebruce.store.service.ConfirmationEmailService;

@Controller
@RequestMapping("/api/v1/confirm")
@RequiredArgsConstructor
public class ConfirmationController {
    private final ConfirmationEmailService confirmationEmailService;

    @GetMapping
    public String confirmEmail(@RequestParam String token, Model model) {
        confirmationEmailService.confirmEmail(token);
        return "confirmation";
    }
}
