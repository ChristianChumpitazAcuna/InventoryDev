package jesus.dev.inventory.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CpuRequestDTO {
    @NotBlank(message = "IP address is required")
    @Pattern(
            regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$",
            message = "Invalid IP address format"
    )
    private String ipAddress;

    @Pattern(
            regexp = "^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$",
            message = "Invalid MAC address format"
    )
    private String macAddress;

    @Size(max = 50, message = "Brand cannot exceed 50 characters")
    private String brand;

    @Size(max = 50, message = "Model cannot exceed 50 characters")
    private String model;

    @Size(max = 100, message = "Serial number cannot exceed 100 characters")
    private String serialNumber;

    @Size(max = 50, message = "Operating System cannot exceed 50 characters")
    private String operatingSystem;

    @NotBlank(message = "ProcessorId is required")
    private String processorId;

    @NotBlank(message = "RamId is required")
    private String ramId;

    @NotBlank(message = "VideoCardId is required")
    private String videoCardId;

    @NotBlank(message = "StorageId is required")
    private String storageId;

    @NotBlank(message = "ScreenId is required")
    private String screenId;
}
