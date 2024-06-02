package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductSizeRequest {
    @NotBlank
    private Long productId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String size;

    @NotBlank
    @PositiveOrZero
    private Integer quantity;
}
