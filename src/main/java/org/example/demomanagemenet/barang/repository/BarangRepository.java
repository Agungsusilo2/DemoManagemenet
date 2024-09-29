package org.example.demomanagemenet.barang.repository;

import org.example.demomanagemenet.barang.entity.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarangRepository extends JpaRepository<Barang, String> , JpaSpecificationExecutor<Barang> {
    List<Barang> findByStockLessThan(int limit);
}
