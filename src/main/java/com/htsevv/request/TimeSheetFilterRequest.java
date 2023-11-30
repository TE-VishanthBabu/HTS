package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetFilterRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "Start date must not be empty")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "End date must not be empty")
    private Date endDate;
    private String employeeId;
    private String clientId;
    private String payor;
    private String serviceGroup;
}
