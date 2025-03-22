package jesus.dev.product_service.exception;

public class ItemStatusAlreadySetException extends RuntimeException {
    public ItemStatusAlreadySetException(String message) {
        super(message);
    }

    public ItemStatusAlreadySetException(String message, Throwable cause) {
        super(message, cause);
    }
}
