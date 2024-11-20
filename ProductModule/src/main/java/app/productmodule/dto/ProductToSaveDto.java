package app.productmodule.dto;

public record ProductToSaveDto(Long id, String name, String description, Float price, Integer stock) {
}
