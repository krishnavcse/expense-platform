package com.demo.expense.user.service;

import com.demo.expense.common.dto.UserDto;
import com.demo.expense.common.service.UserService;
import com.demo.expense.user.domain.User;
import com.demo.expense.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public UserDto register(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user = userRepository.save(user);
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(u -> new UserDto(u.getId(), u.getName(), u.getEmail()));
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(u -> new UserDto(u.getId(), u.getName(), u.getEmail()));
    }
}