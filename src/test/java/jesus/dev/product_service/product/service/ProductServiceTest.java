package jesus.dev.product_service.product.service;

import jesus.dev.product_service.exception.ItemCreationException;
import jesus.dev.product_service.exception.ItemNotFoundException;
import jesus.dev.product_service.exception.ItemStatusAlreadySetException;
import jesus.dev.product_service.exception.ItemUpdatedException;
import jesus.dev.product_service.product.application.mapper.ProductUseCaseMapper;
import jesus.dev.product_service.product.application.service.ProductUseCasesImpl;
import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;
import jesus.dev.product_service.product.domain.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductServiceTest {
    @InjectMocks
    private ProductUseCasesImpl useCases;

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductUseCaseMapper mapper;

    private Product productDomain;
    private ProductRequestDTO productDTO;

    @BeforeEach
    void setUp() {
        productDomain = createProduct();
        productDTO = createProductRequestDTO();
    }

    @Test
    void testGetProductById_Success() {
        String id = UUID.randomUUID().toString();
        productDomain.setId(id);

        when(repository.findById(id)).thenReturn(Mono.just(productDomain));

        StepVerifier.create(useCases.getProductById(id))
                .expectNext(productDomain)
                .verifyComplete();

        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetProductById_NotFound() {
        String id = UUID.randomUUID().toString();
        productDomain.setId(id);

        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(useCases.getProductById(id))
                .expectError(ItemNotFoundException.class)
                .verify();

        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetProductsByStatus_Success() {
        Boolean status = true;

        Product product1 = createProduct();
        product1.setIpAddress("192.168.1.10");

        Product product2 = createProduct();
        product2.setIpAddress("192.168.1.11");

        Flux<Product> productFlux = Flux.just(product1, product2);

        when(repository.findByStatus(status)).thenReturn(productFlux);

        StepVerifier.create(useCases.getProductByStatus(status))
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();

        verify(repository, times(1)).findByStatus(status);
    }

    @Test
    void testSaveProduct_Success() {
        productDTO.setIpAddress("192.168.1.9");
        productDomain.setIpAddress("192.168.1.9");

        when(mapper.dtoToDomain(productDTO)).thenReturn(productDomain);
        when(repository.save(any(Product.class))).thenReturn(Mono.just(productDomain));

        StepVerifier.create(useCases.saveProduct(productDTO))
                .expectNext(productDomain)
                .verifyComplete();

        verify(mapper, times(1)).dtoToDomain(productDTO);
        verify(repository, times(1)).save(productDomain);
    }

    @Test
    void testSaveProduct_Failure() {
        productDTO.setModel("DEll");
        productDomain.setModel("DEll");

        when(mapper.dtoToDomain(productDTO)).thenReturn(productDomain);
        when(repository.save(any(Product.class))).thenReturn(
                Mono.error(new ItemCreationException("Error saving product")));

        StepVerifier.create(useCases.saveProduct(productDTO))
                .expectError(ItemCreationException.class)
                .verify();

        verify(mapper, times(1)).dtoToDomain(productDTO);
        verify(repository, times(1)).save(productDomain);
    }

    @Test
    void testUpdateProduct_Success() {
        // Existing Product
        productDomain.setOperatingSystem("Windows 10");

        // Request DTO
        String id = productDomain.getId();
        productDTO.setOperatingSystem("Windows 11");

        // Domain Product
        Product updatedProduct = createProduct();
        updatedProduct.setId(id);
        updatedProduct.setOperatingSystem(productDTO.getOperatingSystem());

        when(repository.findById(id)).thenReturn(Mono.just(productDomain));
        when(mapper.dtoToDomain(productDTO)).thenReturn(updatedProduct);
        when(repository.save(updatedProduct)).thenReturn(Mono.just(updatedProduct));

        StepVerifier.create(useCases.updateProduct(id, productDTO))
                .expectNextMatches(product -> {
                            boolean match = product.getId().equals(id) &&
                                    product.getOperatingSystem().equals("Windows 11");

                            if (!match) {
                                log.error("Product does not match expected: {}", product);
                            }
                            return match;
                        }
                )
                .verifyComplete();

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).dtoToDomain(productDTO);
        verify(repository, times(1)).save(updatedProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Existing Product
        productDomain.setSerialNumber("191");

        // Request DTO
        String id = productDomain.getId();
        productDTO.setSerialNumber("200");

        // Domain Product
        Product updatedProduct = createProduct();
        updatedProduct.setId(id);
        updatedProduct.setSerialNumber(productDomain.getSerialNumber());

        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(useCases.updateProduct(id, productDTO))
                .expectError(ItemNotFoundException.class)
                .verify();

        verify(repository, times(1)).findById(id);
        verify(mapper, never()).dtoToDomain(productDTO);
        verify(repository, never()).save(updatedProduct);
    }

    @Test
    void testUpdateProduct_Failure() {
        // Existing Product
        productDomain.setSerialNumber("191");

        // Request DTO
        String id = productDomain.getId();
        productDTO.setSerialNumber("200");

        // Domain Product
        Product updatedProduct = createProduct();
        updatedProduct.setId(id);
        updatedProduct.setSerialNumber(productDomain.getSerialNumber());

        when(repository.findById(id)).thenReturn(Mono.just(productDomain));
        when(mapper.dtoToDomain(productDTO)).thenReturn(updatedProduct);
        when(repository.save(updatedProduct)).thenReturn(Mono.error(
                new ItemUpdatedException("Error updating product")));

        StepVerifier.create(useCases.updateProduct(id, productDTO))
                .expectError(ItemUpdatedException.class)
                .verify();

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).dtoToDomain(productDTO);
        verify(repository, times(1)).save(updatedProduct);
    }

    @Test
    void testChangeStatusProduct_Success() {
        // Existing Product
        String id = UUID.randomUUID().toString();
        productDomain.setId(id);

        Boolean status = false;

        when(repository.findById(id)).thenReturn(Mono.just(productDomain));
        when(repository.changeStatus(id, status)).thenReturn(Mono.empty());

        StepVerifier.create(useCases.changeStatusProduct(id, status))
                .verifyComplete();

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).changeStatus(id, status);
    }

    @Test
    void testChangeStatusProduct_NotFound() {
        // Existing Product
        String id = UUID.randomUUID().toString();
        productDomain.setId(id);

        Boolean status = false;

        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(useCases.changeStatusProduct(id, status))
                .expectError(ItemNotFoundException.class)
                .verify();

        verify(repository, times(1)).findById(id);
        verify(repository, never()).changeStatus(id, status);
    }

    @Test
    void testChangeStatusProduct_StatusAlreadySet() {
        // Existing Product
        String id = UUID.randomUUID().toString();
        productDomain.setId(id);
        productDomain.setStatus(false);

        Boolean status = false;

        when(repository.findById(id)).thenReturn(Mono.just(productDomain));

        StepVerifier.create(useCases.changeStatusProduct(id, status))
                .expectError(ItemStatusAlreadySetException.class)
                .verify();

        verify(repository, times(1)).findById(id);
        verify(repository, never()).changeStatus(id, status);
    }

    @Test
    void testChangeStatusProduct_Failure() {
        // Existing Product
        String id = UUID.randomUUID().toString();
        productDomain.setId(id);
        productDomain.setStatus(false);

        Boolean status = true;

        when(repository.findById(id)).thenReturn(Mono.just(productDomain));
        when(repository.changeStatus(id, status)).thenReturn(Mono.error(
                new ItemUpdatedException("Error chaining product status")));

        StepVerifier.create(useCases.changeStatusProduct(id, status))
                .expectError(ItemUpdatedException.class)
                .verify();

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).changeStatus(id, status);
    }

    private Product createProduct() {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setIpAddress("192.168.1.87");
        product.setMacAddress("00:D8:61:C1:33:E9");
        product.setBrand("HP");
        product.setModel("ProDesk 400 G6 SFF");
        product.setSerialNumber("120");
        product.setOperatingSystem("Windows 10");
        product.setProcessorId("proc-123");
        product.setRamId("ram-123");
        product.setVideoCardId("video-card-123");
        product.setScreenId("screen-123");
        product.setStatus(true);
        return product;
    }

    private ProductRequestDTO createProductRequestDTO() {
        ProductRequestDTO productRequestDTO = new ProductRequestDTO();
        productRequestDTO.setIpAddress("192.168.1.87");
        productRequestDTO.setMacAddress("00:D8:61:C1:33:E9");
        productRequestDTO.setBrand("HP");
        productRequestDTO.setModel("ProDesk 400 G6 SFF");
        productRequestDTO.setSerialNumber("120");
        productRequestDTO.setOperatingSystem("Windows 10");
        productRequestDTO.setProcessorId("proc-123");
        productRequestDTO.setRamId("ram-123");
        productRequestDTO.setVideoCardId("video-card-123");
        productRequestDTO.setScreenId("screen-123");
        return productRequestDTO;
    }
}
