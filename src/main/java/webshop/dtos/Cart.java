package webshop.dtos;

import lombok.Data;
import webshop.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void add(Product product) {
        CartItem cartItem = items.stream()
                .filter(item -> item.getProductId() == product.getId())
                .findFirst()
                .orElse(new CartItem(product.getId(), product.getTitle(), 0, product.getPrice(), 0));

        if (cartItem.getQuantity() == 0) items.add(cartItem);

        cartItem.inc();
        recalculate();
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
    }
}
