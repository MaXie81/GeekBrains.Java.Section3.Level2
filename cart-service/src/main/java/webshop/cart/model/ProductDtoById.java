package webshop.cart.model;

import shop.api.ProductDto;

public interface ProductDtoById {
    ProductDto get(Long productId);
}
