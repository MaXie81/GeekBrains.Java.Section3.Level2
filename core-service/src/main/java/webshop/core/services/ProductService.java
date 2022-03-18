package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.api.FilterDto;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.core.entities.Category;
import webshop.core.entities.Product;
import webshop.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }

    public List<Product> getAllProductByFilter(FilterDto filterDto) {
        System.out.println(filterDto.getTitle() == null ? "NULL" : filterDto.getTitle());
        System.out.println(filterDto.getMinPrice() == null ? "NULL" : filterDto.getMinPrice());
        System.out.println(filterDto.getMaxPrice() == null ? "NULL" : filterDto.getMaxPrice());
        return productRepository.getAllProductByFilter(filterDto.getTitle(), filterDto.getMinPrice(), filterDto.getMaxPrice());
    }
}
