package com.example.solichmag.controllers;

import com.example.solichmag.models.Product;
import com.example.solichmag.models.User;
import com.example.solichmag.services.ProductService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void productsTest() {
        String searchWord = "test";
        User user = new User();
        when(productService.listProducts(searchWord)).thenReturn(Collections.emptyList());
        when(productService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = productController.products(searchWord, principal, model);

        verify(model, times(1)).addAttribute("products", Collections.emptyList());
        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("searchWord", searchWord);
        assertEquals("products", viewName);
    }

    @Test
    void productInfoTest() {
        Long productId = 1L;
        Product product = new Product();
        User user = new User();
        when(productService.getProductById(productId)).thenReturn(product);
        when(productService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = productController.productInfo(productId, model, principal);

        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("product", product);
        verify(model, times(1)).addAttribute("images", product.getImages());
        verify(model, times(1)).addAttribute("authorProduct", product.getUser());
        assertEquals("product-info", viewName);
    }

    @Test
    void createProductTest() throws IOException {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        MultipartFile file3 = mock(MultipartFile.class);
        Product product = new Product();
        doNothing().when(productService).saveProduct(any(Principal.class), any(Product.class), any(MultipartFile.class), any(MultipartFile.class), any(MultipartFile.class));

        String viewName = productController.createProduct(file1, file2, file3, product, principal);

        verify(productService, times(1)).saveProduct(principal, product, file1, file2, file3);
        assertEquals("redirect:/my/products", viewName);
    }

    @Test
    void deleteProductTest() {
        Long productId = 1L;
        User user = new User();
        when(productService.getUserByPrincipal(principal)).thenReturn(user);
        doNothing().when(productService).deleteProduct(any(User.class), anyLong());

        String viewName = productController.deleteProduct(productId, principal);

        verify(productService, times(1)).deleteProduct(user, productId);
        assertEquals("redirect:/my/products", viewName);
    }

    @Test
    void userProductsTest() {
        User user = new User();
        user.setProducts(Collections.emptyList());
        when(productService.getUserByPrincipal(principal)).thenReturn(user);

        String viewName = productController.userProducts(principal, model);

        verify(model, times(1)).addAttribute("user", user);
        verify(model, times(1)).addAttribute("products", user.getProducts());
        assertEquals("my-products", viewName);
    }
}
