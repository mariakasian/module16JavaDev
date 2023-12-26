package com.maria.module16.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "password"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @Size(max = 50)
    private String username;

    @Column
    @NotBlank
    @Size(max = 100)
    private String password;

    @Column
    @NotBlank
    @Size(max = 50)
    private String email;

    @Column
    @NotBlank
    @Size(max = 50)
    private String authority;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String email, String authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
    }
}
