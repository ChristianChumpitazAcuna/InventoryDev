package jesus.dev.product_service.exception;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class CustomValidationException extends RuntimeException {
    private final Errors errors;

    public CustomValidationException(Errors errors) {
        super("Validation Failed");
        this.errors = errors;
    }
}
