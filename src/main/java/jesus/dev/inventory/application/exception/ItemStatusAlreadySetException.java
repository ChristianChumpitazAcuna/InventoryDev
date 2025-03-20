package jesus.dev.inventory.application.exception;

public class ItemStatusAlreadySetException extends RuntimeException {
    public ItemStatusAlreadySetException(String message) {
        super(message);
    }

    public ItemStatusAlreadySetException(String message, Throwable cause) {
        super(message, cause);
    }
}
