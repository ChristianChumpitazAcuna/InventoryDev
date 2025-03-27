package jesus.dev.product_service.ram.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ram {
    private String id;
    private String brand;
    private String model;
    private int capacity;
    private Boolean status;
}
