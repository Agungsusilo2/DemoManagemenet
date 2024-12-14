package org.example.demomanagemenet.supplier;

import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.supplier.model.ListSupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierResponse;
import org.example.demomanagemenet.supplier.service.SupplierService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SpringBootTest
public class SupplierServiceTest {
    @Autowired
    private SupplierService supplierService;

    @Test
    public void testAddSupplier() {
        SupplierRequest request = new SupplierRequest();
        request.setName("test");
        request.setAddress("test");
        request.setContact("092323212");
        supplierService.store(request);
    }

    @Test
    public void testUpdateSupplier() {
        SupplierRequest request = new SupplierRequest();
        request.setName("test1");
        request.setAddress("test");
        request.setContact("092323212");
        supplierService.update(request,"e8734257-7748-48a7-b533-d614e7a69ef5");

        Supplier supplier = supplierService.get("e8734257-7748-48a7-b533-d614e7a69ef5");
        Assertions.assertEquals("test", supplier.getAddress());
        Assertions.assertEquals("092323212", supplier.getContact());
        Assertions.assertEquals("test1", supplier.getName());
    }

    @Test
    public void testDeleteSupplier() {
        supplierService.delete("e8734257-7748-48a7-b533-d614e7a69ef5");

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            Supplier supplier = supplierService.get("e8734257-7748-48a7-b533-d614e7a69ef5");
        });
    }

    @Test
    public void testGetSuppliers(){
        List<SupplierResponse> all = supplierService.getAll(new ListSupplierRequest(0, 10));
        Assertions.assertNotNull(all);
    }

    @Test
    public void testGetSupplier(){
        List<SupplierResponse> all = supplierService.getAll(new ListSupplierRequest(0, 10));;
        Page<SupplierResponse> search = supplierService.search(new ListSupplierRequest(0, 10), "test");
        Assertions.assertNotNull(search);
    }
}
