package com.example.yapdev.repository;

import com.example.yapdev.repository.dto.testDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface testRepository extends JpaRepository<testDto, Long> {
}
