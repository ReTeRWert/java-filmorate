package ru.yandex.practicum.filmorate.validator;


import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.AnnotationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Map;

class LoginValidatorTest {
    private LoginValidator loginValidator;
    private Login login;
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    private Login createAnnotation(String message) {
        final Map<String, Object> attrs = new HashMap<>();
        if (null != message) {
            attrs.put("message", message);
        }
        var desc = new AnnotationDescriptor
                .Builder<>(Login.class, attrs)
                .build();
        return AnnotationFactory.create(desc);
    }

    @BeforeEach
    void beforeEachTest() {
        loginValidator = new LoginValidator();
        login = createAnnotation("Can't use space");
    }

    @DisplayName("Space Validation")
    @ParameterizedTest
    @CsvSource({
            "' ' , false",
            "'\t', false",
            "' Login', false",
            "'Login ', false",
            "'Log in', false",
            "'L ogin', false",
            "'Login', true"
    }
    )
    void spaceValidation(String testName, boolean isCorrect) {
        loginValidator.initialize(login);

        Assertions.assertEquals(isCorrect
                , loginValidator.isValid(testName, constraintValidatorContext));
    }
}
