package org.example.demomanagemenet.transaksi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.supplier.entity.Supplier;

@Data
public class TransaksiRequest {
    @NotNull
    private Long date;
    @NotNull
    private Type type;
    @NotNull
    private int total;
    @NotBlank
    private String barangId;
    @NotBlank
    private String supplierId;
}
