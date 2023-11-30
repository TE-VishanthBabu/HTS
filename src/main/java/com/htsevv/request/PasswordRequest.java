package com.htsevv.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordRequest {
    @NotNull(message = "New password should not be null")
    @NotEmpty(message = "Enter the new password")
    private String newPassword;
    @NotNull(message = "Confirm password should not be null")
    @NotEmpty(message = "Enter the confirm password")
    private String confirmPassword;
}
