package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @NotNull(message = "Username Should not be null")
    @NotEmpty(message = "Enter the username")
    private String username;
    @NotEmpty(message = "Enter the password")
    @NotNull(message = "Password should not be null")
    private String password;
    private String name;
}
