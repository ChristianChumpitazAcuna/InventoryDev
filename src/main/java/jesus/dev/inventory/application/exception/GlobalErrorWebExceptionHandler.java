package jesus.dev.inventory.application.exception;

import jesus.dev.inventory.application.exception.model.ErrorResponse;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(-2)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private final Map<Class<? extends Throwable>, HttpStatus> exceptionStatusMap;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                          ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.exceptionStatusMap = new HashMap<>();
        this.exceptionStatusMap.put(WebExchangeBindException.class, HttpStatus.BAD_REQUEST);
        this.exceptionStatusMap.put(NotFoundException.class, HttpStatus.BAD_REQUEST);
        this.exceptionStatusMap.put(StatusAlreadySetException.class, HttpStatus.CONFLICT);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        Throwable error = getError(serverRequest);
        HttpStatus status = exceptionStatusMap.getOrDefault(error.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorResponse errorResponse = new ErrorResponse(status.value(), status.getReasonPhrase(), error.getMessage());

        if (error instanceof WebExchangeBindException ex) {
            Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (existing, replacement) -> existing
                    ));
            errorResponse.setDetails(errors);
        }
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorResponse));
    }
}
