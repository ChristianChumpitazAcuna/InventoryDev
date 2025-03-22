package jesus.dev.product_service.product.domain.repository;

import jesus.dev.product_service.product.domain.model.Cpu;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CpuRepository {
    Mono<Cpu> save(Cpu cpu);

    Mono<Void> changeStatus(String id, Boolean status);

    Mono<Cpu> findById(String id);

    Flux<Cpu> findByStatus(Boolean status);

    Flux<Cpu> findByIpAddress(String ipAddress);
}
