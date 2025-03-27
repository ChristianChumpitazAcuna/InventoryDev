package jesus.dev.product_service.ram.infrastructure.rest.handler;

import jesus.dev.product_service.ram.domain.model.Ram;
import jesus.dev.product_service.ram.domain.model.dto.RamRequestDTO;
import jesus.dev.product_service.ram.domain.useCases.RamUseCases;
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
public class RamHandler {
    private final RamUseCases useCases;
    private final CustomValidator customValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(RamRequestDTO.class)
                .flatMap(customValidator::validate)
                .flatMap(useCases::saveRam)
                .flatMap(ram -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ram)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RamRequestDTO.class)
                .flatMap(customValidator::validate)
                .flatMap(dto -> useCases.updateRam(id, dto))
                .flatMap(ram -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ram)
                );
    }

    public Mono<ServerResponse> changeStatus(ServerRequest request) {
        String id = request.pathVariable("id");
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        return useCases.changeStatusRam(id, status)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCases.getRamById(id)
                .flatMap(ram -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(ram)
                );
    }

    public Mono<ServerResponse> getByStatus(ServerRequest request) {
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        Flux<Ram> ramFlux = useCases.getRamByStatus(status);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ramFlux, Ram.class);
    }
}
