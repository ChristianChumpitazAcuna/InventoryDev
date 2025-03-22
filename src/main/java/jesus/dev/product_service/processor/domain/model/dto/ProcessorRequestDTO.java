package jesus.dev.product_service.processor.domain.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessorRequestDTO {

    @NotNull(message = "Name is required")
    @Size(max = 50, message = "Brand cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "Only letters are allowed"
    )
    private String name;

    @NotBlank(message = "Brand is required")
    @Size(max = 50, message = "Brand cannot exceed 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ -]+$",
            message = "Only letters, numbers, accents, and hyphens are allowed"
    )
    private String generation;
}
