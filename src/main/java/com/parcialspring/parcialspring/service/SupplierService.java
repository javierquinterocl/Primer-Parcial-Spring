package com.parcialspring.parcialspring.service;

import com.parcialspring.parcialspring.dto.SupplierRequest;
import com.parcialspring.parcialspring.dto.SupplierResponse;
import com.parcialspring.parcialspring.model.SupplierModel;
import com.parcialspring.parcialspring.repository.SupplierRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class SupplierService {

    private final SupplierRepository repository;

    SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    // ---------------- Métodos del servicio ----------------

    // Crear un proveedor
    public SupplierResponse createSupplier(SupplierRequest request) {
        SupplierModel supplier = new SupplierModel();
        supplier.setSupplierId(request.getName().substring(0, 3).toUpperCase() + System.currentTimeMillis()); // Ejemplo de generar supplierId
        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setCityId(request.getCityId());
        supplier.setStateId(request.getStateId());
        supplier.setCountryId(request.getCountryId());
        supplier.setNit(request.getNit());
        supplier.setAddress(request.getAddress());

        SupplierModel newSupplier = repository.save(supplier);

        return new SupplierResponse(
                newSupplier.getId(),
                newSupplier.getSupplierId(),
                newSupplier.getName(),
                newSupplier.getPhone(),
                newSupplier.getEmail(),
                newSupplier.getCityId(),
                newSupplier.getStateId(),
                newSupplier.getCountryId(),
                newSupplier.getNit(),
                newSupplier.getAddress()
        );
    }

    // Listar todos los proveedores
    public List<SupplierResponse> findAllSuppliers() {
        List<SupplierModel> suppliers = repository.findAll();

        return suppliers.stream()
                .map(s -> new SupplierResponse(
                        s.getId(),
                        s.getSupplierId(),
                        s.getName(),
                        s.getPhone(),
                        s.getEmail(),
                        s.getCityId(),
                        s.getStateId(),
                        s.getCountryId(),
                        s.getNit(),
                        s.getAddress()
                ))
                .toList();
    }

    // Buscar proveedor por ID
    public SupplierResponse findSupplierById(Long id) {
        SupplierModel supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + id));

        return new SupplierResponse(
                supplier.getId(),
                supplier.getSupplierId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getCityId(),
                supplier.getStateId(),
                supplier.getCountryId(),
                supplier.getNit(),
                supplier.getAddress()
        );
    }

    // Actualizar proveedor por ID
    public SupplierResponse updateSupplier(Long id, SupplierRequest request) {
        SupplierModel supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id " + id));

        supplier.setName(request.getName());
        supplier.setPhone(request.getPhone());
        supplier.setEmail(request.getEmail());
        supplier.setCityId(request.getCityId());
        supplier.setStateId(request.getStateId());
        supplier.setCountryId(request.getCountryId());
        supplier.setNit(request.getNit());
        supplier.setAddress(request.getAddress());

        SupplierModel updatedSupplier = repository.save(supplier);

        return new SupplierResponse(
                updatedSupplier.getId(),
                updatedSupplier.getSupplierId(),
                updatedSupplier.getName(),
                updatedSupplier.getPhone(),
                updatedSupplier.getEmail(),
                updatedSupplier.getCityId(),
                updatedSupplier.getStateId(),
                updatedSupplier.getCountryId(),
                updatedSupplier.getNit(),
                updatedSupplier.getAddress()
        );
    }

    // Eliminar proveedor por ID
    public void deleteSupplierById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con id " + id);
        }
        repository.deleteById(id);
    }
}