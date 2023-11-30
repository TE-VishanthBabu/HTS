package com.htsevv.repository;

import com.htsevv.entity.ClientCaseManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientCaseManagerRepository extends JpaRepository<ClientCaseManager, UUID> {
}
