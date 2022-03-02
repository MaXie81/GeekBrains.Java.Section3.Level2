package webshop.core.soap.utils;

import webshop.core.entities.Product;

public class Mapping {
    public static webshop.core.soap.product.Product getSoapProductFromProduct(Product product) {
        webshop.core.soap.product.Product soapProduct = new webshop.core.soap.product.Product();
        soapProduct.setId(product.getId());
        soapProduct.setTitle(product.getTitle());
        soapProduct.setPrice(product.getPrice());
        return soapProduct;
    };
}
