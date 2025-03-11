package jesus.dev.inventory.infraestructure.rest.handler;

import jesus.dev.inventory.domain.model.dto.CpuRequestDTO;
import jesus.dev.inventory.domain.useCases.CpuUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CpuHandler {
    private final CpuUseCases useCases;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CpuRequestDTO.class)
                .flatMap(useCases::saveCpu)
                .flatMap(cpu -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cpu)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(CpuRequestDTO.class)
                .flatMap(dto -> useCases.updateCpu(id, dto))
                .flatMap(cpu -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cpu)
                );
    }

    public Mono<ServerResponse> changeStatus(ServerRequest request) {
        String id = request.pathVariable("id");
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        return useCases.changeStatusCpu(id, status)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String id = request.pathVariable("id");
        return useCases.getCpuById(id)
                .flatMap(cpu -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cpu)
                );
    }

    public Mono<ServerResponse> getByStatus(ServerRequest request) {
        Boolean status = Boolean.parseBoolean(request.pathVariable("status"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(useCases.getCpuByStatus(status));
    }

}
