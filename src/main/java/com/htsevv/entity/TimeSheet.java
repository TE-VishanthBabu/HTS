package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted=false")
public class TimeSheet extends AbstractEntity {

    private String serviceId;
    private String employeeId;
    private String clientId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date timeSheetDate;
    private String notes;
    private Double totalHours;
    private Boolean eTimeSheet;
    private String timeSheetFile;
}
