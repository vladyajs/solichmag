package com.example.solichmag.services;

import com.example.solichmag.models.Image;
import com.example.solichmag.models.Product;
import com.example.solichmag.models.User;
import com.example.solichmag.repositories.ProductRepository;
import com.example.solichmag.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listProducts_WithTitle_ReturnsProductList() {
        String title = "Test Title";
        List<Product> products = Collections.singletonList(new Product());
        when(productRepository.findByTitle(title)).thenReturn(products);

        List<Product> result = productService.listProducts(title);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findByTitle(title);
    }

    @Test
    void listProducts_WithoutTitle_ReturnsAllProducts() {
        List<Product> products = Collections.singletonList(new Product());
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.listProducts(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void saveProduct_WithValidData_SavesProduct() throws IOException {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        Product product = new Product();
        MultipartFile file = mock(MultipartFile.class);
        when(file.getSize()).thenReturn(100L);
        when(file.getBytes()).thenReturn(new byte[]{});
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.getContentType()).thenReturn("image/jpeg");

        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.saveProduct(principal, product, file, null, null);

        verify(productRepository, times(2)).save(any(Product.class));
        assertEquals(user, product.getUser());
        assertNotNull(product.getImages());
        assertEquals(1, product.getImages().size());
        assertTrue(product.getImages().get(0).isPreviewImage());
    }

    @Test
    void getUserByPrincipal_WithPrincipal_ReturnsUser() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = productService.getUserByPrincipal(principal);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getUserByPrincipal_WithoutPrincipal_ReturnsNewUser() {
        User result = productService.getUserByPrincipal(null);

        assertNotNull(result);
    }

    @Test
    void deleteProduct_WithValidUserAndProduct_DeletesProduct() {
        User user = new User();
        user.setId(1L);
        Product product = new Product();
        product.setId(1L);
        product.setUser(user);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(user, 1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void getProductById_WithValidId_ReturnsProduct() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getProductById_WithInvalidId_ReturnsNull() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(1L);

        assertNull(result);
    }
}