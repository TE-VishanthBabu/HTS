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
public class ClientAuthorization extends UUIDEntity {
    private String companyName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String payer;
    private String serviceCode;
    private String serviceDescription;
    private String netRate;
    private String ucRate;
    private String seNo;
    private Integer ttUts;
    private String insNo;
    private String hoursPerDay;
    private Boolean tempAuth;

}
