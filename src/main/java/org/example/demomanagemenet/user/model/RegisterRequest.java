package org.example.demomanagemenet.user.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String username;
    @NotBlank
    @Size(min = 1, max = 50)
    private String password;
    @NotNull
    private Role role;
}
