package app.productmodule.dto;

public record ProductDto(Long id, String name, String description, Float price, Integer stock) {
}
