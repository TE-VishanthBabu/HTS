package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.HousingQA;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingStabilizationRequest {
    private String clientId;
    private String employeeId;
    private String contactType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Double hours;
    private Integer units;
    private String serviceDescription;
    private String serviceCode;
    private List<HousingQA> qas;
    private String task;
    private String taskDescription;
    private String notes;
    private String staffSignature;
}
