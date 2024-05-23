package com.example.solichmag.services;

import com.example.solichmag.models.User;
import com.example.solichmag.models.enums.Role;
import com.example.solichmag.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_UserDoesNotExist_ReturnsTrue() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

        boolean result = userService.createUser(user);

        assertTrue(result);
        assertTrue(user.isActive());
        assertEquals("encodedPassword", user.getPassword());
        assertTrue(user.getRoles().contains(Role.ROLE_USER));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_UserExists_ReturnsFalse() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        boolean result = userService.createUser(user);

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void list_ReturnsAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.list();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void banUser_UserExists_ChangesActiveStatus() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setActive(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.banUser(1L);

        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);

        userService.banUser(1L);

        assertTrue(user.isActive());
        verify(userRepository, times(2)).save(user);
    }

    @Test
    void changeUserRoles_ChangesRolesCorrectly() {
        User user = new User();
        Map<String, String> form = new HashMap<>();
        form.put("ROLE_ADMIN", "ROLE_ADMIN");

        userService.changeUserRoles(user, form);

        assertTrue(user.getRoles().contains(Role.ROLE_ADMIN));
        assertFalse(user.getRoles().contains(Role.ROLE_USER));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserByPrincipal_WithPrincipal_ReturnsUser() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.getUserByPrincipal(principal);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getUserByPrincipal_WithoutPrincipal_ReturnsNewUser() {
        User result = userService.getUserByPrincipal(null);

        assertNotNull(result);
    }
}