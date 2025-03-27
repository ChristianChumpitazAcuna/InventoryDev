package jesus.dev.product_service.processor.infrastructure.persistence.mapper;

import jesus.dev.product_service.processor.domain.model.Processor;
import jesus.dev.product_service.processor.infrastructure.persistence.entity.ProcessorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProcessorMapper {

    Processor toDomain(ProcessorEntity entity);

    ProcessorEntity toEntity(Processor processor);
}
