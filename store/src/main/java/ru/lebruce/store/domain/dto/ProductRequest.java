package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {
    private Long productId;

    @NotEmpty(message = "Наименование продукта не должно быть пустым")
    private String productName;

    @NotEmpty(message = "Бренд продукта не должен быть пустым")
    private String brand;

    @NotNull(message = "Категория продукта не должна быть пустой")
    private Long categoryId;

    @PositiveOrZero(message = "Цена продукта должна быть неотрицательной")
    private double price;

    @Size(max = 1000, message = "Описание продукта не должно превышать 1000 символов")
    private String description;

    @PositiveOrZero(message = "Доступное количество продукта должно быть неотрицательным")
    private int availableQuantity;
}
