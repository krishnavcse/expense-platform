package com.demo.expense.common.service;

import com.demo.expense.common.dto.UserDto;
import java.util.Optional;

// Cross-module interface for user operations
public interface UserService {
    UserDto register(UserDto dto);
    Optional<UserDto> findByEmail(String email);
    Optional<UserDto> findById(Long id);
}