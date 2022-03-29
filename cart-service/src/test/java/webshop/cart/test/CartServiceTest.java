package webshop.cart.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.api.ProductDto;
import webshop.cart.integrations.ProductServiceIntegration;
import webshop.cart.model.Cart;
import webshop.cart.services.CartService;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @BeforeEach
    public void clear() {
        cartService.clear(null);
    }

    @Test
    public void all() {
        Cart cart = cartService.getCurrentCart(null);

        ProductDto productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setTitle("Тестовый продукт 1");
        productDto1.setPrice(BigDecimal.valueOf(34.55));
        productDto1.setCategoryTitle("Тестовая категория");

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setTitle("Тестовый продукт 2");
        productDto2.setPrice(BigDecimal.valueOf(26.44));
        productDto2.setCategoryTitle("Тестовая категория");

        ProductDto productDto3 = new ProductDto();
        productDto3.setId(3L);
        productDto3.setTitle("Тестовый продукт 3");
        productDto3.setPrice(BigDecimal.valueOf(15.00));
        productDto3.setCategoryTitle("Тестовая категория");

        Mockito.doReturn(Optional.of(productDto1)).when(productServiceIntegration).getProductById(1L);
        Mockito.doReturn(Optional.of(productDto2)).when(productServiceIntegration).getProductById(2L);
        Mockito.doReturn(Optional.of(productDto3)).when(productServiceIntegration).getProductById(3L);

        cartService.add(1L, null);
        cartService.add(2L, null);
        cartService.add(3L, null);
        cartService.add(2L, null);

        Assertions.assertEquals(3, cart.getItems().size());
        Assertions.assertEquals(BigDecimal.valueOf(102.43), cart.getTotalPrice());
        Assertions.assertEquals(4, cart.getItems().stream()
                .map(cartItem -> cartItem.getQuantity())
                .reduce((sum, x) -> sum += x)
                .get()
        );

        cartService.remove(2L, null);

        Assertions.assertEquals(2, cart.getItems().size());
        Assertions.assertEquals(2, cart.getItems().stream()
                .map(cartItem -> cartItem.getQuantity())
                .reduce((sum, x) -> sum += x)
                .get()
        );

        cartService.clear(null);

        Assertions.assertEquals(0, cart.getItems().size());
    }
}

