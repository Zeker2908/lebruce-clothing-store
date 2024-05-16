package ru.lebruce.store.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lebruce.store.service.EmailService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/simple-email/{email}")
    public @ResponseBody ResponseEntity<?> sendSimpleEmail(@PathVariable String email) {
        emailService.sendSimpleEmail(email, "Welcome", "This is a welcome email for your!!");
        return new ResponseEntity<>("Please check your inbox", HttpStatus.OK);
    }

    @GetMapping("/simple-order-email/{email}")
    public @ResponseBody ResponseEntity<?> sendEmailAttachment(@PathVariable String email) throws MessagingException, FileNotFoundException {
        emailService.sendEmailWithAttachment(email, "Order Confirmation", "Thanks for your recent order",
                "classpath:purchase_order.pdf");
        return new ResponseEntity<>("Please check your inbox for order confirmation", HttpStatus.OK);
    }

}