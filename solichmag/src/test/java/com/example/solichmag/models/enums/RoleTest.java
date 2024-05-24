package com.example.solichmag.models.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleTest {

    @Test
    void testRoleUserAuthority() {
        Role role = Role.ROLE_USER;
        assertEquals("ROLE_USER", role.getAuthority());
    }

    @Test
    void testRoleAdminAuthority() {
        Role role = Role.ROLE_ADMIN;
        assertEquals("ROLE_ADMIN", role.getAuthority());
    }
}