package com.parcialspring.parcialspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.parcialspring.parcialspring.model.UserModel;

@Data
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String idCard;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;

}
