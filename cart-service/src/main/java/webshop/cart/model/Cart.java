package webshop.cart.model;

import lombok.Data;
import shop.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Long productId, ProductDtoById productDto) {
        for (CartItem item : items) {
            if (productId.equals(item.getProductId())) {
                item.changeQuantity(1);
                recalculate();
                return;
            }
        }
        ProductDto product = productDto.get(productId);
        items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        recalculate();
    }

    public void addItem(CartItem item) {
        CartItem cartItem = items.stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            items.add(item);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
            cartItem.setPrice(cartItem.getPrice().add(item.getPrice()));
        }
        recalculate();
    }

    public void remove(Long productId) {
        if (items.removeIf(item -> item.getProductId().equals(productId))) {
            recalculate();
        }
    }

    public void clear() {
        items.clear();
        totalPrice = new BigDecimal(0);
    }

    private void recalculate() {
        totalPrice = new BigDecimal(0);
        for (CartItem item : items) {
            totalPrice = totalPrice.add(item.getPrice());
        }
    }
}
