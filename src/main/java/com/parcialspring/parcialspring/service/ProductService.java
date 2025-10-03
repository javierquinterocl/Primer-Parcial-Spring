package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.dto.ProductRequest;
import com.parcialspring.parcialspring.dto.ProductResponse;
import com.parcialspring.parcialspring.model.ProductModel;
import com.parcialspring.parcialspring.repository.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ProductService {

    private final ProductRepository repository;

    ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Métodos del servicio

    // Metodo para Crear producto usando Repository Save
    public ProductResponse createProduct(ProductRequest request) {
        ProductModel product = new ProductModel();
        product.setProductId(request.getProductId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setStock(request.getStock());
        product.setProductType(request.getProductType());

        ProductModel newProduct = repository.save(product);

        return new ProductResponse(
                newProduct.getId(),
                newProduct.getProductId(),
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getUnitPrice(),
                newProduct.getStock(),
                newProduct.getProductType()
        );
    }

    // Metodo para Listar todos los productos
    public List<ProductResponse> getAllProducts() {
        List<ProductModel> products = repository.findAll();

        return products.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getProductId(),
                        p.getName(),
                        p.getDescription(),
                        p.getUnitPrice(),
                        p.getStock(),
                        p.getProductType()
                )).toList();
    }

    // Metodo para Buscar producto por id
    public ProductResponse getProductById(Long id) {
        ProductModel product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado por id " + id));

        return new ProductResponse(
                product.getId(),
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getStock(),
                product.getProductType()
        );
    }

    // Metodo para Actualizar producto por id
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        ProductModel product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado por id " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUnitPrice(request.getUnitPrice());
        product.setStock(request.getStock());
        product.setProductType(request.getProductType());

        ProductModel updatedProduct = repository.save(product);

        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getProductId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getUnitPrice(),
                updatedProduct.getStock(),
                updatedProduct.getProductType()
        );
    }

    // Metodo para Eliminar producto por id
    public void deleteProductById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado por id " + id);
        }
        repository.deleteById(id);
    }
}
