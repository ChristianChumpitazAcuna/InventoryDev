package jesus.dev.inventory.infraestructure.rest.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T dto) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(dto, dto.getClass().getName());
        validator.validate(dto, bindingResult);

        if (bindingResult.hasErrors()) {
            return Mono.error(new WebExchangeBindException(null, bindingResult));
        }
        return Mono.just(dto);
    }
}
