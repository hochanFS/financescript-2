package com.financescript.springapp.validation;

import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class FieldMatchValidatorTest {

    final String FIRST = "first";
    final String SECOND = "second";
    final String MESSAGE = "message";

    @Getter
    @FieldMatch.List({
            @FieldMatch(first = FIRST, second = SECOND, message = MESSAGE)
    })
    public class SampleClass {
        String first;
        String second;

        public SampleClass(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

    final String INPUT1 = "abc";
    final String INPUT2 = "abd";

    @Mock
    FieldMatch constraintAnnotation;

    @Mock
    ConstraintValidatorContext constraintValidatorContext;
    FieldMatchValidator validator;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder builder;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // when
        validator = new FieldMatchValidator();
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(builder);
        when(builder.addPropertyNode(anyString())).thenReturn(nodeContext);
        when(nodeContext.addConstraintViolation()).thenReturn(constraintValidatorContext);
        when(constraintAnnotation.first()).thenReturn(FIRST);
        when(constraintAnnotation.second()).thenReturn(SECOND);
        when(constraintAnnotation.message()).thenReturn(MESSAGE);
        validator.initialize(constraintAnnotation);
    }

    @Test
    void initialize() {
        verify(constraintAnnotation, times(1)).first();
        verify(constraintAnnotation, times(1)).second();
        verify(constraintAnnotation, times(1)).message();

    }

    @Test
    void isValidObjectAccepted() {
        SampleClass sampleClass = new SampleClass(INPUT1, INPUT1);
        assertTrue(validator.isValid(sampleClass, constraintValidatorContext));
    }

    @Test
    void isInvalidObjectDenied() {
        SampleClass sampleClass = new SampleClass(INPUT1, INPUT2);
        assertFalse(validator.isValid(sampleClass, constraintValidatorContext));
    }
}