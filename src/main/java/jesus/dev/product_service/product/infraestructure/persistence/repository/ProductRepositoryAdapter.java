package jesus.dev.product_service.product.infraestructure.persistence.repository;


import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.repository.ProductRepository;
import jesus.dev.product_service.product.infraestructure.persistence.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductReactiveMongoRepository mongoRepository;
    private final ProductMapper mapper;


    @Override
    public Mono<Product> save(Product product) {
        return Mono.just(product)
                .map(mapper::toEntity)
                .flatMap(mongoRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> changeStatus(String id, Boolean status) {
        return mongoRepository.changeStatus(id, status);
    }

    @Override
    public Mono<Product> findById(String id) {
        return mongoRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Product> findByStatus(Boolean status) {
        return mongoRepository.findByStatus(status)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Product> findByIpAddress(String ipAddress) {
        return mongoRepository.findByIpAddress(ipAddress)
                .map(mapper::toDomain);
    }
}
