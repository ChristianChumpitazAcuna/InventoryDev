package jesus.dev.product_service.processor.infrastructure.persistence.repository;

import jesus.dev.product_service.processor.infrastructure.persistence.entity.ProcessorEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProcessorReactiveMongoRepository extends ReactiveMongoRepository<ProcessorEntity, String> {

    @Query("{_id:  ?0}")
    @Update("{$set: {'status':  ?1}}")
    Mono<Void> changeStatus(String id, Boolean status);

    Flux<ProcessorEntity> findByStatus(Boolean status);
}
