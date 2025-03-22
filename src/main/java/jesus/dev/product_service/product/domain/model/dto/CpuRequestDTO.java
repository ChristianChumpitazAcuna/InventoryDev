package jesus.dev.product_service.product.domain.model.dto;

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

    @NotBlank(message = "MAC Address is required")
    @Pattern(
            regexp = "^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$",
            message = "Invalid MAC address format"
    )
    private String macAddress;

    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ-]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 50, message = "Model cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ-]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
    private String model;

    @NotBlank(message = "Serial Number is required")
    @Size(max = 100, message = "Serial number cannot exceed 100 characters")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "Only capital letters and numbers are allowed"
    )
    private String serialNumber;

    @NotBlank(message = "Operating System is required")
    @Size(max = 50, message = "Operating System cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ-]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
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
