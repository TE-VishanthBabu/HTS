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
public class IAPP extends UUIDEntity {
    private String iappProcess;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastCompleted;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date MeetingDue45Days;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completedOn45Days;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewDate60Days;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completedOn60Days;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date followUpDue6Month;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completedOn6Month;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date annualIappDue;
}
