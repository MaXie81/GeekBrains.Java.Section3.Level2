package webshop.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import webshop.core.entities.Product;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> likeByTitle(String title) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }
    public static Specification<Product> greaterOrEqualMinPrice(BigDecimal minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.ge(root.get("price"), minPrice);
    }
    public static Specification<Product> lessOrEqualMaxPrice(BigDecimal maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.le(root.get("price"), maxPrice);
    }
}
