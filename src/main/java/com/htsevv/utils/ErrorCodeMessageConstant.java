package com.htsevv.utils;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCodeMessageConstant {
    USER_NOT_FOUND("ERR_10101","User not found"),
    UserName_NOT_FOUND("ERR_10122","User name not found"),
    EMPLOYEE_NOT_FOUND("ERR_10102","Employee not Found"),
    PATIENT_NOT_FOUND("ERR_10103","Patient not found"),
    LOCATION_NOT_FOUND("ERR_10104","Location not found"),
    VISIT_APPROVED("ERR_10105","Visit already approved, you can't edit it"),
    REJECTION_NOTES("ERR_10106","Rejection notes required"),
    VISIT_SERVICE("ERR_10107","Date of Service should be today's date"),
    PASSWORD_INCORRECT("ERR_10108","You entered incorrect password"),
    EMAIL_FAILED("ERR_10109","Email failed to sent"),
    AUTHENTICATION_FAILED("ERR_10110","Authentication token expired"),
    VISIT_NOT_FOUND("ERR_10111","No visits found"),
    VISIT_START_TIME("ERR_10112","Visit Start time should be required"),
    VALIDATION_ERROR("ERR_10113","Validation Error"),
    BAD_REQUEST("ERR_10114","Invalid Arguments"),
    INTERNAL_SERVER_ERROR("ERR_10115","File storage failed"),
    RESET_PASSWORD_ERROR("ERR_10116","Reset password failed"),
    FILE_UPLOAD_ERROR("ERR_10117","File upload failed"),
    DIAGNOSES_ERROR("ERR_10118","Diagnoses should not be empty"),
    EMERGENCY_ERROR("ERR_10119","Emergency contacts should not be empty"),
    EMPLOYEE_EXIST_ERROR("ERR_10120","Employee with email id already exists"),
    PATIENT_EXIST_ERROR("ERR_10121","Patient with email id already exists");

    private String code;
    private String message;

}
