package app.ordermodule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDto(Long id, String name, Double price, String description) {
}
