package jesus.dev.product_service.product.infraestructure.persistence.repository;


import jesus.dev.product_service.product.domain.model.Cpu;
import jesus.dev.product_service.product.domain.repository.CpuRepository;
import jesus.dev.product_service.product.infraestructure.persistence.mapper.CpuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CpuRepositoryAdapter implements CpuRepository {
    private final CpuReactiveMongoRepository mongoRepository;
    private final CpuMapper mapper;


    @Override
    public Mono<Cpu> save(Cpu cpu) {
        return Mono.just(cpu)
                .map(mapper::toEntity)
                .flatMap(mongoRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> changeStatus(String id, Boolean status) {
        return mongoRepository.changeStatus(id, status);
    }

    @Override
    public Mono<Cpu> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Cpu> findByStatus(Boolean status) {
        return mongoRepository.findByStatus(status)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Cpu> findByIpAddress(String ipAddress) {
        return mongoRepository.findByIpAddress(ipAddress)
                .map(mapper::toDomain);
    }
}
