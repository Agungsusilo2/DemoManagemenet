package org.example.demomanagemenet.transaksi.repository;

import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.transaksi.entity.Transaksi;
import org.example.demomanagemenet.transaksi.model.TransaksiResponse;
import org.example.demomanagemenet.transaksi.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaksiRepository extends JpaRepository<Transaksi, String>, JpaSpecificationExecutor<Transaksi> {
    Optional<List<Transaksi>> findAllByType(Type type);
    List<Transaksi> findByDateBetween(long from, long to);
    List<Transaksi> findByType(Type type);
    List<Transaksi> findBySupplier(Supplier supplier);
}
