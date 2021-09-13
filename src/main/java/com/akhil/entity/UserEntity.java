package com.akhil.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id",length = 255)
    String userId;

    @Column(name="first_name",length = 50,nullable = false)
    String firstName;

    @Column(name = "last_name",length = 50, nullable = false)
    String lastName;

    @Column(name = "email_id",length = 100, nullable = false,unique = true)
    String emailId;

    @Column(name = "password",nullable = false)
    String password;
}
