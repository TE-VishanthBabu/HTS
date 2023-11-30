package com.htsevv.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetResponse {
    private String timeSheetId;
    private String employeeName;
    private String clientName;
    private Date timeSheetDate;
    private Boolean clientSign;
    private Boolean HousingCaseNotes;
    private Boolean employeeSign;
    private String timeSheetStatus;
    private Double totalHours;
    private Boolean eTimeSheet;
    private String service;
    private String payer;
    private String serviceId;
    private String clientId;
    private String employeeId;
    private String notes;
}
