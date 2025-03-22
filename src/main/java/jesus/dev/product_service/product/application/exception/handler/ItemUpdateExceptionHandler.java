package jesus.dev.product_service.product.application.exception.handler;

import jesus.dev.product_service.product.application.exception.ErrorHandlerStrategy;
import jesus.dev.product_service.product.application.exception.ItemUpdatedException;
import jesus.dev.product_service.product.application.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ItemUpdateExceptionHandler implements ErrorHandlerStrategy {
    @Override
    public boolean supports(Throwable error) {
        return error instanceof ItemUpdatedException;
    }

    @Override
    public ErrorResponse handle(Throwable error) {
        ItemUpdatedException ex = (ItemUpdatedException) error;
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Updated Failed",
                ex.getMessage()
        );
    }
}
