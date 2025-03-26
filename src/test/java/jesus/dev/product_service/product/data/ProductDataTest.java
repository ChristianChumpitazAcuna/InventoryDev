package jesus.dev.product_service.product.data;

import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;

import java.util.UUID;

public class ProductDataTest {
    public static Product createProduct() {
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

    public static ProductRequestDTO createProductRequestDTO() {
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
