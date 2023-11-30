package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCareCoordinatorRequest {
    @NotNull(message = "Name should not be null")
    @NotEmpty(message = "Name should not be empty")
    private String name;
    private String country;
    private String telephone;
    private String fax;
    private String alternateFax;
    private String email;
}
