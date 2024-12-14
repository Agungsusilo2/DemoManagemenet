package org.example.demomanagemenet.transaksi.service;

import jakarta.persistence.criteria.Predicate;
import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.barang.repository.BarangRepository;
import org.example.demomanagemenet.model.ValidationService;
import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.supplier.repository.SupplierRepository;
import org.example.demomanagemenet.transaksi.entity.Transaksi;
import org.example.demomanagemenet.transaksi.model.ListTransaksiRequest;
import org.example.demomanagemenet.transaksi.model.TransaksiRequest;
import org.example.demomanagemenet.transaksi.model.TransaksiResponse;
import org.example.demomanagemenet.transaksi.model.Type;
import org.example.demomanagemenet.transaksi.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.*;

@Service
public class TransaksiService {
    @Autowired
    private TransaksiRepository transaksiRepository;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private BarangRepository barangRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    public void storeIn(TransaksiRequest transaksiRequest) {
        try {
            validationService.validate(transaksiRequest);
            System.out.println("Validation passed.");

            Transaksi transaksi = new Transaksi();
            transaksi.setId(UUID.randomUUID().toString());
            transaksi.setDate(transaksiRequest.getDate());
            transaksi.setType(transaksiRequest.getType());
            transaksi.setTotal(transaksiRequest.getTotal());

            String barangId = transaksiRequest.getBarangId();
            Barang barang = barangRepository.findById(barangId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barang Not Found"));

            barang.setStock(barang.getStock() + transaksiRequest.getTotal());
            barangRepository.save(barang);
            transaksi.setBarang(barang);

            if (transaksiRequest.getSupplierId() != null) {
                Supplier supplier = supplierRepository.findById(transaksiRequest.getSupplierId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier Not Found"));
                transaksi.setSupplier(supplier);
            }

            transaksiRepository.save(transaksi);
            System.out.println("Transaksi saved successfully.");

        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while processing the transaction", e);
        }
    }

    @Transactional
    public void storeOut(TransaksiRequest transaksiRequest) {
        validationService.validate(transaksiRequest);

        Transaksi transaksi = new Transaksi();
        transaksi.setId(UUID.randomUUID().toString());
        transaksi.setDate(transaksiRequest.getDate());
        transaksi.setType(transaksiRequest.getType());
        transaksi.setTotal(transaksiRequest.getTotal());

        String barangId = transaksiRequest.getBarangId();
        Barang barang = barangRepository.findById(barangId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barang Not Found"));

        if (barang.getStock() < transaksiRequest.getTotal()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stok tidak mencukupi");
        }

        barang.setStock(barang.getStock() - transaksiRequest.getTotal());
        barangRepository.save(barang);

        transaksi.setBarang(barang);
        transaksiRepository.save(transaksi);
    }

    @Transactional
    public void update(TransaksiRequest transaksiRequest, String id) {
        validationService.validate(transaksiRequest);

        Transaksi transaksi = transaksiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaksi not found"));

        transaksi.setDate(transaksiRequest.getDate());
        transaksi.setType(transaksiRequest.getType());
        transaksi.setTotal(transaksiRequest.getTotal());

        transaksiRepository.save(transaksi);
    }

    @Transactional
    public void delete(String id) {
        Transaksi transaksi = transaksiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaksi not found"));

        Barang barang = transaksi.getBarang();
        Type transaksiType = transaksi.getType();
        if (transaksiType.equals("masuk")) {
            barang.setStock(barang.getStock() - transaksi.getTotal());
        } else if (transaksiType.equals("keluar")) {
            barang.setStock(barang.getStock() + transaksi.getTotal());
        }
        barangRepository.save(barang);

        transaksiRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TransaksiResponse get(String id) {
        Transaksi transaksi = transaksiRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaksi not found"));
        return toResponseJson(transaksi);
    }

    @Transactional(readOnly = true)
    public List<TransaksiResponse> getAll(ListTransaksiRequest listTransaksiRequest) {
        Page<Transaksi> all = transaksiRepository.findAll(PageRequest.of(listTransaksiRequest.getPage(), listTransaksiRequest.getSize()));
        return all.getContent().stream().map(this::toResponseJson).toList();
    }

    @Transactional(readOnly = true)
    public Page<TransaksiResponse> search(ListTransaksiRequest listTransaksiRequest,String keyword) {
        Specification<Transaksi> specification = ((root, query, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(keyword) && !keyword.isEmpty()) {
                Predicate barangNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("barang").get("name")), "%" + keyword.toLowerCase() + "%");
                Predicate supplierNamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("supplier").get("name")), "%" + keyword.toLowerCase() + "%");
                Predicate typePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("type")), "%" + keyword.toLowerCase() + "%");

                predicates.add(criteriaBuilder.or(barangNamePredicate, supplierNamePredicate, typePredicate));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
        Pageable pageable = PageRequest.of(listTransaksiRequest.getPage(), listTransaksiRequest.getSize());
        Page<Transaksi> all = transaksiRepository.findAll(specification, pageable);
        List<TransaksiResponse> transaksiResponses = all.getContent().stream().map(this::toResponseJson).toList();

        return new PageImpl<>(transaksiResponses, pageable, all.getTotalElements());
    }
    @Transactional(readOnly = true)
    public Long countByDate() {

        long startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        long endOfDay = LocalDate.now().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toEpochSecond();

        Long count = transaksiRepository.findByDateBetween(startOfDay, endOfDay).stream()
                .count();

        return count;
    }

    @Transactional(readOnly = true)
    public Map<Integer, Integer> getCountTotalInWeek() {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            long startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
            long endOfDay = date.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toEpochSecond();
            long count = transaksiRepository.findByDateBetween(startOfDay, endOfDay).stream().count();
            countMap.put(i, Math.toIntExact(count)); 
        }

        return countMap;
    }

    @Transactional(readOnly = true)
    public List<TransaksiResponse> getTop3RecentTransaksi() {
        List<Transaksi> transaksiList = transaksiRepository.findTop3ByOrderByDateDesc();

        return transaksiList.stream().map(this::toResponseJson).toList();
    }



    private TransaksiResponse toResponseJson(Transaksi transaksi) {
        TransaksiResponse response = new TransaksiResponse();
        response.setId(transaksi.getId());
        response.setDate(transaksi.getDate());
        response.setType(transaksi.getType());
        response.setTotal(transaksi.getTotal());
        response.setBarang(transaksi.getBarang());
        response.setSupplier(transaksi.getSupplier());
        return response;
    }
}
