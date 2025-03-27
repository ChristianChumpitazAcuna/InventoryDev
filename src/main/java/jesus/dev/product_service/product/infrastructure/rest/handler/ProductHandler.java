package jesus.dev.product_service.product.infrastructure.rest.handler;

import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;
import jesus.dev.product_service.product.domain.useCases.ProductUseCases;
import jesus.dev.product_service.util.validator.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {
    private final ProductUseCases useCases;
    private final CustomValidator customValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProductRequestDTO.class)
                .flatMap(customValidator::validate)
                .flatMap(useCases::saveProduct)
                .flatMap(product -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(ProductRequestDTO.class)
                .flatMap(customValidator::validate)
                .flatMap(dto -> useCases.updateProduct(id, dto))
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product)
                );
    }

    public Mono<ServerResponse> changeStatus(ServerRequest request) {
        String id = request.pathVariable("id");
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        return useCases.changeStatusProduct(id, status)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCases.getProductById(id)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product)
                );
    }

    public Mono<ServerResponse> getByStatus(ServerRequest request) {
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        Flux<Product> productFlux = useCases.getProductByStatus(status);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productFlux, Product.class);
    }

}
