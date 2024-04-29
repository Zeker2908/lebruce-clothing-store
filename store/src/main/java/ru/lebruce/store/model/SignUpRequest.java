package ru.lebruce.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
}
