package jesus.dev.product_service.screen.infrastructure.persistence.repository;

import jesus.dev.product_service.screen.infrastructure.persistence.entity.ScreenEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScreenReactiveMongoRepository extends ReactiveMongoRepository<ScreenEntity, String> {
    @Query("{_id:  ?0}")
    @Update("{$set: {'status': ?1}}")
    Mono<Void> changeStatus(String id, Boolean status);

    Flux<ScreenEntity> findByStatus(Boolean status);
}
