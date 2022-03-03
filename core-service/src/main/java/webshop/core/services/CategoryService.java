package webshop.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webshop.core.entities.Category;
import webshop.core.repositories.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
