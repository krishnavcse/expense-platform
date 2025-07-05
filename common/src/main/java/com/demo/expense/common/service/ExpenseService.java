package com.demo.expense.common.service;

import com.demo.expense.common.dto.ExpenseDto;
import java.util.List;

// Cross-module interface for expense operations
public interface ExpenseService {
    ExpenseDto createExpense(ExpenseDto dto);
    ExpenseDto getById(Long id);
    List<ExpenseDto> listByUser(Long userId);
}