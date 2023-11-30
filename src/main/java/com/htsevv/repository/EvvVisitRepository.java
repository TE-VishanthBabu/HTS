package com.htsevv.repository;

import com.htsevv.entity.EvvVisit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface EvvVisitRepository extends JpaRepository<EvvVisit, UUID> {
    List<EvvVisit> findByStatus(EvvVisit.Status valueOf);

    List<EvvVisit> findByStatusAndLastModificationDateBetween(EvvVisit.Status valueOf, Date startDate, Date endDate);

    @Query(nativeQuery = true, value = "SELECT service_name, count(service_name) as count from evv_visit where status='ACCEPTED' " +
            "and last_modification_date BETWEEN :startDate and :endDate GROUP BY service_name ORDER BY count DESC LIMIT 6")
    List<Map<String, String>> getTopRequestedServices(Date startDate, Date endDate);

    List<EvvVisit> findByDeleted(boolean deleted);
}
