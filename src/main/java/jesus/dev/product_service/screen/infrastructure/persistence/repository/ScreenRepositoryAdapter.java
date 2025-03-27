package jesus.dev.product_service.screen.infrastructure.persistence.repository;

import jesus.dev.product_service.screen.domain.model.Screen;
import jesus.dev.product_service.screen.domain.repository.ScreenRepository;
import jesus.dev.product_service.screen.infrastructure.persistence.mapper.ScreenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ScreenRepositoryAdapter implements ScreenRepository {
    private final ScreenReactiveMongoRepository mongoRepository;
    private final ScreenMapper mapper;

    @Override
    public Mono<Screen> save(Screen screen) {
        return Mono.just(screen)
                .map(mapper::domainToEntity)
                .flatMap(mongoRepository::save)
                .map(mapper::entityToDomain);
    }

    @Override
    public Mono<Void> changeStatus(String id, Boolean status) {
        return mongoRepository.changeStatus(id, status);
    }

    @Override
    public Mono<Screen> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::entityToDomain);
    }

    @Override
    public Flux<Screen> findByStatus(Boolean status) {
        return mongoRepository.findByStatus(status)
                .map(mapper::entityToDomain);
    }
}
