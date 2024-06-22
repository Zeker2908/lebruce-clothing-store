package ru.lebruce.store.domain.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.lebruce.store.domain.model.Role;
import ru.lebruce.store.domain.model.ShoppingCart;
import ru.lebruce.store.domain.model.User;
import ru.lebruce.store.service.ShoppingCartService;
import ru.lebruce.store.service.UserService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final PasswordEncoder passwordEncoder;


    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String password;

    @Override
    public void run(String... args) {
        if (!userService.existsByUsername(adminEmail)) {
            User user = User.builder()
                    .username(adminEmail)
                    .password(passwordEncoder.encode(password))
                    .firstName("admin")
                    .role(Role.ROLE_ADMIN)
                    .build();
            userService.create(user);
            var cart = ShoppingCart.builder()
                    .user(user)
                    .build();
            shoppingCartService.save(cart);
        }
    }
}
