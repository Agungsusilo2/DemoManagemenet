package org.example.demomanagemenet.barang.controller;

import org.example.demomanagemenet.barang.model.BarangRequest;
import org.example.demomanagemenet.barang.model.BarangResponse;
import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.barang.service.BarangService;
import org.example.demomanagemenet.user.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BarangController {
    @Autowired
    BarangService barangService;

    @PostMapping(path = "/api/barang")
    public WebResponse<String> store(@RequestBody BarangRequest barangRequest){
        barangService.add(barangRequest);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/barang/{barangId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<BarangResponse> get(@PathVariable("barangId") String barangId){
        BarangResponse barangResponse = barangService.get(barangId);
        return WebResponse.<BarangResponse>builder().data(barangResponse).build();
    }

    @DeleteMapping(path = "/api/barang/{barangId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(@PathVariable("barangId") String barangId){
        barangService.delete(barangId);
        return WebResponse.<String>builder().data("OK").build();
    }


    @PutMapping(path = "/api/barang/{barangId}",produces =MediaType.APPLICATION_JSON_VALUE ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> update(@PathVariable("barangId") String barangId,@RequestBody BarangRequest barangRequest){
        barangService.update(barangId,barangRequest);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/barang")
    public WebResponse<List<BarangResponse>> getAll(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "15") int size){
        ListBarangRequest listBarangRequest = new ListBarangRequest(page, size);
        List<BarangResponse> all = barangService.getAll(listBarangRequest);

        return WebResponse.<List<BarangResponse>>builder().data(all).build();
    }

    @GetMapping(path = "/api/barang/count")
    public WebResponse<Long> getCount(){
        return WebResponse.<Long>builder().data(barangService.getCount()).build();
    }

    @GetMapping(path = "/api/barang/search")
    public WebResponse<List<BarangResponse>> search(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size) {

        ListBarangRequest listBarangRequest = new ListBarangRequest(page, size);

        Page<BarangResponse> search = barangService.search(keyword, listBarangRequest);

        return WebResponse.<List<BarangResponse>>builder()
                .data(search.getContent())
                .build();
    }

    @GetMapping("/api/barang/top3-stock-ordered")
    public List<BarangResponse> getTop3BarangOrderedByStock() {
        return barangService.getBarangStockLessDate();
    }
}

