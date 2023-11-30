package com.htsevv.repository;

import com.htsevv.entity.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, UUID> {
    List<TimeSheet> findAllByServiceIdAndClientIdEqualsAndEmployeeIdEqualsAndTimeSheetDateBetween(String serviceId,
                                                                                                   String patientId,
                                                                                                   String employeeId,
                                                                                                   Date startDate,
                                                                                                   Date endDate);

    @Query(value = "SELECT SUM(total_hours) FROM time_sheet t WHERE t.employee_id=:employeeId AND t.client_id=:clientId",nativeQuery = true)
    Double findTotalHoursByEmployeeId(String employeeId,String clientId);
}
