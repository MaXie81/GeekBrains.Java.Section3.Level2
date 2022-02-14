package webshop.components;

import org.springframework.stereotype.Component;
import webshop.entities.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {
    private List<Product> productList;

    public Cart() {
        this.productList = new ArrayList<>();
    }

    public List<Product> getProductList() {return productList;}

    public void addProduct(Product product) {
        productList.add(product);
    }
}
