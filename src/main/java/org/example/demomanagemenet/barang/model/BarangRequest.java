package org.example.demomanagemenet.barang.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class BarangRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NotBlank
    @Size(min = 2,max = 50)
    private String category;
    @NotNull
    private int price;
    @NotNull
    private int stock;
    @NotBlank
    private String description;
}
