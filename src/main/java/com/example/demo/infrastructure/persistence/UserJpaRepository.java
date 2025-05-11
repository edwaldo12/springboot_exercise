package com.example.demo.infrastructure.persistence;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class UserJpaRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        UserEntity userEntity = UserEntity.fromDomain(user);
        if (userEntity.getId() == null) {
            entityManager.persist(userEntity);
        } else {
            entityManager.merge(userEntity);
        }
        return userEntity.toDomain();
    }

    @Override
    public Optional<User> findById(UUID id) {
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        return Optional.ofNullable(userEntity).map(UserEntity::toDomain);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class)
                .getResultList()
                .stream()
                .map(UserEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        UserEntity userEntity = (UserEntity) findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        entityManager.remove(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email";
        Long count = (Long) entityManager.createQuery(query)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean existsById(UUID id) {
        String query = "SELECT COUNT(u) FROM UserEntity u WHERE u.id = :id";
        Long count = (Long) entityManager.createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }
}
