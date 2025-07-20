package com.demo.expense.app.controller;

import com.demo.expense.common.dto.UserDto;
import com.demo.expense.common.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Test
    void register_shouldReturnSavedUser() throws Exception {
        UserService svc = Mockito.mock(UserService.class);
        AuthController controller = new AuthController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        UserDto saved = new UserDto(1L, "Alex", "a@b.com");
        when(svc.register(any())).thenReturn(saved);

        String json = "{\"name\":\"Alex\",\"email\":\"a@b.com\",\"password\":\"x\"}";
        mvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("a@b.com"));
    }

    @Test
    void login_shouldReturnTokenOnSuccess() throws Exception {
        UserService svc = Mockito.mock(UserService.class);
        AuthController controller = new AuthController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(svc.findByEmail("a@b.com")).thenReturn(Optional.of(new UserDto(1L, "A", "a@b.com")));

        String json = "{\"email\":\"a@b.com\"}";
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void login_should401WhenUserNotFound() throws Exception {
        UserService svc = Mockito.mock(UserService.class);
        AuthController controller = new AuthController(svc);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(svc.findByEmail("no@x.com")).thenReturn(Optional.empty());

        String json = "{\"email\":\"no@x.com\"}";
        mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isUnauthorized());
    }
}