package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CharacteristicRequest {
    @NotNull
    @Positive
    private Long productId;

    @NotBlank
    @Size(max = 25)
    private String characteristicName;

    @NotBlank
    @Size(max = 100)
    private String characteristicValue;
}
