package webshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webshop.entities.OrderItem;
import webshop.repositories.OrderItemRepository;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void createOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
