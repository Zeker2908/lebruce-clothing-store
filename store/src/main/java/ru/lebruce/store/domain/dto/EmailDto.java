package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailDto {
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;
}
