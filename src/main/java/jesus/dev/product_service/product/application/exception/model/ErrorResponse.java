package jesus.dev.product_service.product.application.exception.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private Object details;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int status, String error, String message, Object details) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.details = details;
    }
}
