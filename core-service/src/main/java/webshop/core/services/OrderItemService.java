package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webshop.core.entities.OrderItem;
import webshop.core.repositories.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void createOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
