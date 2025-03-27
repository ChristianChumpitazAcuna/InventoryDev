package jesus.dev.product_service.screen.domain.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScreenRequestDTO {
    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ -]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
    private String brand;

    @NotBlank(message = "Screen Size is required")
    @Size(max = 4, message = "Screen Size cannot exceed 4 characters")
    @Pattern(
            regexp = "^[0-9']+$",
            message = "Only numbers and apostrophe are allowed"
    )
    private String screenSize;

    @NotEmpty(message = "At least one port is required")
    @Size(max = 5, message = "You can add up to 5 ports")
    private List<@Pattern(
            regexp = "^[a-zA-Z0-9. ]+$",
            message = "Only letters and numbers are allowed"
    ) String> ports;

    @NotBlank
    @Pattern(
            regexp = "^[a-zA-Z]+$",
            message = "Only letters are allowed"
    )
    private String colour;

    @NotNull(message = "Warranty is required")
    private Boolean warranty;
}
