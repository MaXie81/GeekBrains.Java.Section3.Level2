package webshop.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.of(productServiceWebClient
                .get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в продуктовом МС"))
                )
                .bodyToMono(ProductDto.class)
                .block()
        );
    }
}
