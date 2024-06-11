package ru.lebruce.store.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Адрес электронной почты (username)", example = "jondoe@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String username;

    @Schema(description = "Пароль", example = "my_1secret1_password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    private String password;

    @Schema(description = "Имя", example = "Иван")
    @Size(min = 1, max = 25, message = "Длина имени должна быть не более 25 символов")
    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    @Size(min = 1, max = 25, message = "Длина фамилии должна быть не более 25 символов")
    private String lastName;

}