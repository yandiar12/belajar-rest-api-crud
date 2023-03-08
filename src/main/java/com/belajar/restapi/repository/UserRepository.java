package com.belajar.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.belajar.restapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findAllByNameContainingIgnoreCase(String name);
}
