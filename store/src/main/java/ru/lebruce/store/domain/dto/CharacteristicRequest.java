package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CharacteristicRequest {
    @NotEmpty
    @Positive
    private Long productId;

    @NotEmpty
    @Size(max = 25)
    private String characteristicName;

    @NotEmpty
    @Size(max = 100)
    private String characteristicValue;
}
