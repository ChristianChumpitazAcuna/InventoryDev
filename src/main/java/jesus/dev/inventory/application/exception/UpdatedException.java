package jesus.dev.inventory.application.exception;

public class UpdatedException extends RuntimeException {
    public UpdatedException(String message) {
        super(message);
    }

    public UpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
