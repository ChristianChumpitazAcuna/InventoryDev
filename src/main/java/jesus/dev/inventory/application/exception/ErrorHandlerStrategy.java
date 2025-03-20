package jesus.dev.inventory.application.exception;

import jesus.dev.inventory.application.exception.model.ErrorResponse;

public interface ErrorHandlerStrategy {
    boolean supports(Throwable error);

    ErrorResponse handle(Throwable error);
}
