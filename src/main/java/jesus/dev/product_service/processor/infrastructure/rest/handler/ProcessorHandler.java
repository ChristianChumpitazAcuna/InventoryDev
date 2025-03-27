package jesus.dev.product_service.processor.infrastructure.rest.handler;

import jesus.dev.product_service.processor.domain.model.Processor;
import jesus.dev.product_service.processor.domain.model.dto.ProcessorRequestDTO;
import jesus.dev.product_service.processor.domain.useCases.ProcessorUseCases;
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
public class ProcessorHandler {
    private final ProcessorUseCases useCases;
    private final CustomValidator customValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProcessorRequestDTO.class)
                .flatMap(customValidator::validate)
                .flatMap(useCases::saveProcessor)
                .flatMap(processor -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(processor)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(ProcessorRequestDTO.class)
                .flatMap(customValidator::validate)
                .flatMap(dto -> useCases.updateProcessor(id, dto))
                .flatMap(processor -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(processor)
                );
    }

    public Mono<ServerResponse> changeStatus(ServerRequest request) {
        String id = request.pathVariable("id");
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));

        return useCases.changeStatusProcessor(id, status)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCases.getProcessorById(id)
                .flatMap(processor -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(processor)
                );
    }

    public Mono<ServerResponse> getByStatus(ServerRequest request) {
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        Flux<Processor> processorFlux = useCases.getProcessorsByStatus(status);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(processorFlux, Processor.class);
    }
}
