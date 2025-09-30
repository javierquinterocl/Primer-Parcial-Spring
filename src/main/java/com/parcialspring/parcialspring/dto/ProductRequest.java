package com.parcialspring.parcialspring.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String productId;
    private String name;
    private String description;
    private Integer unitPrice;
    private Integer stock;
    private String productType;
}
