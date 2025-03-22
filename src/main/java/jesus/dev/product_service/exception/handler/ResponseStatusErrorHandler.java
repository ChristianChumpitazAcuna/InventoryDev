package jesus.dev.product_service.exception.handler;

import jesus.dev.product_service.exception.ErrorHandlerStrategy;
import jesus.dev.product_service.exception.model.ErrorResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ResponseStatusErrorHandler implements ErrorHandlerStrategy {
    @Override
    public boolean supports(Throwable error) {
        return error instanceof ResponseStatusException;
    }

    @Override
    public ErrorResponse handle(Throwable error) {
        ResponseStatusException ex = (ResponseStatusException) error;
        return new ErrorResponse(
                ex.getStatusCode().value(),
                ex.getReason(),
                "A handled HTTP error occurred"
        );
    }
}
