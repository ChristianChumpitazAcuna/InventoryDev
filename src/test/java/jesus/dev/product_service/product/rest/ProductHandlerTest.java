package jesus.dev.product_service.product.rest;

import jesus.dev.product_service.product.application.service.ProductUseCasesImpl;
import jesus.dev.product_service.product.data.ProductDataTest;
import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;
import jesus.dev.product_service.product.infraestructure.rest.handler.ProductHandler;
import jesus.dev.product_service.util.validator.CustomValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductHandlerTest {
    @InjectMocks
    private ProductHandler handler;

    @Mock
    private ProductUseCasesImpl useCases;

    @Mock
    private CustomValidator customValidator;

    private ServerRequest serverRequest;
    private Product product;
    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    public void setUp() {
        product = ProductDataTest.createProduct();
        productRequestDTO = ProductDataTest.createProductRequestDTO();
        serverRequest = mock(ServerRequest.class);
    }

    @Test
    void testCreateProduct() {
        when(serverRequest.bodyToMono(ProductRequestDTO.class)).thenReturn(
                Mono.just(productRequestDTO));
        when(customValidator.validate(productRequestDTO)).thenReturn(
                Mono.just(productRequestDTO)
        );
        when(useCases.saveProduct(productRequestDTO)).thenReturn(Mono.just(product));

        Mono<ServerResponse> responseMono = handler.create(serverRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(
                        HttpStatus.CREATED, response.statusCode()))
                .verifyComplete();

        verify(customValidator, times(1)).validate(productRequestDTO);
        verify(useCases, times(1)).saveProduct(productRequestDTO);
    }

    @Test
    void testUpdateProduct() {
        when(serverRequest.pathVariable("id")).thenReturn("123");
        when(serverRequest.bodyToMono(ProductRequestDTO.class)).thenReturn(Mono.just(productRequestDTO));
        when(customValidator.validate(productRequestDTO)).thenReturn(Mono.just(productRequestDTO));
        when(useCases.updateProduct(anyString(), any(ProductRequestDTO.class))).thenReturn(Mono.just(product));

        Mono<ServerResponse> responseMono = handler.update(serverRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(
                        HttpStatus.OK, response.statusCode()
                ))
                .verifyComplete();
        verify(customValidator, times(1)).validate(productRequestDTO);
        verify(useCases, times(1)).updateProduct("123", productRequestDTO);
    }

    @Test
    void testChangeStatusProduct() {
        when(serverRequest.pathVariable("id")).thenReturn("123");
        when(serverRequest.pathVariable("status")).thenReturn("true");
        when(useCases.changeStatusProduct("123", true)).thenReturn(Mono.empty());

        Mono<ServerResponse> responseMono = handler.changeStatus(serverRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(
                        HttpStatus.NO_CONTENT, response.statusCode()
                ))
                .verifyComplete();

        verify(useCases, times(1)).changeStatusProduct("123", true);
    }

    @Test
    void testGetProductById() {
        when(serverRequest.pathVariable("id")).thenReturn("123");
        when(useCases.getProductById("123")).thenReturn(Mono.just(product));

        Mono<ServerResponse> responseMono = handler.getById(serverRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(
                        HttpStatus.OK, response.statusCode()
                ))
                .verifyComplete();

        verify(useCases, times(1)).getProductById("123");
    }

    @Test
    void testGetProductsByStatus() {
        when(serverRequest.pathVariable("status")).thenReturn("true");
        when(useCases.getProductByStatus(true)).thenReturn(Flux.just(product));

        Mono<ServerResponse> responseMono = handler.getByStatus(serverRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> assertEquals(
                        HttpStatus.OK, response.statusCode()
                ))
                .verifyComplete();
        verify(useCases, times(1)).getProductByStatus(true);
    }
}
