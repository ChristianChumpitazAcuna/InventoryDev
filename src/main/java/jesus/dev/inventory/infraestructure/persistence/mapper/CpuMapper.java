package jesus.dev.inventory.infraestructure.persistence.mapper;

import jesus.dev.inventory.domain.model.Cpu;
import jesus.dev.inventory.infraestructure.persistence.entity.CpuEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CpuMapper {
    Cpu toDomain(CpuEntity entity);

    CpuEntity toEntity(Cpu domain);
}
