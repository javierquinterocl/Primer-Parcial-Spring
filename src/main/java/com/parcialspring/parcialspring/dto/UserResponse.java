package com.parcialspring.parcialspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String idCard;
    private String code;
    private String fisrtName;
    private String lastName;
    private String email;
    private String phone;
}
