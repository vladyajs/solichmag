package com.example.solichmag.controllers;

import com.example.solichmag.models.User;
import com.example.solichmag.models.enums.Role;
import com.example.solichmag.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class AdminControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void admin_ReturnsAdminView() throws Exception {
        when(userService.list()).thenReturn(Collections.emptyList());
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        mockMvc.perform(get("/admin").principal(principal))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));

        verify(userService, times(1)).list();
        verify(userService, times(1)).getUserByPrincipal(principal);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void userBan_RedirectsToAdmin() throws Exception {
        doNothing().when(userService).banUser(anyLong());

        mockMvc.perform(post("/admin/user/ban/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        verify(userService, times(1)).banUser(1L);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void userEdit_Get_ReturnsUserEditView() throws Exception {
        User user = new User();
        when(userService.getUserByPrincipal(principal)).thenReturn(new User());

        mockMvc.perform(get("/admin/user/edit/1").principal(principal).flashAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(view().name("user-edit"));

        verify(userService, times(1)).getUserByPrincipal(principal);
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void userEdit_Post_RedirectsToAdmin() throws Exception {
        User user = new User();
        Map<String, String> form = new HashMap<>();

        doNothing().when(userService).changeUserRoles(any(User.class), anyMap());

        mockMvc.perform(post("/admin/user/edit")
                        .flashAttr("userId", user)
                        .flashAttr("form", form))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        verify(userService, times(1)).changeUserRoles(any(User.class), anyMap());
    }
}