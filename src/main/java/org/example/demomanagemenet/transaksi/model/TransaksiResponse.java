package org.example.demomanagemenet.transaksi.model;

import lombok.Data;
import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.supplier.entity.Supplier;

@Data
public class TransaksiResponse {
    private String id;
    private long date;
    private Type type;
    private int total;
    private Barang barang;
    private Supplier supplier;
}
