package jesus.dev.product_service.ram.domain.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RamRequestDTO {
    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ -]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
    private String brand;

    @NotBlank(message = "Model is required")
    @Size(max = 50, message = "Model cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ -]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
    private String model;

    @Min(value = 1, message = "Capacity must be at least 1GB")
    @Max(value = 256, message = "Capacity cannot exceed 256GB")
    private int capacity;
}
