package jesus.dev.product_service.product.application.mapper;

import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.domain.model.dto.ProductRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductUseCaseMapper {

    Product dtoToDomain(ProductRequestDTO dto);
}
