package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPharmacyRequest {
    private String name;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
