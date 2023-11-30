package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.*;
import com.htsevv.entity.generic.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    @NotEmpty(message = "First name should be required")
    @NotNull(message = "First name should not be null")
    private String firstName;

    @NotEmpty(message = "Last Name should be required")
    @NotNull(message = "Last name should not be null")
    private String lastName;

    @NotNull(message = "Date of birth should be required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotEmpty(message = "Mobile Number should be required")
    private String mobileNumber;

    @NotEmpty(message = "PMI should be required")
    @NotNull(message = "PMI should not be null")
    private String pmi;

    private String notes;

    private EmergencyContact emergencyContacts;

    @NotEmpty(message = "Gender is required")
    @NotNull(message = "Gender should not be null")
    private String gender;

    private String username;
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
    private List<UUID> caregivers = new ArrayList<>();
    private List<UUID> locations;
    private String placeOfService;
    private CarePlanDue carePlanDue;

}
