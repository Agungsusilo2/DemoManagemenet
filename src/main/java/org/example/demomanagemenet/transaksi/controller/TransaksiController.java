package org.example.demomanagemenet.transaksi.controller;


import org.example.demomanagemenet.transaksi.model.ListTransaksiRequest;
import org.example.demomanagemenet.transaksi.model.TransaksiRequest;
import org.example.demomanagemenet.transaksi.model.TransaksiResponse;
import org.example.demomanagemenet.transaksi.service.TransaksiService;
import org.example.demomanagemenet.user.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    @Autowired
    private TransaksiService transaksiService;

    @PostMapping(path = "/in", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> storeIn(@RequestBody TransaksiRequest transaksiRequest) {
        transaksiService.storeIn(transaksiRequest);
        return WebResponse.<String>builder().data("Transaksi Barang Masuk Berhasil").build();
    }

    @PostMapping(path = "/out", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> storeOut(@RequestBody TransaksiRequest transaksiRequest) {
        transaksiService.storeOut(transaksiRequest);
        return WebResponse.<String>builder().data("Transaksi Barang Keluar Berhasil").build();
    }

    @PutMapping(path = "/{transaksiId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> update(@RequestBody TransaksiRequest transaksiRequest, @PathVariable(name = "transaksiId") String transaksiId) {
        transaksiService.update(transaksiRequest, transaksiId);
        return WebResponse.<String>builder().data("Transaksi Berhasil Diperbarui").build();
    }

    @DeleteMapping(path = "/{transaksiId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(@PathVariable(name = "transaksiId") String transaksiId) {
        transaksiService.delete(transaksiId);
        return WebResponse.<String>builder().data("Transaksi Berhasil Dihapus").build();
    }

    @GetMapping(path = "/{transaksiId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TransaksiResponse> get(@PathVariable(name = "transaksiId") String transaksiId) {
        TransaksiResponse transaksiResponse = transaksiService.get(transaksiId);
        return WebResponse.<TransaksiResponse>builder().data(transaksiResponse).build();
    }

    @GetMapping
    public WebResponse<List<TransaksiResponse>> getAll(@RequestParam(name = "page",defaultValue = "0") int page,@RequestParam(name = "size",defaultValue = "15") int size) {
        List<TransaksiResponse> transaksiResponses = transaksiService.getAll(new ListTransaksiRequest(page, size));
        return WebResponse.<List<TransaksiResponse>>builder().data(transaksiResponses).build();
    }
//    @GetMapping("/graph")
//    public WebResponse<Map<LocalDate, Long>> getGraph() {
//        Map<LocalDate, Long> localDateLongMap = transaksiService.countByDateWeek();
//        return WebResponse.<Map<LocalDate, Long>>builder().data(localDateLongMap).build();
//    }

    @GetMapping("/search")
    public WebResponse<List<TransaksiResponse>> search(@RequestParam(name = "keyword") String keyword,@RequestParam(name = "page",defaultValue = "0")int page,@RequestParam(name = "size",defaultValue = "15")int size) {
        Page<TransaksiResponse> searched = transaksiService.search(new ListTransaksiRequest(page, size), keyword);
        return WebResponse.<List<TransaksiResponse>>builder().data(searched.getContent()).build();
    }

    @GetMapping("/count")
    public WebResponse<Long> count() {
        Long count = transaksiService.countByDate();
        return WebResponse.<Long>builder().data(count).build();
    }
    @GetMapping("/top3-recent")
    public List<TransaksiResponse> getTop3RecentTransaksi() {
        return transaksiService.getTop3RecentTransaksi();
    }

    @GetMapping("/graph")
    public WebResponse<Map<Integer,Integer>> graph() {
        Map<Integer, Integer> countTotalInWeek = transaksiService.getCountTotalInWeek();
        return WebResponse.<Map<Integer,Integer>>builder()
                .data(countTotalInWeek).build();
    }
}
