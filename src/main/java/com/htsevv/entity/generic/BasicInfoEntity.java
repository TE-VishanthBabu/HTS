package com.htsevv.entity.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BasicInfoEntity extends AbstractEntity{

    public enum Status {
        ACTIVE, INACTIVE
    }
    private String firstName;
    private String lastName;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    @Column(name = "dob")
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private Integer age;
    private String gender;
    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity mailingAddress;
    private String mobileNumber;
    private String profilePhoto;
    private Status accountStatus;
}
