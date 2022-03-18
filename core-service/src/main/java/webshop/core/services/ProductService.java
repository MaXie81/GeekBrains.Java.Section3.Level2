package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import shop.api.FilterDto;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.core.entities.Category;
import webshop.core.entities.Product;
import webshop.core.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

import static webshop.core.repositories.specifications.ProductSpecification.greaterOrEqualMinPrice;
import static webshop.core.repositories.specifications.ProductSpecification.lessOrEqualMaxPrice;
import static webshop.core.repositories.specifications.ProductSpecification.likeByTitle;

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
        Specification<Product> andPoolOfPridicates = likeByTitle(filterDto.getTitle() == null ? "" : filterDto.getTitle());
        if (filterDto.getMinPrice() != null) andPoolOfPridicates = andPoolOfPridicates.and(greaterOrEqualMinPrice(filterDto.getMinPrice()));
        if (filterDto.getMaxPrice() != null) andPoolOfPridicates = andPoolOfPridicates.and(lessOrEqualMaxPrice(filterDto.getMaxPrice()));
        return productRepository.findAll(andPoolOfPridicates);
    }
}
