package com.demo.expense.common.events;

import com.demo.expense.common.dto.ExpenseDto;
import org.springframework.context.ApplicationEvent;

// Spring domain event to decouple modules
public class ExpenseCreatedEvent extends ApplicationEvent {
    private final ExpenseDto expense;

    public ExpenseCreatedEvent(Object source, ExpenseDto expense) {
        super(source);
        this.expense = expense;
    }

    public ExpenseDto getExpense() {
        return expense;
    }
}