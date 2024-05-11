package ru.lebruce.store.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CreateReviewDTO {
    private Long productId;
    private Long userId;
    @Range(min = 1, max = 5)
    private int rating;
    private String comment;
}
