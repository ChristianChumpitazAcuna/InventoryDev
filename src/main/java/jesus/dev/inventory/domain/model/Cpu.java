package jesus.dev.inventory.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Cpu {
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
