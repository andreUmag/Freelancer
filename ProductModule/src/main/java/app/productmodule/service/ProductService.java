package app.productmodule.service;

import app.productmodule.dto.ProductDto;
import app.productmodule.dto.ProductToSaveDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto saveProduct(ProductToSaveDto productToSaveDto);
    List<ProductDto> getAllProducts();
    Optional<ProductDto> getProductById(Long id);
    ProductDto updateProduct(Long id,ProductToSaveDto productToSaveDto);
    void deleteProduct(long id);
}
