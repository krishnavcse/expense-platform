package com.demo.expense.expense.service;

import com.demo.expense.common.dto.ExpenseDto;
import com.demo.expense.common.events.ExpenseCreatedEvent;
import com.demo.expense.expense.domain.Expense;
import com.demo.expense.expense.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {
    private ExpenseRepository repo;
    private ApplicationEventPublisher events;
    private ExpenseServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(ExpenseRepository.class);
        events = mock(ApplicationEventPublisher.class);
        service = new ExpenseServiceImpl(repo, events);
    }

    @Test
    void createExpense_shouldSaveAndPublishEvent() {
        ExpenseDto dto = new ExpenseDto();
        dto.setUserId(1L);
        dto.setCategory("FOOD");
        dto.setAmount(new BigDecimal("9.99"));
        dto.setDescription("Lunch");
        dto.setDate(LocalDate.of(2024,1,1));

        Expense saved = new Expense();
        saved.setId(10L);
        saved.setUserId(dto.getUserId());
        saved.setCategory(dto.getCategory());
        saved.setAmount(dto.getAmount());
        saved.setDescription(dto.getDescription());
        saved.setDate(dto.getDate());

        when(repo.save(any(Expense.class))).thenReturn(saved);

        ExpenseDto out = service.createExpense(dto);

        assertEquals(10L, out.getId());
        assertEquals(1L, out.getUserId());
        ArgumentCaptor<Expense> entityCap = ArgumentCaptor.forClass(Expense.class);
        verify(repo).save(entityCap.capture());
        assertEquals("FOOD", entityCap.getValue().getCategory());

        ArgumentCaptor<ExpenseCreatedEvent> eventCap = ArgumentCaptor.forClass(ExpenseCreatedEvent.class);
        verify(events).publishEvent(eventCap.capture());
        assertEquals(10L, eventCap.getValue().getExpense().getId());
    }

    @Test
    void getById_shouldReturnDtoOrNull() {
        Expense e = new Expense();
        e.setId(5L);
        when(repo.findById(5L)).thenReturn(Optional.of(e));
        when(repo.findById(6L)).thenReturn(Optional.empty());

        assertNotNull(service.getById(5L));
        assertNull(service.getById(6L));
    }

    @Test
    void listByUser_shouldMapEntities() {
        Expense e = new Expense();
        e.setId(1L);
        e.setUserId(7L);
        when(repo.findByUserId(7L)).thenReturn(List.of(e));

        List<ExpenseDto> list = service.listByUser(7L);
        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());
        assertEquals(7L, list.get(0).getUserId());
    }
}