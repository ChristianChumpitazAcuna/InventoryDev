package jesus.dev.product_service.exception;

import jesus.dev.product_service.exception.model.ErrorResponse;

public interface ErrorHandlerStrategy {
    boolean supports(Throwable error);

    ErrorResponse handle(Throwable error);
}
