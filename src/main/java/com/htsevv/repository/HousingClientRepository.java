package com.htsevv.repository;

import com.htsevv.entity.HousingClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HousingClientRepository extends JpaRepository<HousingClient, UUID> {
    HousingClient findByUserId(String userId);
}
