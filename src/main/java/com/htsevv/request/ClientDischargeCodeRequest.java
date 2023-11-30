package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDischargeCodeRequest {
    private String dischargeCode;
    private String codeDescription;
}
