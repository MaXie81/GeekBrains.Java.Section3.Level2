package webshop.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.core.entities.Category;
import webshop.core.entities.Product;
import webshop.core.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice(), product.getCategory().getTitle());
    }

    public Product dtoToEntity(ProductDto productDto) {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());
        Category c = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(() -> new ResourceNotFoundException("Категория не найдена"));
        p.setCategory(c);
        return p;
    }
}
