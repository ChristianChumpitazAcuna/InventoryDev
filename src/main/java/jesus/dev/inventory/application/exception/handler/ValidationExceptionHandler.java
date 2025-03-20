package jesus.dev.inventory.application.exception.handler;

import jesus.dev.inventory.application.exception.CustomValidationException;
import jesus.dev.inventory.application.exception.ErrorHandlerStrategy;
import jesus.dev.inventory.application.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ValidationExceptionHandler implements ErrorHandlerStrategy {

    @Override
    public boolean supports(Throwable error) {
        return error instanceof CustomValidationException;
    }

    @Override
    public ErrorResponse handle(Throwable error) {
        CustomValidationException ex = (CustomValidationException) error;

        Map<String, String> errors = ex.getErrors().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (oldValue, newValue) -> oldValue + ": " + newValue
                ));

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid request parameters",
                errors
        );
    }
}
