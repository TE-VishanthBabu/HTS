package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientTypeListRequest {
    private Boolean status;
    private Integer number;
    private String typeName;
}
