package com.financescript.springapp.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    EmailValidator emailValidator;
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        emailValidator = new EmailValidator();
    }

    @Test
    void isValidEmailAccepted() {
        assertTrue(emailValidator.isValid("hochan.lee@financescript.com", constraintValidatorContext));
        assertTrue(emailValidator.isValid("it-help@financescript.com", constraintValidatorContext));
        assertTrue(emailValidator.isValid("test0123@gmail.com", constraintValidatorContext));
        assertTrue(emailValidator.isValid("applicant@baruch.cuny.edu", constraintValidatorContext));
        assertTrue(emailValidator.isValid("r.a.n.d.o.m@asjdflajf.afds", constraintValidatorContext));
    }

    @Test
    void isInvalidEmailDenied() {
        assertFalse(emailValidator.isValid("no-at-sign", constraintValidatorContext));
        assertFalse(emailValidator.isValid("invalid_#letter@gmail.com", constraintValidatorContext));
        assertFalse(emailValidator.isValid("double_at@_mail@yahoo.com", constraintValidatorContext));
    }
}