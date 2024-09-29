package org.example.demomanagemenet.transaksi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.transaksi.model.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaksi {
    @Id
    private String id;
    private Long date;
    @Enumerated(EnumType.STRING)
    private Type type;
    private int total;
    @ManyToOne
    @JoinColumn(name = "id_barang",referencedColumnName = "id")
    private Barang barang;
    @ManyToOne
    @JoinColumn(name = "id_supplier",referencedColumnName = "id")
    private Supplier supplier;

}
