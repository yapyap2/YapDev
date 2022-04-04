package com.example.yapdev.repository;

import com.example.yapdev.repository.dto.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface userRepository extends JpaRepository<user, Long> {

    user findByUserId(String userId);

    user findByName(String name);
}
