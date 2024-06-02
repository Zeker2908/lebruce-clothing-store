package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductSizeRequest {
    @NotNull
    private Long productId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String size;

    @NotNull
    @PositiveOrZero
    private Integer quantity;
}
