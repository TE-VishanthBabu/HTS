package com.htsevv.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvvVisitRequest {

    @NotEmpty(message = "Service name should be required")
    private String serviceName;

    @NotNull(message = "Date of Service should be required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfService;

    @JsonFormat(pattern = "HH:mm")
    private String startTime;

    @JsonFormat(pattern = "HH:mm")
    private String endTime;

    @NotNull(message = "Provider should be required")
    private UUID providerId;

    @NotNull(message = "Patient should be required")
    private UUID patientId;

    @NotNull(message = "Location should be required")
    private UUID locationId;

    private String status;

    @Column(length = 10000)
    private String notes;
}
