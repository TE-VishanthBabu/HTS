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
public class ClientPharmacy extends UUIDEntity {
    private String name;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
