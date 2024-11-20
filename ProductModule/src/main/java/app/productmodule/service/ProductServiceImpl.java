package app.productmodule.service;

import app.productmodule.dto.ProductDto;
import app.productmodule.dto.ProductMapper;
import app.productmodule.dto.ProductToSaveDto;
import app.productmodule.model.ProductModel;
import app.productmodule.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto saveProduct(ProductToSaveDto productToSaveDto) {
        ProductModel productModel = productMapper.saveDtoToEntity(productToSaveDto);
        ProductModel productModelSaved = productRepository.save(productModel);
        return productMapper.toDto(productModelSaved);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductModel> productModels = productRepository.findAll();
        return productModels.stream().map(productMapper::toDto).toList();
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        Optional<ProductModel> productModel = productRepository.findById(id);
        return productModel.map(productMapper::toDto);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductToSaveDto productToSaveDto) {
        Optional<ProductModel> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            ProductModel productModel = existingProduct.get();
            productModel.setName(productToSaveDto.name());
            productModel.setPrice(productToSaveDto.price());
            productModel.setDescription(productToSaveDto.description());
            productModel.setStock(productToSaveDto.stock());
            ProductModel updatedProduct = productRepository.save(productModel);
            return productMapper.toDto(updatedProduct);
        }

        throw  new RuntimeException("Product not found by id " + id);
    }

    @Override
    public void deleteProduct(long id) { productRepository.deleteById(id); }
}
