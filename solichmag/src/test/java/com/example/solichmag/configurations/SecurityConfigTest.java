package com.example.solichmag.configurations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void contextLoads() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcRequestPostProcessors.springSecurity()).build();
    }

    @Test
    void testPasswordEncoder() {
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assert passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Test
    void testPublicUrlsAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(formLogin().loginProcessingUrl("/login"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        mockMvc.perform(formLogin().loginProcessingUrl("/product/"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        mockMvc.perform(formLogin().loginProcessingUrl("/images/"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());

        mockMvc.perform(formLogin().loginProcessingUrl("/registration"))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }

    @Test
    @WithMockUser
    void testAuthenticatedUrls() throws Exception {
        mockMvc.perform(formLogin().loginProcessingUrl("/user/home"))
                .andExpect(status().isOk())
                .andExpect(authenticated());

        mockMvc.perform(logout().logoutUrl("/logout").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(unauthenticated());
    }
}