package jesus.dev.product_service.ram.application.mapper;

import jesus.dev.product_service.ram.domain.model.Ram;
import jesus.dev.product_service.ram.domain.model.dto.RamRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RamUseCaseMapper {
    Ram dtoToDomain(RamRequestDTO dto);
}
