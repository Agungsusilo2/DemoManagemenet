package org.example.demomanagemenet.supplier.service;

import jakarta.persistence.criteria.Predicate;
import org.example.demomanagemenet.barang.entity.Barang;
import org.example.demomanagemenet.barang.model.BarangResponse;
import org.example.demomanagemenet.model.ValidationService;
import org.example.demomanagemenet.supplier.entity.Supplier;
import org.example.demomanagemenet.supplier.model.ListSupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierRequest;
import org.example.demomanagemenet.supplier.model.SupplierResponse;
import org.example.demomanagemenet.supplier.repository.SupplierRepository;
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
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void store(SupplierRequest supplierRequest) {
        Supplier supplier = new Supplier();
        validationService.validate(supplierRequest);
        supplier.setId(UUID.randomUUID().toString());
        supplier.setName(supplierRequest.getName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setContact(supplierRequest.getContact());
        supplierRepository.save(supplier);
    }

    @Transactional
    public void update(SupplierRequest supplierRequest,String id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
        validationService.validate(supplierRequest);
        supplier.setName(supplierRequest.getName());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setContact(supplierRequest.getContact());
        supplierRepository.save(supplier);
    }

    @Transactional
    public void delete(String id){
        supplierRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Supplier get(String id){
        return supplierRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> getAll(ListSupplierRequest listSupplierRequest){
        Page<Supplier> suppliers = supplierRepository.findAll(PageRequest.of(listSupplierRequest.getPage(), listSupplierRequest.getSize()));
        List<Supplier> supplierList = suppliers.getContent().stream().toList();
        return supplierList.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Page<SupplierResponse> search(ListSupplierRequest listSupplierRequest, String keyword){
        Specification<Supplier> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(keyword)){
                predicates.add(cb.or(cb.like(root.get("name"), "%" + keyword + "%")));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        PageRequest pageRequest = PageRequest.of(listSupplierRequest.getPage(), listSupplierRequest.getSize());
        Page<Supplier> suppliers = supplierRepository.findAll(specification, pageRequest);
        List<SupplierResponse> supplierList = suppliers.getContent().stream().map(this::toResponse).toList();
        return new PageImpl<>(supplierList, pageRequest, suppliers.getTotalElements());
    }

    private SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse response = new SupplierResponse();
        response.setId(supplier.getId());
        response.setName(supplier.getName());
        response.setContact(supplier.getContact());
        response.setAddress(supplier.getAddress());
        return  response;
    }
}
