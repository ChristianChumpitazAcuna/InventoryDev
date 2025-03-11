package jesus.dev.inventory.application.mapper;

import jesus.dev.inventory.domain.model.Cpu;
import jesus.dev.inventory.domain.model.dto.CpuRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CpuUseCaseMapper {

    Cpu dtoToDomain(CpuRequestDTO dto);
}
