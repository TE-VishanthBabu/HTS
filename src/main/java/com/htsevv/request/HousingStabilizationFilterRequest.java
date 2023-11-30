package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingStabilizationFilterRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date must not be null")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date must not be null")
    private Date endDate;
    private String employeeId;
    private String clientId;
    private String contactType;
    private String status;
    private String validate;
}
