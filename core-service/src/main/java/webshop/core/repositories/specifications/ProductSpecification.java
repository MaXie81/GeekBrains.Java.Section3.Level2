package webshop.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import webshop.core.entities.Product;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> priceGreaterOrEqualsThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanOrEqualsThan(BigDecimal price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }
}
