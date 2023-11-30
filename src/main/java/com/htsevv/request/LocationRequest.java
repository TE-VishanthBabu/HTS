package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    @NotEmpty(message = "Location name should be required")
    @NotNull(message = "Location name should not be null")
    private String locationName;

    @NotEmpty(message = "Address should be required")
    @NotNull(message = "Address should not be null")
    private String address;

    @NotEmpty(message = "Location Manager Name should be required")
    @NotNull(message = "Manager name should not be null")
    private String managerName;

    @NotEmpty(message = "Location Manager Email should be required")
    @Email(regexp = "[a-z0-9._-]+@([a-z0-9.-]+\\.)+[a-z]{2,3}$",message = "Invalid Email Pattern")
    private String managerEmail;

    @NotEmpty(message = "Location Manager Mobile Number should be required")
    @NotNull(message = "Manager number should not be null")
    private String managerMobile;
}
