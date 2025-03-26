package jesus.dev.product_service.product.infraestructure.persistence.mapper;

import jesus.dev.product_service.product.domain.model.Product;
import jesus.dev.product_service.product.infraestructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);
}
