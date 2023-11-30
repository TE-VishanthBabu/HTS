package com.htsevv.repository;

import com.htsevv.entity.HousingStabilization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HousingStabilizationRepository extends JpaRepository<HousingStabilization, UUID> {
}
