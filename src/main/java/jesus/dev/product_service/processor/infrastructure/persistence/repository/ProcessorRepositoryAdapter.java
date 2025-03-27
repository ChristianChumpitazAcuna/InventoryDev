package jesus.dev.product_service.processor.infrastructure.persistence.repository;

import jesus.dev.product_service.processor.domain.model.Processor;
import jesus.dev.product_service.processor.domain.repository.ProcessorRepository;
import jesus.dev.product_service.processor.infrastructure.persistence.mapper.ProcessorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProcessorRepositoryAdapter implements ProcessorRepository {
    private final ProcessorReactiveMongoRepository mongoRepository;
    private final ProcessorMapper mapper;

    @Override
    public Mono<Processor> save(Processor processor) {
        return Mono.just(processor)
                .map(mapper::toEntity)
                .flatMap(mongoRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> changeStatus(String id, Boolean status) {
        return mongoRepository.changeStatus(id, status);
    }

    @Override
    public Mono<Processor> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Processor> findByStatus(Boolean status) {
        return mongoRepository.findByStatus(status)
                .map(mapper::toDomain);
    }
}
