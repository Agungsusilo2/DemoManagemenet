package org.example.demomanagemenet.barang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListBarangRequest {
    private int page;
    private int size;
}
