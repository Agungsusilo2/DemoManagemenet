package org.example.demomanagemenet.barang.service;

import jakarta.persistence.criteria.Predicate;
import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.barang.model.BarangRequest;
import org.example.demomanagemenet.barang.model.BarangResponse;
import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.barang.repository.BarangRepository;
import org.example.demomanagemenet.model.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BarangService {
    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void add(BarangRequest barangRequest){
        validationService.validate(barangRequest);

        Barang barang = new Barang();
        barang.setId(UUID.randomUUID().toString());
        barang.setName(barangRequest.getName());
        barang.setCategory(barangRequest.getCategory());
        barang.setPrice(barangRequest.getPrice());
        barang.setStock(barangRequest.getStock());
        barang.setDescription(barangRequest.getDescription());
        barangRepository.save(barang);
    }

    @Transactional(readOnly = true)
    public List<BarangResponse> getStockBarang(){
        List<Barang> allById = barangRepository.findAll();
        return allById.stream().map(this::toResponseJson).toList();
    }


    @Transactional(readOnly = true)
    public BarangResponse get(String id){
        Barang barang = barangRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barang tidak ditemukan"));
        return toResponseJson(barang);

    }

    @Transactional
    public void delete(String id){
        barangRepository.deleteById(id);
    }

    @Transactional
    public void update(String id, BarangRequest barangRequest) {
        validationService.validate(barangRequest);

        Barang barang = barangRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barang tidak ditemukan"));

        barang.setName(barangRequest.getName());
        barang.setCategory(barangRequest.getCategory());
        barang.setPrice(barangRequest.getPrice());
        barang.setStock(barangRequest.getStock());
        barang.setDescription(barangRequest.getDescription());

        barangRepository.save(barang);
    }
    @Transactional(readOnly = true)
    public Page<BarangResponse> search(String keyword,ListBarangRequest listBarangRequest){
        Specification<Barang> specification = (root, criteriaQuery, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(keyword)){
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%"+keyword+"%")));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(listBarangRequest.getPage(), listBarangRequest.getSize());
        Page<Barang> barangs = barangRepository.findAll(specification, pageable);
        List<BarangResponse> barangResponses = barangs.getContent().stream().map(this::toResponseJson).toList();

        return new PageImpl<>(barangResponses,pageable,barangs.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<BarangResponse> getAll(ListBarangRequest listBarangRequest){
        Page<Barang> all = barangRepository.findAll(PageRequest.of(listBarangRequest.getPage(), listBarangRequest.getSize()));
        List<Barang> barangList = all.get().toList();
        return barangList.stream().map(this::toResponseJson).toList();
    }

    @Transactional(readOnly = true)
    public Long getCount(){
        return barangRepository.count();
    }


    @Transactional(readOnly = true)
    public List<BarangResponse> getBarangStockLessDate(){
        List<Barang> allByOrderByStockAsc = barangRepository.findTop3ByOrderByStockAsc();
        return allByOrderByStockAsc.stream().map(this::toResponseJson).toList();
    }

    private BarangResponse toResponseJson(Barang barang){
        BarangResponse barangResponse = new BarangResponse();
        barangResponse.setId(barang.getId());
        barangResponse.setName(barang.getName());
        barangResponse.setCategory(barang.getCategory());
        barangResponse.setPrice(barang.getPrice());
        barangResponse.setStock(barang.getStock());
        barangResponse.setDescription(barang.getDescription());
        return barangResponse;
    }
}
