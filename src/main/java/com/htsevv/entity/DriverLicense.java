package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverLicense extends UUIDEntity {
    private String licensePhoto;
    private String licenseNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    private String state;
}
