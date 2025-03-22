package jesus.dev.inventory.application.exception.handler;

import jesus.dev.inventory.application.exception.CustomValidationException;
import jesus.dev.inventory.application.exception.ErrorHandlerStrategy;
import jesus.dev.inventory.application.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ValidationExceptionHandler implements ErrorHandlerStrategy {

    @Override
    public boolean supports(Throwable error) {
        return error instanceof CustomValidationException;
    }

    @Override
    public ErrorResponse handle(Throwable error) {
        CustomValidationException ex = (CustomValidationException) error;

        List<Map<String, String>> errors = new ArrayList<>();

        ex.getErrors().getFieldErrors().forEach(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("field", fieldError.getField());
            errorMap.put("message", fieldError.getDefaultMessage());
            errors.add(errorMap);
        });

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid request parameters",
                errors
        );
    }
}
