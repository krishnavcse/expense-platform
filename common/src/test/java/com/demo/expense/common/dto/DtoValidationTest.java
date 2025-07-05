package com.demo.expense.common.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DtoValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void expenseDto_shouldFailOnMissingFields() {
        ExpenseDto dto = new ExpenseDto();
        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    void expenseDto_shouldPassOnValid() {
        ExpenseDto dto = new ExpenseDto();
        dto.setUserId(1L);
        dto.setCategory("FOOD");
        dto.setAmount(new BigDecimal("1.00"));
        dto.setDate(LocalDate.now());
        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void userDto_shouldValidateEmail() {
        UserDto dto = new UserDto();
        dto.setName("X");
        dto.setEmail("bad");
        assertFalse(validator.validate(dto).isEmpty());
        dto.setEmail("ok@example.com");
        AssertIsEmptyOrValid(dto);
    }

    private void AssertIsEmptyOrValid(UserDto dto) {
        assertTrue(validator.validate(dto).stream().noneMatch(v -> v.getPropertyPath().toString().equals("email")));
    }
}