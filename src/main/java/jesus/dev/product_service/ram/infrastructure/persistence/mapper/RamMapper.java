package jesus.dev.product_service.ram.infrastructure.persistence.mapper;

import jesus.dev.product_service.ram.domain.model.Ram;
import jesus.dev.product_service.ram.infrastructure.persistence.entity.RamEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RamMapper {
    RamEntity domainToEntity(Ram ram);

    Ram entityToDomain(RamEntity entity);
}
