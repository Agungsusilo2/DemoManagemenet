package org.example.demomanagemenet.supplier;

import org.example.demomanagemenet.supplier.entity.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupplierTest {
    @Test
    public void testGetSetNama() {
        Supplier supplier = new Supplier();
        supplier.setName("Supplier A");
        Assertions.assertEquals("Supplier A", supplier.getName());
    }

    @Test
    public void testGetSetKontak() {
        Supplier supplier = new Supplier();
        supplier.setContact("08123456789");
        Assertions.assertEquals("08123456789", supplier.getContact());
    }
}
