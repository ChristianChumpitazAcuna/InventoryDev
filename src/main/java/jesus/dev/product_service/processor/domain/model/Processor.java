package jesus.dev.product_service.processor.domain.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Processor {
    private String id;
    private String name;
    private String generation;
    private Boolean status;
}
