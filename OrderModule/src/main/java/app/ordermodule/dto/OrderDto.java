package app.ordermodule.dto;

public record OrderDto(Long id, Long user_id, Long product_id, String state, Integer quantity) {

}
