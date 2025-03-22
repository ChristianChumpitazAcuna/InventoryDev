package jesus.dev.product_service.processor.domain.repository;

import jesus.dev.product_service.processor.domain.model.Processor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProcessorRepository {
    Mono<Processor> save(Processor processor);

    Mono<Void> changeStatus(String id, Boolean status);

    Mono<Processor> findById(String id);

    Flux<Processor> findByStatus(Boolean status);
}
