package com.parcialspring.parcialspring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta con los datos completos de una cabra")
public class GoatResponse {
    @Schema(description = "ID de la base de datos", example = "1")
    private Long id;

    @Schema(description = "Identificador único de la cabra", example = "CAB001")
    private String goatId;

    @Schema(description = "Nombre de la cabra", example = "Margarita")
    private String name;

    @Schema(description = "Raza de la cabra", example = "Saanen")
    private String breed;

    @Schema(description = "Fecha de nacimiento", example = "2023-01-15")
    private String birthDate;

    @Schema(description = "Género de la cabra", example = "Hembra")
    private String gender;

    @Schema(description = "Tipo de cabra", example = "Lechera")
    private String goatType;

    @Schema(description = "Peso en kilogramos", example = "45.5")
    private Double weight;

    @Schema(description = "Producción de leche en litros", example = "2.5")
    private Double milkProduction;

    @Schema(description = "Consumo de alimento en kilogramos", example = "3.0")
    private Double foodConsumption;

    @Schema(description = "Cantidad de vacunas aplicadas", example = "4")
    private Integer vaccinationsCount;

    @Schema(description = "Número de períodos de celo", example = "2")
    private Integer heatPeriods;

    @Schema(description = "Cantidad de crías", example = "1")
    private Integer offspringCount;

    @Schema(description = "ID del padre/madre", example = "CAB002")
    private String parentId;

    @Schema(description = "Estado de la cabra", example = "Activa")
    private String status;

    @Schema(description = "Notas adicionales", example = "Cabra en buen estado de salud")
    private String notes;

    @Schema(description = "Fecha y hora de creación del registro", example = "2023-12-03T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Fecha y hora de última actualización", example = "2023-12-03T10:30:00")
    private LocalDateTime updatedAt;
}
