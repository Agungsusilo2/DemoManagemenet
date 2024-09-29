package org.example.demomanagemenet.supplier.controller;

import org.example.demomanagemenet.barang.model.BarangResponse;
import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.supplier.model.ListSupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierResponse;
import org.example.demomanagemenet.supplier.service.SupplierService;
import org.example.demomanagemenet.user.model.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping(path = "/api/supplier",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> store(@RequestBody SupplierRequest supplierRequest){
        supplierService.store(supplierRequest);
        return WebResponse.<String>builder().data("OK").build();
    }

    @PutMapping(path = "/api/supplier/{supplierId}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> update(@RequestBody SupplierRequest supplierRequest,@PathVariable String supplierId){
        supplierService.update(supplierRequest,supplierId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/supplier/{supplierId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<Supplier> get(@PathVariable String supplierId){
        Supplier supplier = supplierService.get(supplierId);
        return WebResponse.<Supplier>builder().data(supplier).build();
    }

    @DeleteMapping(path = "/api/supplier/{supplierdId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(@PathVariable String supplierdId){
        supplierService.delete(supplierdId);
        return WebResponse.<String>builder().data("OK").build();
    }
    @GetMapping(path = "/api/supplier")
    public WebResponse<List<SupplierResponse>> getAll(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "15") int size){
        ListSupplierRequest listSupplierRequest = new ListSupplierRequest(page, size);
        List<SupplierResponse> all = supplierService.getAll(listSupplierRequest);

        return WebResponse.<List<SupplierResponse>>builder().data(all).build();
    }

    @GetMapping(path = "/api/supplier/search")
    public WebResponse<List<SupplierResponse>> search(@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value = "size",defaultValue = "15")int size,@RequestParam(name = "keyword") String keyword){
        ListSupplierRequest listSupplierRequest = new ListSupplierRequest(page, size);
        Page<SupplierResponse> search = supplierService.search(listSupplierRequest, keyword);
        return WebResponse.<List<SupplierResponse>>builder().data(search.getContent()).build();
    }
}
