package shop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Позиция заказа(DTO)")
public class OrderItemDto {
    @Schema(description = "Идентификатор", required = true, example = "3")
    private Long id;
    @Schema(description = "Наименование продукта", required = true, minLength = 3, example = "Молоко")
    private String productTitle;
    @Schema(description = "Идентификатор заказа", required = true, example = "1")
    private Long orderId;
    @Schema(description = "Количество продуктов в позиции", required = true, example = "2")
    private int quantity;
    @Schema(description = "Стоимость продукта", required = true, example = "50.36")
    private BigDecimal pricePerProduct;
    @Schema(description = "Стоимость позиции", required = true, example = "100.72")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
