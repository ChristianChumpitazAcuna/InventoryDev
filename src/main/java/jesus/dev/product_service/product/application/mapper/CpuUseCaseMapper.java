package jesus.dev.product_service.product.application.mapper;

import jesus.dev.product_service.product.domain.model.Cpu;
import jesus.dev.product_service.product.domain.model.dto.CpuRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CpuUseCaseMapper {

    Cpu dtoToDomain(CpuRequestDTO dto);
}
