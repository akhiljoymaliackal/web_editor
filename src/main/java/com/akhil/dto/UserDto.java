package com.akhil.dto;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String unEncryptedPassword;
    private String password;
}
