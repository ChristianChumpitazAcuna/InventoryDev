package jesus.dev.product_service.screen.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("screen")
public class ScreenEntity {
    @Id
    private String id;
    private String brand;
    private String screenSize;
    private List<String> ports;
    private String colour;
    private Boolean warranty;
    private Boolean status;
}
