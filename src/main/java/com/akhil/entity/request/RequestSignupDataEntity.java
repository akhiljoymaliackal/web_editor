package com.akhil.entity.request;

import lombok.Data;

@Data
public class RequestSignupDataEntity {
    private String firstName;
    private String lastName;
    private String emailId;
    private String unEncryptedPassword;
}
