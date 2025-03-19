package jesus.dev.inventory.application.exception;

public class StatusAlreadySetException extends RuntimeException {
    public StatusAlreadySetException(String message) {
        super(message);
    }

    public StatusAlreadySetException(String message, Throwable cause) {
        super(message, cause);
    }
}
