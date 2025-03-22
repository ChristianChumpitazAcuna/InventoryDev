package jesus.dev.product_service.product.infraestructure.rest.validator;

import jesus.dev.product_service.product.application.exception.CustomValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T object) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(object, object.getClass().getName());
        validator.validate(object, bindingResult);

        if (bindingResult.hasErrors()) {
            return Mono.error(new CustomValidationException(bindingResult));
        }
        return Mono.just(object);
    }
}
