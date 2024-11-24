package app.ordermodule.service;

import app.ordermodule.dto.OrderDto;
import app.ordermodule.dto.OrderToSaveDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getOrdersByProductId(Long productId);
    OrderDto saveOrder(OrderToSaveDto orderToSaveDto);
    List<OrderDto> getAllOrders();
    Optional<OrderDto> getOrderById(Long id);
    OrderDto updateOrder(Long id,OrderToSaveDto orderToSaveDto);
    void deleteOrder(long id);
}
