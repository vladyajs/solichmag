package com.example.solichmag.controllers;

import com.example.solichmag.models.User;
import com.example.solichmag.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void loginTest() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = userController.login(principal, model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("login", viewName);
    }

    @Test
    void profileTest() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = userController.profile(principal, model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("profile", viewName);
    }

    @Test
    void registrationGetTest() {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = userController.registration(principal, model);

        verify(model, times(1)).addAttribute("user", user);
        assertEquals("registration", viewName);
    }

    @Test
    void registrationPostTestSuccess() {
        User user = new User();
        when(userService.createUser(any(User.class))).thenReturn(true);

        String viewName = userController.createUser(user, model);

        verify(userService, times(1)).createUser(user);
        assertEquals("redirect:/login", viewName);
    }

    @Test
    void registrationPostTestFailure() {
        User user = new User();
        user.setEmail("test@example.com");
        when(userService.createUser(any(User.class))).thenReturn(false);

        String viewName = userController.createUser(user, model);

        verify(userService, times(1)).createUser(user);
        verify(model, times(1)).addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
        assertEquals("registration", viewName);
    }

    @Test
    void userInfoTest() {
        User user = new User();
        User principalUser = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(principalUser);

        String viewName = userController.userInfo(user, model, principal);

        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("userByPrincipal", principalUser);
        verify(model, times(1)).addAttribute("products", user.getProducts());
        assertEquals("user-info", viewName);
    }
}