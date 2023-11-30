package com.htsevv.entity;

import com.htsevv.entity.generic.AbstractEntity;
import com.htsevv.request.LocationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location extends AbstractEntity {

    private String locationName;

    private String address;

    private String locationPhoto;

    private String managerName;

    private String managerEmail;

    private String managerMobileNumber;

    private String photo;

    public Location(LocationRequest locationRequest) {
        super();
        setLocationName(locationRequest.getLocationName());
        setAddress(locationRequest.getAddress());
        setManagerName(locationRequest.getManagerName());
        setManagerEmail(locationRequest.getManagerEmail());
        setManagerMobileNumber(locationRequest.getManagerMobile());
    }
}
