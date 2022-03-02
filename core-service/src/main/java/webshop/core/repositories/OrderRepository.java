package webshop.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webshop.core.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
