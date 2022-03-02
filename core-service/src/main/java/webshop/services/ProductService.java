package webshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webshop.entities.Product;
import webshop.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
