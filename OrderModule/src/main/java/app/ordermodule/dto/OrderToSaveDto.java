package app.ordermodule.dto;

public record OrderToSaveDto(Long id, Long user_id, Long product_id, String state, Integer quantity) {
}
