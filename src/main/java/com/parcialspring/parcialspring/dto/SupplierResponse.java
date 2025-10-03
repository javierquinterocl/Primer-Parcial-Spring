package com.parcialspring.parcialspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierResponse {

    private Long id;
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