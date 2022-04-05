package shop.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ошибка")
public class AppError {
    @Schema(description = "Код", required = true, example = "404")
    private int statusCode;
    @Schema(description = "Текст", required = true, example = "Продукт не найден, id: 500")
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppError() {
    }

    public AppError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
