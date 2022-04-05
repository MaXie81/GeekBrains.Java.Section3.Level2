package webshop.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shop.api.AppError;
import shop.api.PageDto;
import shop.api.ProductDto;
import shop.api.ResourceNotFoundException;
import webshop.core.converters.ProductConverter;
import webshop.core.entities.Product;
import webshop.core.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Operation(
            summary = "Запрос на получение отфильтрованного списка продуктов с разбиением на страницы",
            responses = {
                    @ApiResponse(
                            description = "Страница сформирована", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PageDto.class))
                    )
            }
    )
    @GetMapping
    public PageDto<ProductDto> findProducts(
            @RequestParam(required = false, name = "min_price") @Parameter(description = "Фильтр, минимальная цена", required = false) BigDecimal minPrice,
            @RequestParam(required = false, name = "max_price") @Parameter(description = "Фильтр, максимальная цена", required = false) BigDecimal maxPrice,
            @RequestParam(required = false, name = "title") @Parameter(description = "Фильтр, символьная последовательность в имени", required = false) String title,
            @RequestParam(defaultValue = "1", name = "p") @Parameter(description = "Номер страницы", required = true) Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        Specification<Product> spec = productService.createSpecByFilters(minPrice, maxPrice, title);
        Page<ProductDto> jpaPage = productService.findAll(spec, page - 1).map(productConverter::entityToDto);

        PageDto<ProductDto> out = new PageDto<>();
        out.setPage(jpaPage.getNumber());
        out.setItems(jpaPage.getContent());
        out.setTotalPages(jpaPage.getTotalPages());
        return out;
    }

    @Operation(
            summary = "Запрос на получение продукта по его идентификатору(ID)",
            responses = {
                    @ApiResponse(
                            description = "Продукт найден", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable @Parameter(description = "Идентификатор", required = true) Long id) {
        Product p = productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id: " + id));
        return productConverter.entityToDto(p);
    }

    @Operation(
            summary = "Запрос на создание продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);
    }

    @Operation(
            summary = "Запрос на удаление продукта по его идентификатору(ID)",
            responses = {
                    @ApiResponse(
                            description = "Продукт удален", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable @Parameter(description = "Идентификатор", required = true) Long id) {
        productService.deleteById(id);
    }
}
