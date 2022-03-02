package webshop.soap.utils;

import webshop.soap.product.Product;

public class Mapping {
    public static Product getSoapProductFromProduct(webshop.entities.Product product) {
        Product soapProduct = new Product();
        soapProduct.setId(product.getId());
        soapProduct.setTitle(product.getTitle());
        soapProduct.setPrice(product.getPrice());
        return soapProduct;
    };
}
