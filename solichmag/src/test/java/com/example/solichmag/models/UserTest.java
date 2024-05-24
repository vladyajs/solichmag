package com.example.solichmag.models;

import com.example.solichmag.models.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserInitialization() {
        Long id = 1L;
        String email = "test@example.com";
        String phoneNumber = "123456789";
        String name = "Test User";
        boolean active = true;
        Image avatar = new Image();
        String password = "password";
        Set<Role> roles = new HashSet<>(Collections.singletonList(Role.ROLE_USER));
        List<Product> products = Collections.singletonList(new Product());
        LocalDateTime dateOfCreated = LocalDateTime.now();

        User user = new User(id, email, phoneNumber, name, active, avatar, password, roles, products, dateOfCreated);

        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(name, user.getName());
        assertEquals(active, user.isActive());
        assertEquals(avatar, user.getAvatar());
        assertEquals(password, user.getPassword());
        assertEquals(roles, user.getRoles());
        assertEquals(products, user.getProducts());
        assertEquals(dateOfCreated, user.getDateOfCreated());
    }

    @Test
    void testUserIsAdmin() {
        User user = new User();
        user.setRoles(Collections.singleton(Role.ROLE_ADMIN));

        assertTrue(user.isAdmin());
    }

    @Test
    void testUserGetAuthorities() {
        User user = new User();
        Set<Role> roles = new HashSet<>(Collections.singletonList(Role.ROLE_USER));
        user.setRoles(roles);

        assertEquals(roles, user.getAuthorities());
    }

    @Test
    void testUserUsername() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        assertEquals(email, user.getUsername());
    }

    @Test
    void testUserAccountNonExpired() {
        User user = new User();
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testUserAccountNonLocked() {
        User user = new User();
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testUserCredentialsNonExpired() {
        User user = new User();
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testUserIsEnabled() {
        User user = new User();
        user.setActive(true);

        assertTrue(user.isEnabled());
    }
}
