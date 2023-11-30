package com.htsevv.controller;

import com.htsevv.entity.Patient;
import com.htsevv.request.PatientRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.response.PatientResponse;
import com.htsevv.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RequestMapping("/patients")
@RequiredArgsConstructor
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {    private final PatientService patientService;

    @Autowired
    private MessageSource messageSource;

    /**
     * To create a new Patient.
     *
     * @param request
     * @return created patient information
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse> addNewPatient(@Valid @RequestBody PatientRequest request) {
        Patient patient = this.patientService.addNewPatient(request);
        log.info("New Patient have been added. Id: {}", patient.getId());
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(patient);
        commonResponse.setMessage(messageSource.getMessage("patient.add",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all the patients.
     *
     * @return List of patients
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse> getAllPatients(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        List<PatientResponse> patients = this.patientService.getAllPatientResponses(startDate, endDate);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(patients);
        commonResponse.setMessage(messageSource.getMessage("patients.getAll",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get patient by patient id
     *
     * @param patientId
     * @return Patient information
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<CommonResponse> getPatient(@PathVariable UUID patientId) {
        PatientResponse patient = this.patientService.getPatientInfo(patientId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(patient);
        commonResponse.setMessage("Details of Patient " + patientId);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Update patient information with given details
     *
     * @param patientId
     * @param request
     * @return Updated patient details
     */
    @PutMapping("/{patientId}")
    public ResponseEntity<CommonResponse> updatePatient(@PathVariable UUID patientId,
                                                        @Valid @RequestBody PatientRequest request) {
        Patient patient = this.patientService.updatePatient(patientId, request);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(patient);
        commonResponse.setMessage(messageSource.getMessage("patient.update",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete patient by patient id.
     *
     * @param patientId
     * @return Response message
     */
    @DeleteMapping("/{patientId}")
    public ResponseEntity<CommonResponse> deletePatient(@PathVariable UUID patientId) {
        this.patientService.deletePatient(patientId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage("Patient with id " + patientId + " has been deleted.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @GetMapping("/account-info")
    public ResponseEntity<CommonResponse> checkPartyLoginInformation(@RequestParam String userName){
        String patient=this.patientService.checkPartyLoginInformation(userName);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(patient);
        commonResponse.setMessage((messageSource.getMessage("user.account",null, Locale.getDefault())));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
