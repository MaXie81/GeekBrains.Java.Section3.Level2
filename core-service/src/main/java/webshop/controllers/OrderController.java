package webshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import webshop.entities.User;
import webshop.exceptions.ResourceNotFoundException;
import webshop.services.OrderService;
import webshop.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal) {
        User user = userService
                .findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден, name: " + principal.getName()));

        orderService.createOrder(user);
    }
}
