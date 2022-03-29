package webshop.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.api.CartDto;
import webshop.cart.convertes.CartConverter;
import webshop.cart.services.CartService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id, @RequestHeader(required = false) String username) {
        cartService.add(id, Optional.ofNullable(username).orElse(""));
    }

    @GetMapping("/clear")
    public void clearCart(@RequestHeader(required = false) String username) {
        cartService.clear(Optional.ofNullable(username).orElse(""));
    }

    @GetMapping("/remove/{id}")
    public void removeFromCart(@PathVariable Long id, @RequestHeader(required = false) String username) {
        cartService.remove(id, Optional.ofNullable(username).orElse(""));
    }

    @GetMapping
    public CartDto getCurrentCart(@RequestHeader(required = false) String username) {
        return cartConverter.entityToDto(cartService.getCurrentCart(Optional.ofNullable(username).orElse("")));
    }
}
