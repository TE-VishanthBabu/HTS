package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.BasicInfoEntity;
import com.htsevv.entity.generic.MedicalTest;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends BasicInfoEntity {

    public enum Role {
        OFFICE_ADMIN, CARE_GIVER, QP
    }

    private String careGiverUMPI;
    private Integer careGiverLevel;
    private String ssn;
    private double payRate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfHire;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date terminationDate;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String position;
    private Boolean licenced;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "caregivers_patients", joinColumns = @JoinColumn(name = "caregiver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    private List<Patient> patients;
    @OneToOne(cascade = CascadeType.ALL)
    private CareGiverNotes careGiverNotes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PcaUmpiInfo> pcaUmpiInfoList;

    private String notes;
    @OneToOne(cascade = CascadeType.ALL)
    private MedicalTest medicalTest;

    @OneToOne(cascade = CascadeType.ALL)
    private DriverLicense driverLicense;

    private Role role;
}

