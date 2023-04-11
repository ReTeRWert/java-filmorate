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
            throw new ValidationException("Текст отзыва не может быть пустым.");
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

    private void validateFilmId(Integer filmId) {
        if (filmId == null) {
            throw new IllegalArgumentException("Идентификатор фильма не может быть пустым.");
        }
    }

    private void validateUserId(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Идентификатор пользователя не может быть пустым.");
        }
    }
}
