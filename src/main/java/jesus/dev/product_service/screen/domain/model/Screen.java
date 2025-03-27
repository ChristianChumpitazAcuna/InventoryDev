package jesus.dev.product_service.screen.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Screen {
    private String id;
    private String brand;
    private String screenSize;
    private List<String> ports;
    private String colour;
    private Boolean warranty;
    private Boolean status;
}
