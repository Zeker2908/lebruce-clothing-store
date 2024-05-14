package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetPasswordRequest {
    @Size(min = 8, max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}
