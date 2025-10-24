package com.parcialspring.parcialspring.dto;

import lombok.Data;

@Data
public class GoatRequest {
    private String goatId;
    private String name;
    private String breed;
    private String birthDate;
    private String gender;
    private String goatType;
    private Double weight;
    private Double milkProduction;
    private Double foodConsumption;
    private Integer vaccinationsCount;
    private Integer heatPeriods;
    private Integer offspringCount;
    private String parentId;
    private String status;
    private String notes;
}

