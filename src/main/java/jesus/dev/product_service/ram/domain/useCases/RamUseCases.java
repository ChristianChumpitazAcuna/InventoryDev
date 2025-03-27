package jesus.dev.product_service.ram.domain.useCases;

import jesus.dev.product_service.ram.domain.model.Ram;
import jesus.dev.product_service.ram.domain.model.dto.RamRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RamUseCases {
    Mono<Ram> saveRam(RamRequestDTO ramRequestDTO);

    Mono<Ram> updateRam(String id, RamRequestDTO ramRequestDTO);

    Mono<Void> changeStatusRam(String id, Boolean status);

    Mono<Ram> getRamById(String id);

    Flux<Ram> getRamByStatus(Boolean status);
}
