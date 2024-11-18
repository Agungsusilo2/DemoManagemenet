package org.example.demomanagemenet.transaksi;

import org.example.demomanagemenet.transaksi.service.TransaksiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest
public class TransaksiServiceTest {
    @Autowired
    private TransaksiService transaksiService;

    @Test
    public  void testService(){
        Map<LocalDate, Long> localDateLongMap = transaksiService.countByDateWeek();
        localDateLongMap.forEach((k,v)->{
            System.out.println("Key : " + k + " Value : " + v);
        });
    }
}
