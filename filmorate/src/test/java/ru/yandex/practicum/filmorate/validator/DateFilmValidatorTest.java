package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.AnnotationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DateFilmValidatorTest {

    private static DateFilmValidator validatorDataFilm;
    private static DateFilm dataFilmChecker;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private DateFilm createAnnotation(String value, String message) {
        final Map<String, Object> attrs = new HashMap<>();
        if (null != value) {
            attrs.put("value", value);
        }
        if (null != message) {
            attrs.put("message", message);
        }
        return AnnotationFactory.create(new AnnotationDescriptor.Builder<>(DateFilm.class, attrs).build());
    }

    @BeforeEach
    void beforeEach() {
        validatorDataFilm = new DateFilmValidator();
        dataFilmChecker = createAnnotation("1895-12-28", "дата релиза — не раньше 28 декабря 1895 года");
    }

    @DisplayName("Date check")
    @ParameterizedTest
    @CsvSource({"1780-01-01, false, then before", "1985-12-27, false, then before", "1985-12-28, true, then equals", "2000-01-01, true, then after"
    })
    void dateCheck(LocalDate testDate, boolean isCorrect, String message) {
        validatorDataFilm.initialize(dataFilmChecker);
        Assertions.assertEquals(isCorrect, validatorDataFilm.isValid(testDate, constraintValidatorContext), message);
    }
}
