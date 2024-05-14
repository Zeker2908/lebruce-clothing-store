package ru.lebruce.store.domain.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
}
