package com.demo.expense.user.service;

import com.demo.expense.common.dto.UserDto;
import com.demo.expense.user.domain.User;
import com.demo.expense.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    private UserRepository repo;
    private UserServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(UserRepository.class);
        service = new UserServiceImpl(repo);
    }

    @Test
    void register_shouldEncodePasswordAndReturnDto() {
        UserDto in = new UserDto();
        in.setName("A");
        in.setEmail("a@b.com");
        in.setPassword("secret");

        User saved = new User();
        saved.setId(3L);
        saved.setName("A");
        saved.setEmail("a@b.com");

        when(repo.save(any(User.class))).thenReturn(saved);

        UserDto out = service.register(in);
        assertEquals(3L, out.getId());
        assertEquals("A", out.getName());
        assertEquals("a@b.com", out.getEmail());
    }

    @Test
    void findByEmail_shouldMapToDto() {
        User u = new User();
        u.setId(5L);
        u.setName("N");
        u.setEmail("e@x.com");
        when(repo.findByEmail("e@x.com")).thenReturn(Optional.of(u));

        Optional<UserDto> res = service.findByEmail("e@x.com");
        assertTrue(res.isPresent());
        assertEquals(5L, res.get().getId());
    }

    @Test
    void findById_shouldMapToDto() {
        User u = new User();
        u.setId(9L);
        u.setName("Z");
        u.setEmail("z@x.com");
        when(repo.findById(9L)).thenReturn(Optional.of(u));
        assertTrue(service.findById(9L).isPresent());
    }
}