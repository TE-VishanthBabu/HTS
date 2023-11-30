package com.htsevv.repository;

import com.htsevv.entity.ClientPharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientPharmacyRepository extends JpaRepository<ClientPharmacy, UUID> {
}
