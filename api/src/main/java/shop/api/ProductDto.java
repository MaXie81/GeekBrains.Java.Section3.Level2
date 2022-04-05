package shop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Продукт(DTO)")
public class ProductDto {
    @Schema(description = "Идентификатор", required = true, example = "1")
    private Long id;
    @Schema(description = "Наименование", required = true, minLength = 3, example = "Молоко")
    private String title;
    @Schema(description = "Цена", required = true, example = "50.36")
    private BigDecimal price;
    @Schema(description = "Категория", required = true, minLength = 3, example = "Молочные изделия")
    private String categoryTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String categoryTitle) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.categoryTitle = categoryTitle;
    }
}
