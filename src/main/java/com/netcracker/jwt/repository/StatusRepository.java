package com.netcracker.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.netcracker.jwt.domain.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
