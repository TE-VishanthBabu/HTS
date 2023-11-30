package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.BasicInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient extends BasicInfoEntity {

    private String PMI;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date admissionDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dischargeDate;
    private String insuranceNumber;
    private String patientCode;
    private String notes;
    @OneToOne(cascade = CascadeType.ALL)
    private EmergencyContact emergencyContacts;
    private String medicalSpendDown;
    private Double amount;
    private String PcaChoice;
    private boolean patient245D;
    private boolean hasReponsibleParty;
    @OneToOne(cascade = CascadeType.ALL)
    private CaseManager caseManager;
    @OneToOne(cascade = CascadeType.ALL)
    private PcaSupervision pcaSupervision;
    @OneToOne(cascade = CascadeType.ALL)
    private IAPP iapp;

    @OneToOne(cascade = CascadeType.ALL)
    private PcaCarePlan pcaCarePlan;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Service> availableServices;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "caregivers_patients", joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "caregiver_id", referencedColumnName = "id"))
    private List<Employee> caregivers;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Location> locations;
    private String placeOfService;
    @OneToOne(cascade = CascadeType.ALL)
    private CarePlanDue carePlanDue;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Diagnoses> diagnoses;

}

