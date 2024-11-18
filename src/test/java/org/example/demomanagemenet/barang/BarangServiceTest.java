package org.example.demomanagemenet.barang;

import jakarta.validation.ValidationException;
import org.example.demomanagemenet.barang.model.BarangRequest;
import org.example.demomanagemenet.barang.model.BarangResponse;
import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.barang.service.BarangService;
import org.example.demomanagemenet.transaksi.service.TransaksiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@SpringBootTest
public class BarangServiceTest {

    @Autowired
    private BarangService barangService;

    @Autowired
    private TransaksiService transaksiService;

    @Test
    public void testAddBarang() {
        BarangRequest barangRequest = new BarangRequest();
        barangRequest.setName("Gula");
        barangRequest.setCategory("Bahan dapur");
        barangRequest.setStock(200);
        barangRequest.setPrice(20_000);
        barangRequest.setDescription("Gula 200ML");

        barangService.add(barangRequest);

    }


    @Test
    public void testAddBarang_ValidationError() {
        BarangRequest barangRequest = new BarangRequest();
        barangRequest.setName("");
        barangRequest.setCategory("Bahan dapur");
        barangRequest.setStock(200);
        barangRequest.setPrice(20_000);
        barangRequest.setDescription("Gula 200ML");


     Assertions.assertThrows(ValidationException.class, () -> {
            barangService.add(barangRequest);
        });
    }

    @Test
    public void testGetBarangError() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            barangService.get("2424");
        });
    }

    @Test
    public void testBarangDeleteError() {
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            barangService.delete("2424");
        });
    }

    @Test
    public void testBarangUpdateError() {
        BarangRequest barangRequest = new BarangRequest();
        barangRequest.setName("Gula");
        barangRequest.setCategory("Bahan dapur");
        barangRequest.setStock(200);
        barangRequest.setPrice(20_000);
        barangRequest.setDescription("Gula 200ML");


        Assertions.assertThrows(ResponseStatusException.class, () -> {
            barangService.update("salah", barangRequest);
        });
    }


    @Test
    public void tetBarangSearch() {
        List<BarangResponse> gula = barangService.search("Gula", new ListBarangRequest(0, 10)).getContent();
        gula.forEach(System.out::println);
    }

    @Test
    public void testBarangSearchError() {
        List<BarangResponse> gula = barangService.search("", new ListBarangRequest(0, 10)).getContent();
        gula.forEach(System.out::println);
    }

    @Test
    public void testGetAllBarang(){
        List<BarangResponse> all = barangService.getAll(new ListBarangRequest(0, 10));
        all.forEach(System.out::println);
    }

}
