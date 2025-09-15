package org.example.codecompasssolo.repository;

import org.example.codecompasssolo.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    ProfileEntity findByDisplayName(String username);

}

