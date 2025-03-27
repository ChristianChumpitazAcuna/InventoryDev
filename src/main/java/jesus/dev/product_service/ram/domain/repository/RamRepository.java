package jesus.dev.product_service.ram.domain.repository;

import jesus.dev.product_service.ram.domain.model.Ram;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RamRepository {
    Mono<Ram> save(Ram ram);

    Mono<Void> changeStatus(String id, Boolean status);

    Mono<Ram> findById(String id);

    Flux<Ram> findByStatus(Boolean status);
}
