package com.parcialspring.parcialspring.dto;

import lombok.Data;

@Data
public class ProductOutputRequest {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String notes;
}

