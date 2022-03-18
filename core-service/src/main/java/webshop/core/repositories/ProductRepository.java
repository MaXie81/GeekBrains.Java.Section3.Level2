package webshop.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webshop.core.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.title like nvl(:title, '') || '%' and p.price between nvl(:minPrice, p.price) and nvl(:maxPrice, p.price)")
//    @Query("select p from Product p where p.title like :title || '%' and p.price between :minPrice and :maxPrice")
    List<Product> getAllProductByFilter(String title, BigDecimal minPrice, BigDecimal maxPrice);
}
