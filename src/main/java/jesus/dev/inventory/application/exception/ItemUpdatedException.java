package jesus.dev.inventory.application.exception;

public class ItemUpdatedException extends RuntimeException {
    public ItemUpdatedException(String message) {
        super(message);
    }

    public ItemUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
