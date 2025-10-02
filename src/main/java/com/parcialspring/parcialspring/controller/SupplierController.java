package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.SupplierRequest;
import com.parcialspring.parcialspring.dto.SupplierResponse;
import com.parcialspring.parcialspring.service.SupplierService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@Data
public class SupplierController {

    private final SupplierService service;

    SupplierController(SupplierService service) {
        this.service = service;
    }

    //Endpoints

    //Endpoint para crear proveedor
    //Metodo POST http://localhost:8080/suppliers

    @PostMapping
    public SupplierResponse createSupplier(@RequestBody SupplierRequest request) {
        return service.createSupplier(request);
    }

    //Endpoint para listar todos los proveedores
    //Metodo GET http://localhost:8080/suppliers
    @GetMapping
    public List<SupplierResponse> findAllSuppliers() {
        return service.findAllSuppliers();
    }

    //Endpoint para buscar proveedor por id
    //Metodo GET http://localhost:8080/suppliers/{id}

    @GetMapping("/{id}")
    public SupplierResponse findSupplierById(@PathVariable Long id) {
        return service.findSupplierById(id);
    }

    //Endpoint para actualizar proveedor
    //Metodo PUT http://localhost:8080/suppliers/{id}
    @PutMapping("/{id}")
    public SupplierResponse updateSupplier(@PathVariable Long id, @RequestBody SupplierRequest request) {
        return service.updateSupplier(id, request);
    }

    //Endpoint para eliminar proveedor
    //Metodo DELETE http://localhost:8080/suppliers/{id}
    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Long id) {
        service.deleteSupplierById(id);
    }
}
