package com.htsevv.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.htsevv.constants.Constant;
import com.htsevv.response.CommonResponse;
import com.htsevv.response.ExceptionResponse;
import com.htsevv.utils.ErrorCodeMessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<CommonResponse> handlePatientNotFoundException(PatientNotFoundException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("patient.notFound",null, Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED,ErrorCodeMessageConstant.PATIENT_NOT_FOUND.getCode(),
                response, ErrorCodeMessageConstant.PATIENT_NOT_FOUND.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(CommonException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(ex.getStatus().value());
        response.setStatus(ex.getStatus().getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setDetailedError(ex.getDetailedError());
        return new ResponseEntity<>(response, ex.getStatus());
    }
    @ExceptionHandler(VisitAlreadyApprovedException.class)
    public ResponseEntity<CommonResponse> handleVisitAlreadyApprovedException(VisitAlreadyApprovedException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("visit.approved",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.VISIT_APPROVED.getCode(),
                response, ErrorCodeMessageConstant.VISIT_APPROVED.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectServiceDateException.class)
    public ResponseEntity<CommonResponse> handleIncorrectServiceDateException(IncorrectServiceDateException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("service.date",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED,ErrorCodeMessageConstant.VISIT_SERVICE.getCode(),
                response, ErrorCodeMessageConstant.VISIT_SERVICE.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RejectionNotesRequiredException.class)
    public ResponseEntity<CommonResponse> handleRejectionNotesRequiredException(RejectionNotesRequiredException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("rejection.notes",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.REJECTION_NOTES.getCode(),
                response, ErrorCodeMessageConstant.REJECTION_NOTES.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<CommonResponse> handleLocationNotFoundException(LocationNotFoundException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("location.notFound",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED,ErrorCodeMessageConstant.LOCATION_NOT_FOUND.getCode(),
                response, ErrorCodeMessageConstant.LOCATION_NOT_FOUND.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CommonResponse> handleUserNotFoundException(UserNotFoundException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("user.notFound",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.USER_NOT_FOUND.getCode(),
                response, ErrorCodeMessageConstant.USER_NOT_FOUND.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<CommonResponse> handleUserNameNotFoundException(UserNameNotFoundException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("userName.notFound",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.UserName_NOT_FOUND.getCode(),
                response, ErrorCodeMessageConstant.UserName_NOT_FOUND.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse> handleBadCredentialsException(BadCredentialsException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("password.incorrect",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.PASSWORD_INCORRECT.getCode(),
                response, ErrorCodeMessageConstant.PASSWORD_INCORRECT.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotSentException.class)
    public ResponseEntity<CommonResponse> handleEmailNotSentException(EmailNotSentException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("email.failed",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.EMAIL_FAILED.getCode(),
                response, ErrorCodeMessageConstant.EMAIL_FAILED.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<CommonResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("employee.notFound",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.EMPLOYEE_NOT_FOUND.getCode(),
                response, ErrorCodeMessageConstant.EMPLOYEE_NOT_FOUND.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeExistsException.class)
    public ResponseEntity<CommonResponse> handleEmployeeExistsException() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", ErrorCodeMessageConstant.EMPLOYEE_EXIST_ERROR.getMessage());
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.EMPLOYEE_EXIST_ERROR.getCode(),
                response, ErrorCodeMessageConstant.EMPLOYEE_EXIST_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PatientExistsException.class)
    public ResponseEntity<CommonResponse> handlePatientExistsException() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", ErrorCodeMessageConstant.PATIENT_EXIST_ERROR.getMessage());
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.PATIENT_EXIST_ERROR.getCode(),
                response, ErrorCodeMessageConstant.PATIENT_EXIST_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<CommonResponse> handleTokenExpiredException(TokenExpiredException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("authentication.failed",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.AUTHENTICATION_FAILED.getCode(),
                response, ErrorCodeMessageConstant.AUTHENTICATION_FAILED.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFileFormat.class)
    public ResponseEntity<CommonResponse> handleFileTypeException(InvalidFileFormat ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.FILE_UPLOAD_ERROR.getCode(),
                response, ErrorCodeMessageConstant.FILE_UPLOAD_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingDiagnosesException.class)
    public ResponseEntity<CommonResponse> handleDiagnosesException(MissingDiagnosesException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("diagnosis.required",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.DIAGNOSES_ERROR.getCode(),
                response, ErrorCodeMessageConstant.DIAGNOSES_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingEmergencyContactException.class)
    public ResponseEntity<CommonResponse> handleEmergencyContactException(MissingEmergencyContactException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("emergency.required",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.EMERGENCY_ERROR.getCode(),
                response, ErrorCodeMessageConstant.EMERGENCY_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VisitNotFoundException.class)
    public ResponseEntity<CommonResponse> handleVisitNotFoundException(VisitNotFoundException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("visit.notFound",null, Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.VISIT_NOT_FOUND.getCode(),
                response, ErrorCodeMessageConstant.VISIT_NOT_FOUND.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CannotResetPasswordException.class)
    public ResponseEntity<CommonResponse> handleCannotResetPasswordException(CannotResetPasswordException ex){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", ex.getMessage());
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.RESET_PASSWORD_ERROR.getCode(), response, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(VisitStartTimeMissingException.class)
    public ResponseEntity<CommonResponse> handleVisitStartTimeMissingException(VisitStartTimeMissingException ex) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", messageSource.getMessage("visit.startTime",null,Locale.getDefault()));
        response.put("timestamp", new Timestamp(System.currentTimeMillis()));
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.VISIT_START_TIME.getCode(),
                response, ErrorCodeMessageConstant.VISIT_START_TIME.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                          HttpHeaders headers,
                                                          HttpStatus status, WebRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        List<HashMap<String, Object>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x->{
                    HashMap<String, Object> error = new HashMap<>();
                    error.put("field", x.getField());
                    error.put("errorMessage", x.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        response.put("details", errors);
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.BAD_REQUEST.getCode(),
                response, ErrorCodeMessageConstant.BAD_REQUEST.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        HashMap<String, Object> response = new HashMap<>();
        List<HashMap<String, Object>> errors = ex.getConstraintViolations()
                .stream()
                .map(x->{
                    HashMap<String, Object> error = new HashMap<>();
                    String field = x.getPropertyPath().toString();
                    String[] list  = field.split("[.]", 0);

                    error.put("field", list[list.length-1]);
                    error.put("errorMessage", x.getMessage());
                    return error;
                }).collect(Collectors.toList());
        response.put("details", errors);
        CommonResponse error = new CommonResponse(Constant.STATUS_FAILED, ErrorCodeMessageConstant.VALIDATION_ERROR.getCode(),
                response, ErrorCodeMessageConstant.VALIDATION_ERROR.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

