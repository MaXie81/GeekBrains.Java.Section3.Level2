package webshop.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.cart.integrations.ProductServiceIntegration;
import webshop.cart.model.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart cart;
    private HashMap<String, Cart> cartHashMap;

    @PostConstruct
    public void init() {
        cartHashMap = new HashMap<>();
    }

    public Cart getCurrentCart(String username) {
        if(!cartHashMap.containsKey(username)) {
            cartHashMap.put(username, new Cart());
        }
        return cartHashMap.get(username);
    }

    public void add(Long productId, String username) {
        cart = getCurrentCart(username);
        ProductDto product = productServiceIntegration
                .getProductById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Не удается добавить продукт с id: " + productId + " в корзину. Продукт не найден"));
        cart.add(product);
    }

    public void remove(Long productId, String username) {
        cart = getCurrentCart(username);
        cart.remove(productId);
    }

    public void clear(String username) {
        cart = getCurrentCart(username);
        cart.clear();
    }
}
