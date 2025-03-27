package jesus.dev.product_service.processor.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("processor")
@Getter
@Setter
public class ProcessorEntity {
    @Id
    private String id;
    private String name;
    private String generation;
    private Boolean status;
}
