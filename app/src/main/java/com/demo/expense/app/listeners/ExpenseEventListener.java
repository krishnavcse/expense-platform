package com.demo.expense.app.listeners;

import com.demo.expense.common.events.ExpenseCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExpenseEventListener {
    private static final Logger log = LoggerFactory.getLogger(ExpenseEventListener.class);

    @Async
    @EventListener
    public void onExpenseCreated(ExpenseCreatedEvent event) {
        // Placeholder for async workflows (e.g., notify, audit, analytics)
        log.info("Expense created event received: id={}, userId={}",
                event.getExpense().getId(), event.getExpense().getUserId());
    }
}