package jesus.dev.product_service.screen.application.mapper;

import jesus.dev.product_service.screen.domain.model.Screen;
import jesus.dev.product_service.screen.domain.model.dto.ScreenRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScreenUseCaseMapper {
    Screen dtoToDomain(ScreenRequestDTO dto);
}
