package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.CareGiverNotes;
import com.htsevv.entity.DriverLicense;
import com.htsevv.entity.PcaUmpiInfo;
import com.htsevv.entity.generic.AddressEntity;
import com.htsevv.entity.generic.MedicalTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    @NotNull(message = "Firstname should not be null")
    @NotEmpty(message = "Please enter the Firstname")
    private String firstName;

    @NotNull(message = "LastName should not be null")
    @NotEmpty(message = "Please enter the Lastname")
    private String lastName;

    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "PLease enter the email")
    @Email(regexp = "[a-z0-9._-]+@([a-z0-9.-]+\\.)+[a-z]{2,3}$",message = "Enter valid Email address")
    private String email;

    private Integer age;

    @NotNull(message = "Position should not be null")
    @NotEmpty(message = "Please enter the position")
    private String position;

    @NotNull(message = "Gender should not be null")
    @NotEmpty(message = "PLease enter the gender")
    private String gender;

    @NotNull(message = "Date of birth should not be null")
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @NotNull(message = "Mobile number should not be null")
    @NotEmpty(message = "Mobile number should not be empty")
    private String mobileNumber;

    private String username;
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
    private List<UUID> patients = new ArrayList<>();
    private CareGiverNotes careGiverNotes;
    private List<PcaUmpiInfo> pcaUmpiInfoList;
    private String notes;
    private MedicalTest medicalTest;
    private DriverLicense driverLicense;
    private String role;
    private String profilePhoto;
    private String accountStatus;
}
