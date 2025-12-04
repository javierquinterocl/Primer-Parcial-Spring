package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.SupplierRequest;
import com.parcialspring.parcialspring.dto.SupplierResponse;
import com.parcialspring.parcialspring.service.SupplierService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

@RestController
@RequestMapping("/suppliers")
@Data
@Tag(name = "Suppliers (Proveedores)", description = "Endpoints para la gestión de proveedores del proyecto caprino")
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

    // ----------------------------
    // EXPORTAR PROVEEDORES A EXCEL
    // ----------------------------
    @Operation(
            summary = "Exportar proveedores a Excel",
            description = "Genera y descarga un archivo Excel (.xlsx) con todos los proveedores registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Archivo Excel generado exitosamente",
                    content = @Content(mediaType = "application/octet-stream")
            ),
            @ApiResponse(responseCode = "500", description = "Error al generar el archivo Excel")
    })
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportSuppliersToExcel() {
        try {
            byte[] excelData = service.exportToExcel();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "proveedores.xlsx");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------------------
    // EXPORTAR PROVEEDORES A PDF
    // ----------------------------
    @Operation(
            summary = "Exportar proveedores a PDF",
            description = "Genera y descarga un archivo PDF con un reporte de todos los proveedores"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Archivo PDF generado exitosamente",
                    content = @Content(mediaType = "application/pdf")
            ),
            @ApiResponse(responseCode = "500", description = "Error al generar el archivo PDF")
    })
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportSuppliersToPdf() {
        try {
            byte[] pdfData = service.exportToPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "proveedores.pdf");

            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
