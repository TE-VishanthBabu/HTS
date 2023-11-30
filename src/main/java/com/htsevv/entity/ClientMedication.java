package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ClientMedication extends UUIDEntity {
    private Boolean status;
    private String medication;
    private String rxNumber;
    private String appearance;
    private String howMany;
    private String refills;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String howTaken;
    private String frequency;
    private String reason;
    private String notes;
    private String physicianNpi;
    private String prescribedBy;
}
