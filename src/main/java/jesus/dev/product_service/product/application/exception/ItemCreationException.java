package jesus.dev.product_service.product.application.exception;

public class ItemCreationException extends RuntimeException {
    public ItemCreationException(String message) {
        super(message);
    }

    public ItemCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
