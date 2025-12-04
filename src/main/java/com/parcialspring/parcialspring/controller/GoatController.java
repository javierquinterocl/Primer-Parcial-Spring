package com.parcialspring.parcialspring.controller;

import com.parcialspring.parcialspring.dto.GoatRequest;
import com.parcialspring.parcialspring.dto.GoatResponse;
import com.parcialspring.parcialspring.service.GoatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/goats")
@Data
@Tag(name = "Goats (Cabras)", description = "Endpoints para la gestión de cabras del proyecto caprino")
public class GoatController {

    private final GoatService service;

    public GoatController(GoatService service) {
        this.service = service;
    }

    // Crear cabra
    //Endpoint para crear cabra
    //Metodo POST http://localhost:8080/goats
    @Operation(
            summary = "Crear una nueva cabra",
            description = "Registra una nueva cabra en el sistema con todos sus datos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabra creada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoatResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public GoatResponse createGoat(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la cabra a crear",
                    required = true
            )
            @RequestBody GoatRequest request) {
        return service.createGoat(request);
    }

    // Listar todas
    //Endpoint para listar todas las cabras
    //Metodo GET http://localhost:8080/goats
    @Operation(
            summary = "Listar todas las cabras",
            description = "Obtiene la lista completa de todas las cabras registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de cabras obtenida exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoatResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<GoatResponse> findAllGoats() {
        return service.findAllGoats();
    }

    // Buscar por id
    //Endpoint para buscar cabra por id
    //Metodo GET http://localhost:8080/goats/{id}
    @Operation(
            summary = "Buscar cabra por ID",
            description = "Obtiene los detalles de una cabra específica mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabra encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoatResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cabra no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public GoatResponse findGoatById(
            @Parameter(description = "ID de la cabra a buscar", required = true)
            @PathVariable Long id) {
        return service.findGoatById(id);
    }

    // Actualizar
    //Endpoint para actualizar cabra
    //Metodo PUT http://localhost:8080/goats/{id}
    @Operation(
            summary = "Actualizar cabra",
            description = "Actualiza los datos de una cabra existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cabra actualizada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GoatResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Cabra no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public GoatResponse updateGoat(
            @Parameter(description = "ID de la cabra a actualizar", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevos datos de la cabra",
                    required = true
            )
            @RequestBody GoatRequest request) {
        return service.updateGoat(id, request);
    }

    // Eliminar
    //Endpoint para eliminar cabra
    //Metodo DELETE http://localhost:8080/goats/{id}
    @Operation(
            summary = "Eliminar cabra",
            description = "Elimina una cabra del sistema de forma permanente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cabra eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cabra no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public void deleteGoat(
            @Parameter(description = "ID de la cabra a eliminar", required = true)
            @PathVariable Long id) {
        service.deleteGoatById(id);
    }

    // Exportar a Excel
    //Endpoint para exportar cabras a Excel
    //Metodo GET http://localhost:8080/goats/export/excel
    @Operation(
            summary = "Exportar cabras a Excel",
            description = "Genera y descarga un archivo Excel (.xlsx) con todos los datos de las cabras"
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
            headers.setContentDispositionFormData("attachment", "cabras.xlsx");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Exportar a PDF
    //Endpoint para exportar cabras a PDF
    //Metodo GET http://localhost:8080/goats/export/pdf
    @Operation(
            summary = "Exportar cabras a PDF",
            description = "Genera y descarga un archivo PDF con un reporte tabular de todas las cabras"
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
            headers.setContentDispositionFormData("attachment", "cabras.pdf");

            return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
