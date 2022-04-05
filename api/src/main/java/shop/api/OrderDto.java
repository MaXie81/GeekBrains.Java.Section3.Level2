package shop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Заказ(DTO)")
public class OrderDto {
    @Schema(description = "Идентификатор", required = true, example = "1")
    private Long id;
    @Schema(description = "Имя клиента", required = true, example = "bob")
    private String username;
    @Schema(description = "Список позиций", required = true, example = "")
    private List<OrderItemDto> items;
    @Schema(description = "Адрес клиента", required = false, example = "г.Москва, ул.Октябрьская, д.15, кв.45")
    private String address;
    @Schema(description = "Телефон клиента", required = false, example = "9157654531")
    private String phone;
    @Schema(description = "Общая стоимость заказа", required = true, example = "255.19")
    private BigDecimal totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
