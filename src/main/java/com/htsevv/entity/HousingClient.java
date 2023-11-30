package com.htsevv.entity;

import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted=false")
public class HousingClient extends AbstractEntity {
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
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
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientStartOfCare> careList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientNotes> clientNotes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientCertification> certifications;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientAuthorization> authorizations;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientMedication> medications;
    @OneToOne(cascade =CascadeType.ALL)
    private ClientPharmacy pharmacy;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> clientContacts;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientCaseManager> clientCaseManagers;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ClientCareCoordinator> clientCareCoordinators;
    @OneToMany(cascade = CascadeType.ALL)
    private List<EmployeeClientRelationship> relationships;
}
