package jesus.dev.product_service.product.application.exception;

import jesus.dev.product_service.product.application.exception.model.ErrorResponse;

public interface ErrorHandlerStrategy {
    boolean supports(Throwable error);

    ErrorResponse handle(Throwable error);
}
