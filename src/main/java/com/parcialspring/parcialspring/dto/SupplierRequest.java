package com.parcialspring.parcialspring.dto;

import lombok.Data;

@Data
public class SupplierRequest {
    private String supplierId;
    private String name;
    private String phone;
    private String email;
    private String cityId;
    private String stateId;
    private String countryId;
    private String nit;
    private String address;
}