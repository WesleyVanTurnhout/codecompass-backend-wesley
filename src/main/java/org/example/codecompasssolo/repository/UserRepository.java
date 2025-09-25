package org.example.codecompasssolo.repository;

import org.example.codecompasssolo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findById(UUID id);
}
