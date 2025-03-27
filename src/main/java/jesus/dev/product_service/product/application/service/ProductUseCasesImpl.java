package jesus.dev.product_service.product.application.service;

import jesus.dev.product_service.exception.ItemCreationException;
import jesus.dev.product_service.exception.ItemNotFoundException;
import jesus.dev.product_service.exception.ItemStatusAlreadySetException;
import jesus.dev.product_service.exception.ItemUpdatedException;
import jesus.dev.product_service.product.application.mapper.ProductUseCaseMapper;
import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;
import jesus.dev.product_service.product.domain.repository.ProductRepository;
import jesus.dev.product_service.product.domain.useCases.ProductUseCases;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductUseCasesImpl implements ProductUseCases {
    private final ProductRepository repository;
    private final ProductUseCaseMapper mapper;

    @Override
    public Mono<Product> saveProduct(ProductRequestDTO productRequestDTO) {
        return Mono.just(productRequestDTO)
                .map(dto -> {
                    Product product = mapper.dtoToDomain(dto);
                    product.setStatus(true);
                    return product;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    log.error("Error saving product: {}", e.getMessage());
                    return new ItemCreationException("Error saving product");
                });
    }

    @Override
    public Mono<Product> updateProduct(String id, ProductRequestDTO productRequestDTO) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Product not found with id: " + id)))
                .map(existingProduct -> {
                    Product product = mapper.dtoToDomain(productRequestDTO);
                    product.setId(existingProduct.getId());
                    product.setStatus(true);
                    return product;
                })
                .flatMap(repository::save)
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException) {
                        return e;
                    }
                    log.error("Error updating product: {}", e.getMessage());
                    return new ItemUpdatedException("Error updating product");
                });
    }


    @Override
    public Mono<Void> changeStatusProduct(String id, Boolean status) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Product not found with id: " + id)))
                .flatMap(product -> {
                    if (product.getStatus().equals(status)) {
                        return Mono.error(new ItemStatusAlreadySetException("Product is already in the requested status: " + id));
                    }
                    return repository.changeStatus(product.getId(), status);
                })
                .onErrorMap(e -> {
                    if (e instanceof ItemNotFoundException || e instanceof ItemStatusAlreadySetException) {
                        return e;
                    }
                    log.error("Error chaining product status: {}", e.getMessage());
                    return new ItemUpdatedException("Error chaining product status");
                });
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Product not found with id: " + id)));
    }

    @Override
    public Flux<Product> getProductByStatus(Boolean status) {
        return repository.findByStatus(status);
    }

    @Override
    public Mono<Product> getProductByIpAddress(String ipAddress) {
        return repository.findByIpAddress(ipAddress)
                .switchIfEmpty(Mono.error(new ItemNotFoundException("Cannot find product with ip address: " + ipAddress)));
    }
}
