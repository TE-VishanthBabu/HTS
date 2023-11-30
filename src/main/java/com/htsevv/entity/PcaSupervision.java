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
public class PcaSupervision extends UUIDEntity {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date serviceRenewalDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date carePlanDue;
    private String supervisionNeeded;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date careGiverStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date supervisionDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastCompleted;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date nextSupervisionDate;
    private String qpAssigned;
}
