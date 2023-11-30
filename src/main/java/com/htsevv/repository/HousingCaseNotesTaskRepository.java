package com.htsevv.repository;

import com.htsevv.entity.HousingCaseNotesTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HousingCaseNotesTaskRepository extends JpaRepository<HousingCaseNotesTask, UUID> {
}
