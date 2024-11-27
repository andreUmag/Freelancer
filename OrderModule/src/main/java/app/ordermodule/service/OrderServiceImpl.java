package app.ordermodule.service;
import app.ordermodule.client.ProductClient;
import app.ordermodule.dto.OrderDto;
import app.ordermodule.dto.OrderMapper;
import app.ordermodule.dto.OrderToSaveDto;
import app.ordermodule.dto.ProductDto;
import app.ordermodule.model.OrderModel;
import app.ordermodule.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productClient = productClient;
    }

    @Override
    public OrderDto saveOrder(OrderToSaveDto orderToSaveDto) {
        ResponseEntity<?> productResponse = productClient.getProductById(orderToSaveDto.product_id());

        if (!productResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Product with ID " + orderToSaveDto.product_id() + " does not exist.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto product = objectMapper.convertValue(productResponse.getBody(), ProductDto.class);

        if (product.stock() < orderToSaveDto.quantity()) {
            throw new RuntimeException("Stock insufficient para el product: " + orderToSaveDto.product_id() +
                    ". Stock disposable: " + product.stock() + ", requested: " + orderToSaveDto.quantity());
        }

        OrderModel orderModel = orderMapper.saveDtoToEntity(orderToSaveDto);
        OrderModel orderModelSaved = orderRepository.save(orderModel);
        return orderMapper.toDto(orderModelSaved);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderModel> orderModels = orderRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();

        return orderModels.stream().map(orderModel -> {
            ResponseEntity<?> productResponse = productClient.getProductById(orderModel.getProduct_id());
            ProductDto product = null;
            if (productResponse.getStatusCode().is2xxSuccessful()) {
                product = objectMapper.convertValue(productResponse.getBody(), ProductDto.class);
            }
            OrderDto orderDto = orderMapper.toDto(orderModel);
            return new OrderDto(orderDto.id(), orderDto.user_email(), orderDto.product_id(), product, orderDto.state(), orderDto.quantity());
        }).toList();
    }

    @Override
    public Optional<OrderDto> getOrderById(Long id) {
        Optional<OrderModel> orderModel = orderRepository.findById(id);
        ObjectMapper objectMapper = new ObjectMapper();

        return orderModel.map(order -> {
            ResponseEntity<?> productResponse = productClient.getProductById(order.getProduct_id());
            ProductDto product = null;
            if (productResponse.getStatusCode().is2xxSuccessful()) {
                product = objectMapper.convertValue(productResponse.getBody(), ProductDto.class);
            }
            OrderDto orderDto = orderMapper.toDto(order);
            return new OrderDto(orderDto.id(), orderDto.user_email(), orderDto.product_id(), product, orderDto.state(), orderDto.quantity());
        });
    }

    @Override
    public List<OrderDto> getOrdersByProductId(Long productId) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<OrderModel> orderModels = orderRepository.findAll().stream()
                .filter(order -> order.getProduct_id().equals(productId))
                .toList();

        return orderModels.stream().map(orderModel -> {
            ResponseEntity<?> productResponse = productClient.getProductById(orderModel.getProduct_id());
            ProductDto product = null;
            if (productResponse.getStatusCode().is2xxSuccessful()) {
                product = objectMapper.convertValue(productResponse.getBody(), ProductDto.class);
            }

            OrderDto orderDto = orderMapper.toDto(orderModel);
            return new OrderDto(orderDto.id(), orderDto.user_email(), orderDto.product_id(), product, orderDto.state(), orderDto.quantity());
        }).toList();
    }


    @Override
    public OrderDto updateOrder(Long id, OrderToSaveDto orderToSaveDto) {
        Optional<OrderModel> existingOrder = orderRepository.findById(id);

        if (existingOrder.isPresent()) {
            OrderModel orderModel = existingOrder.get();

            ResponseEntity<?> productResponse = productClient.getProductById(orderToSaveDto.product_id());
            if (!productResponse.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Product with ID " + orderToSaveDto.product_id() + " does not exist.");
            }

            orderModel.setUser_email(orderToSaveDto.user_email());
            orderModel.setProduct_id(orderToSaveDto.product_id());
            orderModel.setQuantity(orderToSaveDto.quantity());
            orderModel.setState(orderToSaveDto.state());
            OrderModel updatedOrder = orderRepository.save(orderModel);
            return orderMapper.toDto(updatedOrder);
        }

        throw new RuntimeException("Order not found by id " + id);
    }

    @Override
    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}

