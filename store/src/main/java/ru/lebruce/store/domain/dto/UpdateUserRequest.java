package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Size(min = 1, max = 25, message = "Длина имени должна быть не более 25 символов")
    private String firstName;

    @Size(min = 1, max = 25, message = "Длина фамилии должна быть не более 25 символов")
    private String lastName;
}
