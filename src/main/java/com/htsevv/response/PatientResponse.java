package com.htsevv.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.*;
import com.htsevv.entity.generic.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private UUID id;
    private String firstName;
    private String lastName;

    private Date dateOfBirth;

    private String mobileNumber;

    private String pmi;

    private String notes;

    private EmergencyContact emergencyContacts;

    private String gender;

    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
    private Integer age;
    private AddressEntity address;
    private AddressEntity mailingAddress;
    private String profilePhoto;
    private String accountStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date admissionDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dischargeDate;
    private String insuranceNumber;
    private String patientCode;
    private String medicalSpendDown;
    private Double amount;
    private String PcaChoice;
    private boolean patient245D;
    private boolean hasReponsibleParty;
    private CaseManager caseManager;
    private PcaSupervision pcaSupervision;
    private IAPP iapp;
    private PcaCarePlan pcaCarePlan;
    private List<Service> availableServices;
    private List<EmployeeResponse> caregivers = new ArrayList<>();
    private List<LocationResponse> locations;
    private String placeOfService;
    private CarePlanDue carePlanDue;
}
