package app.productmodule.dto;

import app.productmodule.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductDto toDto(ProductModel productModel);
    ProductModel toEntity(ProductDto productDto);
    ProductModel saveDtoToEntity(ProductToSaveDto productToSaveDto);
}
