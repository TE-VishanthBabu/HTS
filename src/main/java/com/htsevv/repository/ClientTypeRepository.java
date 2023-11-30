package com.htsevv.repository;

import com.htsevv.entity.ClientTypeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientTypeList, UUID> {
}
