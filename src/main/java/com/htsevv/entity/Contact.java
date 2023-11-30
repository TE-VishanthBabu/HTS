package com.htsevv.entity;

import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contact extends UUIDEntity {
    private String name;
    private String relationshipType;
    private String phoneNo;
    private String email;
    private String street;
    private String state;
    private String contactType;
    private String carrierType;
    private String alternatePhoneNo;
    private String fax;
    private String city;
    private String zip;
}
