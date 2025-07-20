package com.demo.expense.app.controller;

import com.demo.expense.common.dto.ExpenseDto;
import com.demo.expense.common.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseDto create(@Valid @RequestBody ExpenseDto dto, Authentication auth) {
        // userId comes from token subject
        dto.setUserId(Long.valueOf(auth.getName()));
        return expenseService.createExpense(dto);
    }

    @GetMapping("/{id}")
    public ExpenseDto get(@PathVariable Long id) {
        return expenseService.getById(id);
    }

    @GetMapping
    public List<ExpenseDto> myExpenses(Authentication auth) {
        Long userId = Long.valueOf(auth.getName());
        return expenseService.listByUser(userId);
    }
}