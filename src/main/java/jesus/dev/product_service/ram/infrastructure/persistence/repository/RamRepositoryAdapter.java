package jesus.dev.product_service.ram.infrastructure.persistence.repository;

import jesus.dev.product_service.ram.domain.model.Ram;
import jesus.dev.product_service.ram.domain.repository.RamRepository;
import jesus.dev.product_service.ram.infrastructure.persistence.mapper.RamMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RamRepositoryAdapter implements RamRepository {
    private final RamReactiveMongoRepository mongoRepository;
    private final RamMapper mapper;

    @Override
    public Mono<Ram> save(Ram ram) {
        return Mono.just(ram)
                .map(mapper::domainToEntity)
                .flatMap(mongoRepository::save)
                .map(mapper::entityToDomain);
    }

    @Override
    public Mono<Void> changeStatus(String id, Boolean status) {
        return mongoRepository.changeStatus(id, status);
    }

    @Override
    public Mono<Ram> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::entityToDomain);
    }

    @Override
    public Flux<Ram> findByStatus(Boolean status) {
        return mongoRepository.findByStatus(status)
                .map(mapper::entityToDomain);
    }
}
