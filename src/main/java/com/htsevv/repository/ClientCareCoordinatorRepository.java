package com.htsevv.repository;

import com.htsevv.entity.ClientCareCoordinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientCareCoordinatorRepository extends JpaRepository<ClientCareCoordinator, UUID> {
}
