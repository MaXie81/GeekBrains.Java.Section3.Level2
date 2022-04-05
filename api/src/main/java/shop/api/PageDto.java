package shop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Страница продуктов(DTO)")
public class PageDto<E> {
    @Schema(description = "Список продуктов", required = true, example = "")
    private List<E> items;
    @Schema(description = "Текущий номер страницы", required = true, example = "0")
    private int page;
    @Schema(description = "Всего страниц", required = true, example = "4")
    private int totalPages;

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public PageDto() {
    }
}
