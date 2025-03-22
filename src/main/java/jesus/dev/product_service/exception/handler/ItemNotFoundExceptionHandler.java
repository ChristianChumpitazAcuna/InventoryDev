package jesus.dev.product_service.exception.handler;

import jesus.dev.product_service.exception.ErrorHandlerStrategy;
import jesus.dev.product_service.exception.ItemNotFoundException;
import jesus.dev.product_service.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ItemNotFoundExceptionHandler implements ErrorHandlerStrategy {
    @Override
    public boolean supports(Throwable error) {
        return error instanceof ItemNotFoundException;
    }

    @Override
    public ErrorResponse handle(Throwable error) {
        ItemNotFoundException ex = (ItemNotFoundException) error;
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage()
        );
    }
}
