package jesus.dev.product_service.product.infraestructure.persistence.mapper;

import jesus.dev.product_service.product.domain.model.Cpu;
import jesus.dev.product_service.product.infraestructure.persistence.entity.CpuEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CpuMapper {
    Cpu toDomain(CpuEntity entity);

    CpuEntity toEntity(Cpu domain);
}
