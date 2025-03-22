package jesus.dev.product_service.product.application.exception;

public class ItemUpdatedException extends RuntimeException {
    public ItemUpdatedException(String message) {
        super(message);
    }

    public ItemUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
