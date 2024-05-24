package ru.lebruce.store.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class ReviewRequest {
    private Long reviewId;

    @NotNull(message = "ProductId не должно быть пустым")
    private Long productId;

    @NotNull(message = "UserId не должно быть пустым")
    private Long userId;

    @NotNull(message = "Rating не должно быть пустым")
    @Range(min = 1, max = 5, message = "Rating должен быть от 1 до 5")
    private Integer rating;

    @Size(max = 1000, message = "Отзыв не должен превышать 1000 символов")
    private String comment;
}
