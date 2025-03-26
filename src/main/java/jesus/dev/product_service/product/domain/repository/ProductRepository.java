package jesus.dev.product_service.product.domain.repository;

import jesus.dev.product_service.product.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product product);

    Mono<Void> changeStatus(String id, Boolean status);

    Mono<Product> findById(String id);

    Flux<Product> findByStatus(Boolean status);

    Mono<Product> findByIpAddress(String ipAddress);
}
