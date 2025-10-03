package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.ProductRequest;
import com.parcialspring.parcialspring.dto.ProductResponse;
import com.parcialspring.parcialspring.service.ProductService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Data
public class ProductController {

    private final ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    //Endpoints

    // Endpoint para crear producto
    // POST http://localhost:8080/products
    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return service.createProduct(request);
    }

    // Endpoint para listar todos los productos
    // GET http://localhost:8080/products
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    // Endpoint para buscar producto por id
    // GET http://localhost:8080/products/{id}
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // Endpoint para actualizar producto
    // PUT http://localhost:8080/products/{id}
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        return service.updateProduct(id, request);
    }

    // Endpoint para eliminar producto
    // DELETE http://localhost:8080/products/{id}
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProductById(id);
    }
}

