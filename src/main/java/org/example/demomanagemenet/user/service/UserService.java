package org.example.demomanagemenet.user.service;

import jakarta.persistence.criteria.Predicate;
import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.model.ValidationService;
import org.example.demomanagemenet.security.JwtUtil;
import org.example.demomanagemenet.user.entity.User;
import org.example.demomanagemenet.user.model.*;
import org.example.demomanagemenet.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        validationService.validate(registerRequest);
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()));
        user.setRole(registerRequest.getRole());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String Login(LoginRequest loginRequest) {
        validationService.validate(loginRequest);
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect username or password"));
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }
        return jwtUtil.generateToken(user.getUsername());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll(ListBarangRequest listBarangRequest) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(listBarangRequest.getPage(), listBarangRequest.getSize()));

        return userPage.getContent().stream()
                .map(this::toUserResponse)
                .toList();
    }


    @Transactional
    public void delete(String id){
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserResponse getById(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
        return toUserResponse(user);
    }

    @Transactional
    public void update(String id, UpdateUserRequest updateUserRequest){
        validationService.validate(updateUserRequest);
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND"));
        if(!BCrypt.checkpw(updateUserRequest.getOldPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }
        user.setUsername(updateUserRequest.getUsername());
        user.setPassword(BCrypt.hashpw(updateUserRequest.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);
    }

    @Transactional
    public Page<UserResponse> search(ListUserRequest listUserRequest, String keyword){
        Specification<User> specification = (root, query, builder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(keyword)){
                predicates.add(builder.equal(root.get("username"), keyword));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(listUserRequest.getPage(), listUserRequest.getSize());
        Page<User> userPage = userRepository.findAll(specification, pageable);
        List<UserResponse> userList = userPage.getContent().stream().map(this::toUserResponse).toList();
        return new PageImpl<>(userList, pageable, userPage.getTotalElements());
    }

    private UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole().toString());
        return userResponse;
    }
}
