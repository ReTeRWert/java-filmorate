package ru.yandex.practicum.filmorate.validator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;

@Component
public class ReviewValidator {

    public void validate(Review review) {
        validateContent(review.getContent());
        validateIsPositive(review.getIsPositive());
        validateFilmId(review.getFilmId());
        validateUserId(review.getUserId());
    }

    private void validateContent(String content) {
        if (content == null) {
            throw new ValidationException("Отзыв не может быть пустым");
        }
        if (content.isEmpty()) {
            throw new IllegalArgumentException("Текст отзыва не может быть пустым.");
        }
    }

    private void validateIsPositive(Boolean isPositive) {
        if (isPositive == null) {
            throw new ValidationException("Оценка не может быть пустой.");
        }
    }

    private void validateFilmId(Long filmId) {
        if (filmId == null) {
            throw new ValidationException("Фильм не указан");
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null) {
            throw new ValidationException("Пользователь не указан");
        }
    }
}
