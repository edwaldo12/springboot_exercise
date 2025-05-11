package com.example.demo.domain.user;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    List<User> findAll();
    void deleteById(UUID id);
    boolean existsByEmail(String email);
    boolean existsById(UUID id);
}