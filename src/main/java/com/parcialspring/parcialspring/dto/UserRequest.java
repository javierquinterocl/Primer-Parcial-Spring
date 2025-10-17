package com.parcialspring.parcialspring.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String idCard;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String role;
}
