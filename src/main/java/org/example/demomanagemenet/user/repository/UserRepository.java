package org.example.demomanagemenet.user.repository;

import org.example.demomanagemenet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> , JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
}
