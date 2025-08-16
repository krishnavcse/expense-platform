package com.demo.expense.app.listeners;

import com.demo.expense.common.dto.ExpenseDto;
import com.demo.expense.common.events.ExpenseCreatedEvent;
import org.junit.jupiter.api.Test;

class ExpenseEventListenerTest {

    @Test
    void onExpenseCreated_shouldNotThrow() {
        ExpenseEventListener listener = new ExpenseEventListener();
        ExpenseDto dto = new ExpenseDto();
        dto.setId(1L);
        dto.setUserId(2L);
        listener.onExpenseCreated(new ExpenseCreatedEvent(this, dto));
        // No assertions; ensure no exception and method is callable
    }
}