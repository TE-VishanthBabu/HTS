package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCaseManagerRequest {
    private String name;
    private String country;
    private String telephone;
    private String fax;
    private String alternateFax;
    private String email;
}
