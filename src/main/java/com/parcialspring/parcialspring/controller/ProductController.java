package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.ProductRequest;
import com.parcialspring.parcialspring.dto.ProductResponse;
import com.parcialspring.parcialspring.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
@Data
@Tag(name = "Products (Productos)", description = "Endpoints para la gestión de productos lácteos")
public class ProductController {

    private final ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    // Endpoint para crear producto
    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return service.createProduct(request);
    }

    // Endpoint para listar todos los productos
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    // Endpoint para buscar producto por id
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // Endpoint para actualizar producto
    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        return service.updateProduct(id, request);
    }

    // Endpoint para eliminar producto
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProductById(id);
    }

    // ----------------------------
    // EXPORTAR PRODUCTOS A EXCEL
    // ----------------------------
    // GET http://localhost:8080/products/export/excel
    @Operation(
            summary = "Exportar productos a Excel",
            description = "Genera y descarga un archivo Excel (.xlsx) con todos los datos de los productos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Archivo Excel generado exitosamente",
                    content = @Content(
                            mediaType = "application/octet-stream"
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error al generar el archivo Excel")
    })
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportToExcel() {
        try {
            byte[] excelData = service.exportToExcel();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "productos.xlsx");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ----------------------------
    // EXPORTAR PRODUCTOS A PDF
    // ----------------------------
    // GET http://localhost:8080/products/export/pdf
    @Operation(
            summary = "Exportar productos a PDF",
            description = "Genera y descarga un archivo PDF con un reporte tabular de todos los productos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Archivo PDF generado exitosamente",
                    content = @Content(
                            mediaType = "application/pdf"
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error al generar el archivo PDF")
    })
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportToPdf() {
        try {
            byte[] pdfData = service.exportToPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "productos.pdf");

            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
