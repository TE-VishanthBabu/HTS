package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingClientRequest {
    private UUID id;
    @NotNull(message = "FirstName should not be null")
    @NotEmpty(message = "Please Enter the firstname")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "Please Enter the lastname")
    @NotNull(message = "LastName should not be null")
    private String lastName;
    @NotNull(message = "Gender should not be null")
    @NotEmpty(message = "Please pick the gender")
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Date of birth should not be null")
    private Date dateOfBirth;
    private String medicaId;
    private String languages;
    private String waiverTYpe;
    private Integer weight;
    private String aka;
    private Integer age;
    private String clientType;
    private String medicare;
    private String clientInsuranceId;
    private String pcaType;
    private Integer height;
    private String street;
    private String apt;
    private String phone1;
    private String phone2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String internalId;
    private String assessmentRating;
    private String priorityStatus;
    private String billName;
    private String billStreet;
    private String billCity;
    private String billState;
    private String billZipCode;
    private String billPhone;
    private String billEmail;
    private String userName;
    private String password;
    private List<ClientNotes> clientNotes;
    private List<ClientStartOfCare> careList;
    private List<ClientCertification> certifications;
    private List<ClientAuthorization> authorizations;
    private List<ClientMedication> medications;
    private UUID pharmacyId;
    private List<Contact> clientContacts;
    private List<UUID> clientCaseManagers;
    private List<UUID> clientCareCoordinator;
    private List<EmployeeClientRelationship> relationships;
}
