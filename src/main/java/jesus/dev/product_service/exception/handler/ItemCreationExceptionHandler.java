package jesus.dev.product_service.exception.handler;

import jesus.dev.product_service.exception.ErrorHandlerStrategy;
import jesus.dev.product_service.exception.ItemCreationException;
import jesus.dev.product_service.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ItemCreationExceptionHandler implements ErrorHandlerStrategy {
    @Override
    public boolean supports(Throwable error) {
        return error instanceof ItemCreationException;
    }

    @Override
    public ErrorResponse handle(Throwable error) {
        ItemCreationException ex = (ItemCreationException) error;
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Creation Failed",
                ex.getMessage()
        );
    }
}
