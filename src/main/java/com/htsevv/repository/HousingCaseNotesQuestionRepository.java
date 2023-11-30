package com.htsevv.repository;

import com.htsevv.entity.HousingCaseNotesQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HousingCaseNotesQuestionRepository extends JpaRepository<HousingCaseNotesQuestion, UUID> {
}
