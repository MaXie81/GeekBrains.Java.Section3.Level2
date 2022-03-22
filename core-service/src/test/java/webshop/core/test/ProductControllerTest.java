package webshop.core.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import webshop.core.entities.Category;
import webshop.core.entities.Product;
import webshop.core.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void all() throws Exception {
        List<Product> productList = new ArrayList<>();

        Category category = new Category();
        category.setId(1L);
        category.setTitle("Тестовая категория");

        Product product = new Product();
        product.setId(1L);
        product.setTitle("Тестовый продукт 1");
        product.setPrice(BigDecimal.valueOf(34.55));
        product.setCategory(category);
        productList.add(product);

        product = new Product();
        product.setId(2L);
        product.setTitle("Тестовый продукт 2");
        product.setPrice(BigDecimal.valueOf(26.44));
        product.setCategory(category);
        productList.add(product);

        product = new Product();
        product.setId(3L);
        product.setTitle("Тестовый продукт 3");
        product.setPrice(BigDecimal.valueOf(15.00));
        product.setCategory(category);
        productList.add(product);

        Mockito.doReturn(productList)
                .when(productRepository)
                .findAll();

        Mockito.doReturn(productList
                .stream()
                .filter(p -> p.getId() == 2L)
                .findFirst()
        )
                .when(productRepository)
                .findById(2L);

        mvc.perform(get("/api/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].title", is(productList.get(1).getTitle()))
                );

        mvc.perform(get("/api/v1/products/2").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Тестовый продукт 2"))
                );
    }
}
