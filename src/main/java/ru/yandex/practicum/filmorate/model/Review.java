package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Review {
    private Long reviewId;
    private String content;
    private Boolean isPositive;
    private Long userId;
    private Long filmId;
    private Integer useful = 0;
}