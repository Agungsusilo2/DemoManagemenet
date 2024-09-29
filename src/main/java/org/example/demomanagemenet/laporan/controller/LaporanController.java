package org.example.demomanagemenet.laporan.controller;

import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.laporan.service.LaporanService;
import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.transaksi.entity.Transaksi;
import org.example.demomanagemenet.transaksi.model.Type;
import org.example.demomanagemenet.user.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/laporan")
public class LaporanController {

    @Autowired
    private LaporanService laporanService;

    @GetMapping("/stock_less")
    public WebResponse<List<Barang>> stockLess(@RequestParam(name = "limit") int limit) {
        List<Barang> stockLess = laporanService.getStockLess(limit);
        return WebResponse.<List<Barang>>builder().data(stockLess).build();
    }

    @GetMapping("/transaksi_by_date")
    public WebResponse<List<Transaksi>> transaksiByDateRange(@RequestParam(name = "start") Long start, @RequestParam(name = "end") Long end) {
        List<Transaksi> transaksiByDateRange = laporanService.getTransaksiByDateRange(start, end);
        return WebResponse.<List<Transaksi>>builder().data(transaksiByDateRange).build();
    }

    @GetMapping("/transaksi_by_type")
    public WebResponse<List<Transaksi>> transaksiByType(@RequestParam(name = "type") String type) {
        List<Transaksi> transaksiByType = laporanService.getTransaksiByType(Type.valueOf(type));
        return WebResponse.<List<Transaksi>>builder().data(transaksiByType).build();
    }

    @GetMapping("/suppliers")
    public WebResponse<List<Supplier>> suppliers() {
        List<Supplier> allSupplier = laporanService.getAllSupplier();
        return WebResponse.<List<Supplier>>builder().data(allSupplier).build();
    }

    @GetMapping("/transaksi_by_supplier")
    public WebResponse<List<Transaksi>> transaksiBySupplier(@RequestParam(name = "id") String supplierId) {
        List<Transaksi> transaksiBySupplierId = laporanService.getTransaksiBySupplierId(supplierId);
        return WebResponse.<List<Transaksi>>builder().data(transaksiBySupplierId).build();
    }
}
