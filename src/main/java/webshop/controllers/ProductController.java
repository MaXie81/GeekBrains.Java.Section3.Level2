package webshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webshop.components.Cart;
import webshop.entities.Product;
import webshop.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final Cart cart;

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id) {
        return productService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/cart")
    public List<Product> getCart() {
        return cart.getProductList();
    }

    @GetMapping("/cart/{id}")
    public void addProductToCartById(@PathVariable Long id) {
        Product product;
        product = productService.findById(id).get();
        if (product != null) {
            cart.addProduct(product);
        }
    }
}