package org.example.demomanagemenet.laporan.service;

import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.barang.repository.BarangRepository;
import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.supplier.repository.SupplierRepository;
import org.example.demomanagemenet.transaksi.entity.Transaksi;
import org.example.demomanagemenet.transaksi.model.Type;
import org.example.demomanagemenet.transaksi.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class LaporanService {
    @Autowired
    private BarangRepository barangRepository;
    @Autowired
    private TransaksiRepository transaksiRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    public List<Barang> getStockLess(Integer limit){
       if(Objects.isNull(limit)){
           return barangRepository.findAll();
       }
       return barangRepository.findByStockLessThan(limit);
    }

    public  List<Transaksi> getTransaksiByDateRange(Long start, Long end){
        if(Objects.isNull(start) || Objects.isNull(end)){
            return null;
        }
        return transaksiRepository.findByDateBetween(start, end);
    }
    public List<Transaksi> getTransaksiByType(Type type){
        if(Objects.isNull(type)){
            return null;
        }
        return transaksiRepository.findByType(type);
    }
    public List<Supplier> getAllSupplier(){
        return supplierRepository.findAll();
    }

    public List<Transaksi> getTransaksiBySupplierId(String supplierId){
        if(Objects.isNull(supplierId)){
            return null;
        }
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
        return transaksiRepository.findBySupplier(supplier);
    }
}
