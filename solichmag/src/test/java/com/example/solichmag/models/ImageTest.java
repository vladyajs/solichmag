package com.example.solichmag.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImageTest {

    @Test
    void testImageInitialization() {
        Long id = 1L;
        String name = "testImage";
        String originalFileName = "testFile.jpg";
        Long size = 1024L;
        String contentType = "image/jpeg";
        boolean isPreviewImage = false;
        byte[] bytes = new byte[1024];
        Product product = new Product();

        Image image = new Image(id, name, originalFileName, size, contentType, isPreviewImage, bytes, product);

        assertEquals(id, image.getId());
        assertEquals(name, image.getName());
        assertEquals(originalFileName, image.getOriginalFileName());
        assertEquals(size, image.getSize());
        assertEquals(contentType, image.getContentType());
        assertEquals(isPreviewImage, image.isPreviewImage());
        assertArrayEquals(bytes, image.getBytes());
        assertEquals(product, image.getProduct());
    }

    @Test
    void testImageSetterMethods() {
        Image image = new Image();

        Long id = 1L;
        String name = "testImage";
        String originalFileName = "testFile.jpg";
        Long size = 1024L;
        String contentType = "image/jpeg";
        boolean isPreviewImage = false;
        byte[] bytes = new byte[1024];
        Product product = new Product();

        image.setId(id);
        image.setName(name);
        image.setOriginalFileName(originalFileName);
        image.setSize(size);
        image.setContentType(contentType);
        image.setPreviewImage(isPreviewImage);
        image.setBytes(bytes);
        image.setProduct(product);

        assertEquals(id, image.getId());
        assertEquals(name, image.getName());
        assertEquals(originalFileName, image.getOriginalFileName());
        assertEquals(size, image.getSize());
        assertEquals(contentType, image.getContentType());
        assertEquals(isPreviewImage, image.isPreviewImage());
        assertArrayEquals(bytes, image.getBytes());
        assertEquals(product, image.getProduct());
    }
}
