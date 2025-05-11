package com.example.demo.infrastructure.persistence;
import com.example.demo.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "app_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends User {
    @Id
    private UUID id;
    private String name;
    private String email;


    public User toDomain() {
        return new User(id, name, email);
    }

    public static UserEntity fromDomain(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail());
    }
}
