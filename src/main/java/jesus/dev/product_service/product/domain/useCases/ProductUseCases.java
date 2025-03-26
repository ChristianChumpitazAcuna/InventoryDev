package jesus.dev.product_service.product.domain.useCases;

import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCases {
    Mono<Product> saveProduct(ProductRequestDTO productRequestDTO);

    Mono<Product> updateProduct(String id, ProductRequestDTO productRequestDTO);

    Mono<Void> changeStatusProduct(String id, Boolean status);

    Mono<Product> getProductById(String id);

    Flux<Product> getProductByStatus(Boolean status);

    Mono<Product> getProductByIpAddress(String ipAddress);
}
