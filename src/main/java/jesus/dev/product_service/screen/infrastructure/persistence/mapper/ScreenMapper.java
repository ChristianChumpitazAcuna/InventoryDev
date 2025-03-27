package jesus.dev.product_service.screen.infrastructure.persistence.mapper;

import jesus.dev.product_service.screen.domain.model.Screen;
import jesus.dev.product_service.screen.infrastructure.persistence.entity.ScreenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScreenMapper {
    ScreenEntity domainToEntity(Screen screen);

    Screen entityToDomain(ScreenEntity entity);
}
