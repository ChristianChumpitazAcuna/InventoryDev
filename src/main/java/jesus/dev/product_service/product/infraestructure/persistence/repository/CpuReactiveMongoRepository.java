package jesus.dev.product_service.product.infraestructure.persistence.repository;

import jesus.dev.product_service.product.infraestructure.persistence.entity.CpuEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CpuReactiveMongoRepository extends ReactiveMongoRepository<CpuEntity, String> {

    @Query("{_id:  ?0}")
    @Update("{$set: {'status': ?1}}")
    Mono<Void> changeStatus(String id, Boolean status);

    Flux<CpuEntity> findByStatus(Boolean status);

    Flux<CpuEntity> findByIpAddress(String ipAddress);
}
