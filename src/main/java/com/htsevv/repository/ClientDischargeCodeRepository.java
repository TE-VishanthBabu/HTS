package com.htsevv.repository;

import com.htsevv.entity.ClientDischargeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientDischargeCodeRepository extends JpaRepository<ClientDischargeCode, UUID> {
}
