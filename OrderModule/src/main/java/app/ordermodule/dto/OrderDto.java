package app.ordermodule.dto;

public record OrderDto(Long id, String user_email, Long product_id, String state, Integer quantity) {

}
