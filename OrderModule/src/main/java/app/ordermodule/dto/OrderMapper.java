package app.ordermodule.dto;

import app.ordermodule.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);
    OrderDto toDto(OrderModel orderModel);
    OrderModel toEntity(OrderDto orderDto);
    OrderModel saveDtoToEntity(OrderToSaveDto orderToSaveDto);
}
