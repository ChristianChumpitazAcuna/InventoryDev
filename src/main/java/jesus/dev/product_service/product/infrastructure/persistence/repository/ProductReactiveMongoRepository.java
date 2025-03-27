package jesus.dev.product_service.product.infrastructure.persistence.repository;

import jesus.dev.product_service.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReactiveMongoRepository extends ReactiveMongoRepository<ProductEntity, String> {

    @Query("{_id:  ?0}")
    @Update("{$set: {'status': ?1}}")
    Mono<Void> changeStatus(String id, Boolean status);

    Flux<ProductEntity> findByStatus(Boolean status);

    Mono<ProductEntity> findByIpAddress(String ipAddress);
}
