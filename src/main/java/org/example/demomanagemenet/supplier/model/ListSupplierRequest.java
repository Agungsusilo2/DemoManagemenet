package org.example.demomanagemenet.supplier.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListSupplierRequest {
    private int page;
    private int size;
}
