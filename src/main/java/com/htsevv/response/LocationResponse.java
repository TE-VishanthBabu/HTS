package com.htsevv.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {

    private UUID id;

    private String locationName;

    private String address;

    private String locationPhoto;

    private String managerName;

    private String managerEmail;

    private String managerMobileNumber;

    private String photo;
}
