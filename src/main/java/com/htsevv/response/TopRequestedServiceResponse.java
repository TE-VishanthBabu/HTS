package com.htsevv.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopRequestedServiceResponse {
    private String service_name;
    private Integer count;
}
