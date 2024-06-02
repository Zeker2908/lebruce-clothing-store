package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShoppingCartItemRequest {
    @NotNull(message = "ProductId не должно быть пустым")
    private Long productId;

    @NotNull(message = "Колличество не должно быть пустым")
    private Integer quantity;

    @NotNull
    private String size;

}
