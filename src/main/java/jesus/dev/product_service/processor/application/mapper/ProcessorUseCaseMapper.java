package jesus.dev.product_service.processor.application.mapper;

import jesus.dev.product_service.processor.domain.model.Processor;
import jesus.dev.product_service.processor.domain.model.dto.ProcessorRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessorUseCaseMapper {
    Processor dtoToDomain(ProcessorRequestDTO dto);
}
