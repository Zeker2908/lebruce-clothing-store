package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {
    private Long productId;

    @NotBlank(message = "Наименование продукта не должно быть пустым")
    private String productName;

    @NotNull(message = "Бренд продукта не должен быть пустым")
    private Long brandId;

    @NotNull(message = "Категория продукта не должна быть пустой")
    private Long categoryId;

    @NotNull
    @PositiveOrZero(message = "Цена продукта должна быть неотрицательной")
    private double price;

    @NotBlank
    @Size(max = 1000, message = "Описание продукта не должно превышать 1000 символов")
    private String description;

}
