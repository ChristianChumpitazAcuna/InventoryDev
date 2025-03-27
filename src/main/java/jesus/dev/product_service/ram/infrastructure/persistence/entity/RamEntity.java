package jesus.dev.product_service.ram.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("ram")
public class RamEntity {
    @Id
    private String id;
    private String brand;
    private String model;
    private int capacity;
    private Boolean status;
}
