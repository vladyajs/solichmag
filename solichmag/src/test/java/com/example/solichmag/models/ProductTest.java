package com.example.solichmag.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void testProductInitialization() {
        Long id = 1L;
        String title = "Test Product";
        String description = "This is a test product";
        int price = 100;
        String city = "Test City";
        List<Image> images = new ArrayList<>();
        Long previewImageId = 1L;
        User user = new User();
        LocalDateTime dateOfCreated = LocalDateTime.now();

        Product product = new Product(id, title, description, price, city, images, previewImageId, user, dateOfCreated);

        assertEquals(id, product.getId());
        assertEquals(title, product.getTitle());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(city, product.getCity());
        assertEquals(images, product.getImages());
        assertEquals(previewImageId, product.getPreviewImageId());
        assertEquals(user, product.getUser());
        assertEquals(dateOfCreated, product.getDateOfCreated());
    }

    @Test
    void testAddImageToProduct() {
        Product product = new Product();
        Image image = new Image();

        product.addImageToProduct(image);

        assertTrue(product.getImages().contains(image));
        assertEquals(product, image.getProduct());
    }

    @Test
    void testDateOfCreatedInitialization() {
        Product product = new Product();

        LocalDateTime beforeInit = LocalDateTime.now();
        LocalDateTime dateOfCreated = product.getDateOfCreated();
        LocalDateTime afterInit = LocalDateTime.now();

        assertTrue(dateOfCreated.isAfter(beforeInit) && dateOfCreated.isBefore(afterInit));
    }
}
