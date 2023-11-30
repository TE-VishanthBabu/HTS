package com.htsevv.repository;

import com.htsevv.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findByCreationDateBetween(Date startDate, Date endDate);

    Employee findByEmail(String email);

    Employee findByUserName(String userName);
}
