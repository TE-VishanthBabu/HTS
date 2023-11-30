package com.htsevv.entity;

import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarePlanDue extends UUIDEntity {
    private Date lastCompleted;
    private Date dueDate;
    private String qpAssigned;
    private String notes;
}
