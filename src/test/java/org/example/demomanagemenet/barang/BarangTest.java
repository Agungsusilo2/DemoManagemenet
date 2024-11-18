package org.example.demomanagemenet.barang;

import org.example.demomanagemenet.barang.entity.Barang;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class BarangTest {
    @Test
    public void testGetSetNama() {
        Barang barang = new Barang();
        barang.setName("Gula");
        Assertions.assertEquals("Gula", barang.getName());
    }

    @Test
    public void testGetSetStok() {
        Barang barang = new Barang();
        barang.setStock(100);
        Assertions.assertEquals(100, barang.getStock());
    }

    @Test
    public void testGetSetDescription() {
        Barang barang = new Barang();
        barang.setDescription("Barang ini mengandung banyak gula, agar diperhatikan");
        Assertions.assertEquals("Barang ini mengandung banyak gula, agar diperhatikan", barang.getDescription());
    }

    @Test
    public void testGetSetCategory() {
        Barang barang = new Barang();
        barang.setCategory("Minuman");
        Assertions.assertEquals("Minuman", barang.getCategory());
    }

    @Test
    public void testGetSetPrice() {
        Barang barang = new Barang();
        barang.setPrice(200000);
        Assertions.assertEquals(200000, barang.getPrice());
    }

    @Test
    public void testGetSetCreated() {
        Barang barang = new Barang();
        barang.setCreated(LocalDateTime.now());
        Assertions.assertEquals(LocalDateTime.now(), barang.getCreated());
    }

    @Test
    public void testCountTransactionDate() {
        LocalDate now = LocalDate.now();
        long[] dates = new long[7];
        for (int i = 0; i < 7; i++) {
            dates[i] = now.minusDays(i).atStartOfDay(ZoneOffset.UTC).toEpochSecond();
            System.out.println(dates[i]);
        }
    }

}
