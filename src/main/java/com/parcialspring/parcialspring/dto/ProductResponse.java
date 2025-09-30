package com.parcialspring.parcialspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String productId;
    private String name;
    private String description;
    private Integer unitPrice;
    private Integer stock;
    private String productType;
}