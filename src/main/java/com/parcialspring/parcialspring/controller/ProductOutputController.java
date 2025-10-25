package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.ProductOutputRequest;
import com.parcialspring.parcialspring.dto.ProductOutputResponse;
import com.parcialspring.parcialspring.service.ProductOutputService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-outputs")
@Data
public class ProductOutputController {

    private final ProductOutputService service;

    ProductOutputController(ProductOutputService service) {
        this.service = service;
    }

    // Endpoints

    // Endpoint para crear salida de producto
    // POST http://localhost:8080/product-outputs
    @PostMapping
    public ProductOutputResponse createProductOutput(@RequestBody ProductOutputRequest request) {
        return service.createProductOutput(request);
    }

    // Endpoint para listar todas las salidas de productos
    // GET http://localhost:8080/product-outputs
    @GetMapping
    public List<ProductOutputResponse> findAllProductOutputs() {
        return service.findAllProductOutputs();
    }

    // Endpoint para buscar salida de producto por id
    // GET http://localhost:8080/product-outputs/{id}
    @GetMapping("/{id}")
    public ProductOutputResponse findProductOutputById(@PathVariable Long id) {
        return service.findProductOutputById(id);
    }

    // Endpoint para eliminar salida de producto
    // DELETE http://localhost:8080/product-outputs/{id}
    @DeleteMapping("/{id}")
    public void deleteProductOutput(@PathVariable Long id) {
        service.deleteProductOutputById(id);
    }
}

