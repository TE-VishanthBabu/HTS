package com.htsevv.repository;

import com.htsevv.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findByCreationDateBetween(Date startDate, Date endDate);

    Patient findByEmail(String email);
    Patient findByUserName(String userName);
}
