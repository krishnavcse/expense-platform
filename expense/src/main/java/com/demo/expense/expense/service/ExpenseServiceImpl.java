package com.demo.expense.expense.service;

import com.demo.expense.common.dto.ExpenseDto;
import com.demo.expense.common.events.ExpenseCreatedEvent;
import com.demo.expense.common.service.ExpenseService;
import com.demo.expense.expense.domain.Expense;
import com.demo.expense.expense.repository.ExpenseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repo;
    private final ApplicationEventPublisher events;

    public ExpenseServiceImpl(ExpenseRepository repo, ApplicationEventPublisher events) {
        this.repo = repo;
        this.events = events;
    }

    @Override
    @Transactional
    public ExpenseDto createExpense(ExpenseDto dto) {
        Expense e = new Expense();
        e.setUserId(dto.getUserId());
        e.setCategory(dto.getCategory());
        e.setAmount(dto.getAmount());
        e.setDescription(dto.getDescription());
        e.setDate(dto.getDate());
        e = repo.save(e);

        ExpenseDto saved = toDto(e);
        events.publishEvent(new ExpenseCreatedEvent(this, saved)); // internal async-ready event
        return saved;
    }

    @Override
    public ExpenseDto getById(Long id) {
        return repo.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<ExpenseDto> listByUser(Long userId) {
        return repo.findByUserId(userId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private ExpenseDto toDto(Expense e) {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(e.getId());
        dto.setUserId(e.getUserId());
        dto.setCategory(e.getCategory());
        dto.setAmount(e.getAmount());
        dto.setDescription(e.getDescription());
        dto.setDate(e.getDate());
        return dto;
    }
}