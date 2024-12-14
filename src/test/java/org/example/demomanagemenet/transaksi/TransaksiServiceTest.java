package org.example.demomanagemenet.transaksi;

import org.example.demomanagemenet.barang.model.BarangResponse;
import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.barang.service.BarangService;
import org.example.demomanagemenet.supplier.model.ListSupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierResponse;
import org.example.demomanagemenet.supplier.service.SupplierService;
import org.example.demomanagemenet.transaksi.entity.Transaksi;
import org.example.demomanagemenet.transaksi.model.ListTransaksiRequest;
import org.example.demomanagemenet.transaksi.model.TransaksiRequest;
import org.example.demomanagemenet.transaksi.model.TransaksiResponse;
import org.example.demomanagemenet.transaksi.model.Type;
import org.example.demomanagemenet.transaksi.service.TransaksiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TransaksiServiceTest {
    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private SupplierService supplierService;



    @Test
    public void testAddTransaksi() {
        List<BarangResponse> all = barangService.getAll(new ListBarangRequest(0, 10));
        String barangId = null;
        barangId = all.getFirst().getId();
        List<SupplierResponse> all1 = supplierService.getAll(new ListSupplierRequest(0, 10));
        String supplierId = null;
        supplierId = all1.getFirst().getId();
        TransaksiRequest transaksiRequest = new TransaksiRequest();
        transaksiRequest.setBarangId(barangId);
        transaksiRequest.setSupplierId(supplierId);
        transaksiRequest.setDate(new Date().getTime());
        transaksiRequest.setTotal(4);
        transaksiRequest.setType(Type.masuk);
        transaksiService.storeIn(transaksiRequest);
    }

    @Test
    public void testStoreOut() {
        List<BarangResponse> all = barangService.getAll(new ListBarangRequest(0, 10));
        String barangId = null;
        barangId = all.getFirst().getId();
        List<SupplierResponse> all1 = supplierService.getAll(new ListSupplierRequest(0, 10));
        String supplierId = null;
        supplierId = all1.getFirst().getId();
        TransaksiRequest transaksiRequest = new TransaksiRequest();
        transaksiRequest.setBarangId(barangId);
        transaksiRequest.setSupplierId(supplierId);
        transaksiRequest.setDate(new Date().getTime());
        transaksiRequest.setTotal(2);
        transaksiRequest.setType(Type.keluar);
        transaksiService.storeOut(transaksiRequest);
    }


    @Test
    public void testGetTransaksi() {
        List<TransaksiResponse> transaksiResponses = transaksiService.getAll(new ListTransaksiRequest(0, 10));
        Assertions.assertNotNull(transaksiResponses);
    }

    @Test
    public void testGetTransaksiById() {

        List<TransaksiResponse> transaksiResponses = transaksiService.getAll(new ListTransaksiRequest(0, 10));
        String transaksiId = transaksiResponses.get(0).getId();
        TransaksiResponse transaksiResponse = transaksiService.get(transaksiId);
        Assertions.assertNotNull(transaksiResponse);
    }

    @Test
    public void testCount(){
        List<TransaksiResponse> top3RecentTransaksi = transaksiService.getTop3RecentTransaksi();
        Assertions.assertEquals(3, top3RecentTransaksi.size());
    }

    @Test
    public void testCountByDate() {
        Long count = transaksiService.countByDate();
        Assertions.assertNotNull(count);
        System.out.println(count);
    }

    @Test
    public void testSearch() {
        long count = transaksiService.search(new ListTransaksiRequest(0, 10), "ultra").getContent().stream().count();

    }

    @Test
    public void testCountWeek(){
        Map<Integer, Integer> countTotalInWeek = transaksiService.getCountTotalInWeek();
        countTotalInWeek.forEach((k,v)->{
            System.out.println(k + "\t" + v);
        });
    }
}
