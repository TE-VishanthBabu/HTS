package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientStartOfCare extends UUIDEntity {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startOfCare;
    private String companyName;
    private Boolean discharge;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dischargeDate;
    private String dischargeStatusCode;
    private String dischargeDescription;
}
