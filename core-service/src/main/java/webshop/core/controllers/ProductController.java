package webshop.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.api.FilterDto;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.core.converters.ProductConverter;
import webshop.core.entities.Product;
import webshop.core.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public List<ProductDto> findAllProducts() {
        return productService.findAll().stream().map(productConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        Product p = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
        return productConverter.entityToDto(p);
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);
    }

    @PostMapping("/filter")
    public List<ProductDto> createNewProduct(@RequestBody FilterDto filterDto) {
        System.out.println(filterDto.getTitle() + "/" + filterDto.getMinPrice() + "/" + filterDto.getMaxPrice());
        return productService.getAllProductByFilter(filterDto)
                .stream()
                .map(productConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
