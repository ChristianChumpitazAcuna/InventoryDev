package jesus.dev.product_service.product.infraestructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("cpu")
public class CpuEntity {
    @Id
    private String id;
    private String ipAddress;
    private String macAddress;
    private String brand;
    private String model;
    private String serialNumber;
    private String operatingSystem;
    private String processorId;
    private String ramId;
    private String videoCardId;
    private String storageId;
    private String screenId;
    private Boolean status;
}
