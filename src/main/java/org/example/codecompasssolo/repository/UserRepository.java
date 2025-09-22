package org.example.codecompasssolo.repository;

import org.example.codecompasssolo.entity.ProfileEntity;
import org.example.codecompasssolo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailAndEncryptedPassword(String email, String encrypted_password);

}
