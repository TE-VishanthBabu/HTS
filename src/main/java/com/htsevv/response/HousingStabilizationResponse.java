package com.htsevv.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingStabilizationResponse{
    private String clientName;
    private String employeeName;
    private String serviceCode;
    private String staffSignature;
    private String notes;
    private Date startDate;
    private Date endDate;
    private Double hours;
    private Boolean exported;

}
