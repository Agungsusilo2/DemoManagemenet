package org.example.demomanagemenet.transaksi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListTransaksiRequest {
    private int page;
    private int size;
}
