package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.api.CartDto;
import webshop.core.entities.Order;
import webshop.core.entities.OrderItem;
import webshop.core.integrations.CartServiceIntegration;
import webshop.core.repositories.OrderRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

    @Transactional
    public void createOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getCurrentCart();
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream().map(
                cartItem -> new OrderItem(
                        productService.findById(cartItem.getProductId()).get(),
                        order,
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()
                )
        ).collect(Collectors.toList()));
        orderRepository.save(order);
        cartServiceIntegration.clear();
    }
}
