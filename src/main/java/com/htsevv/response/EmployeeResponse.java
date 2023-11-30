package com.htsevv.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.CareGiverNotes;
import com.htsevv.entity.DriverLicense;
import com.htsevv.entity.PcaUmpiInfo;
import com.htsevv.entity.generic.AddressEntity;
import com.htsevv.entity.generic.MedicalTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private UUID id;
    private String firstName;

    private String lastName;
    private String userName;

    private String email;

    private Integer age;

    private String position;

    private String gender;

    private Date dateOfBirth;

    private String mobileNumber;

    private String password;
    private String confirmPassword;
    private AddressEntity address;
    private AddressEntity mailingAddress;
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
    private Boolean licenced;
    private List<PatientResponse> patients;
    private CareGiverNotes careGiverNotes;
    private List<PcaUmpiInfo> pcaUmpiInfoList;
    private String notes;
    private MedicalTest medicalTest;
    private DriverLicense driverLicense;
    private String role;
    private String profilePhoto;
    private String accountStatus;
}
