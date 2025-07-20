package com.demo.expense.app.controller;

import com.demo.expense.common.dto.ExpenseDto;
import com.demo.expense.common.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExpenseControllerTest {

    @Test
    void create_shouldReturnCreatedDto() {
        ExpenseService svc = Mockito.mock(ExpenseService.class);
        ExpenseController controller = new ExpenseController(svc);

        Authentication auth = new UsernamePasswordAuthenticationToken("1", null);

        ExpenseDto input = new ExpenseDto();
        input.setCategory("FOOD");
        input.setAmount(new BigDecimal("12.50"));
        input.setDescription("Lunch");
        input.setDate(LocalDate.of(2024,1,1));

        ExpenseDto resp = new ExpenseDto();
        resp.setId(10L);
        resp.setUserId(1L);
        resp.setCategory("FOOD");
        resp.setAmount(new BigDecimal("12.50"));
        resp.setDate(LocalDate.of(2024,1,1));

        when(svc.createExpense(any())).thenReturn(resp);

        ExpenseDto out = controller.create(input, auth);
        assertEquals(10L, out.getId());
        assertEquals(1L, out.getUserId());
    }

    @Test
    void list_shouldReturnMyExpenses() {
        ExpenseService svc = Mockito.mock(ExpenseService.class);
        ExpenseController controller = new ExpenseController(svc);

        Authentication auth = new UsernamePasswordAuthenticationToken("1", null);

        ExpenseDto dto = new ExpenseDto();
        dto.setId(1L);
        dto.setUserId(1L);
        dto.setCategory("FOOD");
        dto.setAmount(new BigDecimal("5"));
        dto.setDate(LocalDate.now());
        when(svc.listByUser(1L)).thenReturn(List.of(dto));

        List<ExpenseDto> list = controller.myExpenses(auth);
        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());
    }
}