package org.example.demomanagemenet.barang.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarangResponse {
    private String id;
    private String name;
    private String category;
    private int price;
    private int stock;
    private String description;
}
