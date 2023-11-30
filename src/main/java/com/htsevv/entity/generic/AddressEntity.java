package com.htsevv.entity.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity extends UUIDEntity{
    private String address;
    private String city;
    private String province;
    private String zip;
}
