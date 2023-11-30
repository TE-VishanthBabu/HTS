package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSheetRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotEmpty(message = "TimeSheet date should not be empty")
    private Date timeSheetDate;
    private String notes;
    @NotEmpty(message = "Total hours should not be empty")
    private Double totalHours;
    @NotEmpty(message = "Service should not be empty")
    @NotNull(message = "Services should not be null")
    private String serviceId;
}
