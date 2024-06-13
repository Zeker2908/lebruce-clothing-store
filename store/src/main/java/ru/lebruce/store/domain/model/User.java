package ru.lebruce.store.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_username", columnList = "username")
})
@Schema(description = "Пользователь")
public class User implements UserDetails {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    @Schema(description = "Имя пользователя", example = "john_doe", required = true)
    private String username;

    @Column(name = "password", nullable = false)
    @Schema(description = "Пароль пользователя", example = "password123", required = true)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Schema(description = "Роль пользователя", example = "ADMIN", required = true)
    private Role role;

    @Column(nullable = false)
    @Schema(description = "Имя пользователя", example = "Иван", required = true)
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Отзывы пользователя")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Адреса пользователя")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Заказы пользователя")
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(description = "Корзина пользователя")
    private ShoppingCart shoppingCart;

    @Override
    @Schema(description = "Получение полномочий пользователя")
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @Schema(description = "Проверка, что учетная запись не истекла")
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Schema(description = "Проверка, что учетная запись не заблокирована")
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Schema(description = "Проверка, что учетные данные не истекли")
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Schema(description = "Проверка, что учетная запись включена")
    public boolean isEnabled() {
        return true;
    }
}
