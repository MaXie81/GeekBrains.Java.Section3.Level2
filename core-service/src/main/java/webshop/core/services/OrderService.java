package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webshop.core.entities.Order;
import webshop.core.entities.OrderItem;
import webshop.core.exceptions.ResourceNotFoundException;
import webshop.core.model.Cart;
import webshop.core.model.CartItem;
import webshop.core.entities.User;
import webshop.core.repositories.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final ProductService productService;

    public void createOrder(User user) {
        Order order = new Order();
        Cart cart = cartService.getCurrentCart();

        order.setUser(user);
        order.setTotalPrice(cart.getTotalPrice());

        orderRepository.save(order);

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(productService
                    .findById(cartItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + cartItem.getProductId())));
            orderItem.setOrder(order);

            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerProduct(cartItem.getPricePerProduct());
            orderItem.setPrice(cartItem.getPrice());

            orderItemService.createOrderItem(orderItem);
        }
    }
}
