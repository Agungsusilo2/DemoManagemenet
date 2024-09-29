package org.example.demomanagemenet.supplier.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NotBlank
    @Size(min = 2, max = 50)
    private String contact;
    @NotBlank
    @Size(min = 2, max = 50)
    private String address;
}