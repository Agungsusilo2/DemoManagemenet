package org.example.demomanagemenet.barang;

import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.barang.repository.BarangRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BarangTest {

    @Autowired
    private BarangRepository barangRepository;

    private Barang barang;

    @BeforeEach
    public void setUp() {
        barang = new Barang();
        barang.setId("123");
        barang.setName("Barang Test");
        barang.setCategory("Elektronik");
        barang.setPrice(500000);
        barang.setStock(100);
        barang.setDescription("Barang untuk pengujian");
    }

    @Test
    public void testSaveBarang() {
        Barang savedBarang = barangRepository.save(barang);

        assertNotNull(savedBarang.getId());
        assertEquals(barang.getName(), savedBarang.getName());
        assertEquals(barang.getCategory(), savedBarang.getCategory());
    }

    @Test
    public void testFindBarangById() {
        barangRepository.save(barang);

        Optional<Barang> foundBarang = barangRepository.findById("123");

        assertTrue(foundBarang.isPresent());
        assertEquals(barang.getName(), foundBarang.get().getName());
        assertEquals(barang.getPrice(), foundBarang.get().getPrice());
    }
}
