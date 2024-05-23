package com.example.solichmag.controllers;

import com.example.solichmag.models.Image;
import com.example.solichmag.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageController imageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getImageById_ImageExists_ReturnsImage() throws Exception {
        Long imageId = 1L;
        Image image = new Image();
        image.setId(imageId);
        image.setOriginalFileName("test.jpg");
        image.setContentType("image/jpeg");
        image.setSize(100L);
        image.setBytes(new byte[]{1, 2, 3});
        
        when(imageRepository.findById(imageId)).thenReturn(Optional.of(image));

        mockMvc.perform(get("/images/{id}", imageId))
                .andExpect(status().isOk())
                .andExpect(header().string("filename", "test.jpg"))
                .andExpect(content().contentType(MediaType.valueOf("image/jpeg")))
                .andExpect(content().bytes(image.getBytes()));
    }

    @Test
    void getImageById_ImageDoesNotExist_ReturnsNotFound() throws Exception {
        Long imageId = 1L;
        
        when(imageRepository.findById(imageId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/images/{id}", imageId))
                .andExpect(status().isNotFound());
    }
}