package webshop.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.api.OrderDto;
import shop.api.PageDto;
import webshop.core.converters.OrderConverter;
import webshop.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами клиентов")
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на формирование заказа клиента",
            responses = {
                    @ApiResponse(
                            description = "Заказ сформирован", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username) {
        orderService.createOrder(username);
    }

    @Operation(
            summary = "Запрос на получение всех заказов клиента по его логину в заголовке запроса",
            responses = {
                    @ApiResponse(
                            description = "Страница сформирована", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username) {
        return orderService.findByUsername(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}
