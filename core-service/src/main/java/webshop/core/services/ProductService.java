package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.core.entities.Category;
import webshop.core.entities.Product;
import webshop.core.repositories.ProductRepository;
import webshop.core.repositories.specifications.ProductSpecification;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Page<Product> findAll(Specification<Product> spec, int page) {
        return productRepository.findAll(spec, PageRequest.of(page, 5));
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

    public Specification<Product> createSpecByFilters(BigDecimal minPrice, BigDecimal maxPrice, String title) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.priceLessThanOrEqualsThan(maxPrice));
        }
        if (title != null) {
            spec = spec.and(ProductSpecification.titleLike(title));
        }
        return spec;
    }
}
