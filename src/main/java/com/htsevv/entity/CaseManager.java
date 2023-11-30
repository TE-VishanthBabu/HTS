package com.htsevv.entity;

import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseManager extends UUIDEntity {
    private String physicianName;
    private String clinicName;
    private String phoneNumber;
    private String faxNumber;
    private String physicianAddress;
    private String physicianCity;
    private String physicianProvince;
    private String physicianZip;
    private String caseManagerName;
    private String caseManagerEmail;
    private String caseManagerExtension;
    private String caseManagerPhoneNumber;
    private String caseManagerFaxNumber;
    private String caseManagerNotes;
}
