package ru.yandex.practicum.filmorate.validator;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;

@Component
public class DirectorValidator {
    public void validate(Director director) {
        if (director == null) {
            throw new IllegalArgumentException("Director is null");
        }
        validateName(director.getName());
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Имя должно быть задано.");
        }
        if (name.isEmpty() || name.isBlank()) {
            throw new ValidationException("Имя не должно быть пустым.");
        }
    }
}