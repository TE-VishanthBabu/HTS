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
public class ClientCertification extends UUIDEntity {
    private String startCare;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String companyName;
    private String icd10;
    private String physicianNpi;
    private String physicianName;
    private String physicianAddress;
    private String physicianPhone;
    private String clinic;
    private String firstDigitFamilyCode;
    private String secondDigitFamilyCode;
    private String frequencyCode;
    private String admissionPriority;
    private String visit;
    private String dischargeStatus;
}
