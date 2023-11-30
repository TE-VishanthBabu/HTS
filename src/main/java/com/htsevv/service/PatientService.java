package com.htsevv.service;

import com.htsevv.entity.Employee;
import com.htsevv.entity.Location;
import com.htsevv.entity.Patient;
import com.htsevv.entity.generic.BasicInfoEntity;
import com.htsevv.exception.*;
import com.htsevv.repository.EmployeeRepository;
import com.htsevv.repository.LocationRepository;
import com.htsevv.repository.PatientRepository;
import com.htsevv.request.PatientRequest;
import com.htsevv.response.EmployeeResponse;
import com.htsevv.response.LocationResponse;
import com.htsevv.response.PatientResponse;
import com.htsevv.utils.DateUtil;
import com.htsevv.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final ValidationUtils validationUtils;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final LocationRepository locationRepository;

    /**
     * Create a new patient.
     *
     * @param patientRequest Patient request
     * @return Created patient details
     */
    public Patient addNewPatient(PatientRequest patientRequest) {
        Patient patient = this.patientRepository.findByEmail(patientRequest.getEmail());
        if(ObjectUtils.isNotEmpty(patient)){
            log.error("Email with patient already exists: {}", patient.getEmail());
            throw new PatientExistsException();
        }
        Patient patientToAdd = this.setPatient(patientRequest, new Patient());
        return patientRepository.save(patientToAdd);
    }

    private Patient setPatient(PatientRequest request, Patient patient) {
        patient.setFirstName(validationUtils.truncateString(request.getFirstName(), 30));
        patient.setLastName(validationUtils.truncateString(request.getLastName(), 30));
        patient.setEmail(request.getEmail());
        patient.setGender(request.getGender());
        patient.setMobileNumber(request.getMobileNumber());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setUserName(request.getUsername());
        if(StringUtils.isNotEmpty(request.getPassword())){
            patient.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        patient.setAddress(request.getAddress());
        patient.setMailingAddress(request.getMailingAddress());
        patient.setNotes(request.getNotes());
        patient.setAccountStatus(BasicInfoEntity.Status.valueOf(request.getAccountStatus()));
        List<Employee> careGivers = new ArrayList<>();
        for(UUID careGiverId: request.getCaregivers()){
            Employee employee = this.employeeRepository.findById(careGiverId).orElseThrow(() -> {
                throw new EmployeeNotFoundException();
            });
            careGivers.add(employee);
        }
        patient.setCaregivers(careGivers);
        patient.setProfilePhoto(request.getProfilePhoto());
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = simpleDateFormat.format(date);
            Date formattedCurrentDate = simpleDateFormat.parse(currentDate);
            LocalDate birthDate = DateUtil.convertToLocalDateViaInstant(request.getDateOfBirth());
            LocalDate currentLocalDate = DateUtil.convertToLocalDateViaInstant(formattedCurrentDate);
            patient.setAge(DateUtil.calculateAge(birthDate,currentLocalDate));
        }catch (Exception e) {
            e.printStackTrace();
        }
        patient.setPMI(request.getPmi());
        patient.setAdmissionDate(request.getAdmissionDate());
        patient.setDischargeDate(request.getDischargeDate());
        patient.setInsuranceNumber(request.getInsuranceNumber());
        patient.setPatientCode(request.getPatientCode());
        patient.setMedicalSpendDown(request.getMedicalSpendDown());
        patient.setAmount(request.getAmount());
        patient.setPcaChoice(request.getPcaChoice());
        patient.setPatient245D(request.isPatient245D());
        patient.setHasReponsibleParty(request.isHasReponsibleParty());
        patient.setCaseManager(request.getCaseManager());
        patient.setPcaSupervision(request.getPcaSupervision());
        patient.setIapp(request.getIapp());
        patient.setPcaCarePlan(request.getPcaCarePlan());
        patient.setAvailableServices(request.getAvailableServices());
        List<Location> locations = new ArrayList<>();
        for(UUID locationId: request.getLocations()){
            Location location = this.locationRepository.findById(locationId).orElseThrow(() -> {
                throw new LocationNotFoundException();
            });
            locations.add(location);
        }
        patient.setLocations(locations);
        patient.setPlaceOfService(request.getPlaceOfService());
        patient.setEmergencyContacts(request.getEmergencyContacts());
        patient.setCarePlanDue(request.getCarePlanDue());
        patient.setNotes(request.getNotes());
        return patient;
    }

    /**
     * Get all the patients
     *
     * @return List of patients information
     */
    public List<Patient> getAllPatients(Date startDate, Date endDate) {
        if(ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)){
            return this.patientRepository.findByCreationDateBetween(startDate, DateUtil.getEndDate(endDate));
        }else {
            log.info("Getting all the patients info");
            return this.patientRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
        }
    }

    /**
     * Get the patient details by patient id.
     *
     * @param patientId
     * @return Patient information
     */
    public Patient getPatient(UUID patientId) {
        log.info("Getting details of patient {}", patientId);
        return this.patientRepository.findById(patientId).orElseThrow(() -> {
            throw new PatientNotFoundException();
        });
    }

    public PatientResponse getPatientInfo(UUID patientId){
        log.info("Getting details of patient {}", patientId);
        Patient patient = this.patientRepository.findById(patientId).orElseThrow(() -> {
            throw new PatientNotFoundException();
        });
        PatientResponse patientResponse = new PatientResponse();
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        List<LocationResponse> locationResponses = new ArrayList<>();
        patientResponse.setId(patient.getId());
        patientResponse.setFirstName(patient.getFirstName());
        patientResponse.setLastName(patient.getLastName());
        patientResponse.setUserName(patient.getUserName());
        patientResponse.setEmail(patient.getEmail());
        patientResponse.setMobileNumber(patient.getMobileNumber());
        patientResponse.setGender(patient.getGender());
        patientResponse.setAge(patient.getAge());
        patientResponse.setPatient245D(patient.isPatient245D());
        patientResponse.setPatientCode(patient.getPatientCode());
        patientResponse.setAddress(patient.getAddress());
        patientResponse.setAmount(patient.getAmount());
        patientResponse.setDateOfBirth(patient.getDateOfBirth());
        patientResponse.setDischargeDate(patient.getDischargeDate());
        patientResponse.setAdmissionDate(patient.getAdmissionDate());
        patientResponse.setPmi(patient.getPMI());
        patientResponse.setAvailableServices(patient.getAvailableServices());
        patientResponse.setCaseManager(patient.getCaseManager());
        patientResponse.setEmergencyContacts(patient.getEmergencyContacts());
        patientResponse.setHasReponsibleParty(patient.isHasReponsibleParty());
        patientResponse.setIapp(patient.getIapp());
        patientResponse.setInsuranceNumber(patient.getInsuranceNumber());
        patientResponse.setMailingAddress(patient.getMailingAddress());
        patientResponse.setMedicalSpendDown(patient.getMedicalSpendDown());
        patientResponse.setNotes(patient.getNotes());
        patientResponse.setPcaCarePlan(patient.getPcaCarePlan());
        patientResponse.setPcaChoice(patient.getPcaChoice());
        patientResponse.setPcaSupervision(patient.getPcaSupervision());
        patientResponse.setPlaceOfService(patient.getPlaceOfService());
        patientResponse.setPassword(patient.getPassword());
        for(Employee employee:patient.getCaregivers()){
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setId(employee.getId());
            employeeResponse.setUserName(employee.getUserName());
            employeeResponse.setFirstName(employee.getLastName());
            employeeResponse.setNotes(employee.getNotes());
            employeeResponses.add(employeeResponse);
        }
        for(Location location:patient.getLocations()){
            LocationResponse response = new LocationResponse();
            response.setId(location.getId());
            response.setLocationName(location.getLocationName());
            response.setManagerEmail(location.getManagerEmail());
            response.setManagerMobileNumber(location.getManagerMobileNumber());
            response.setManagerName(location.getManagerName());
            response.setLocationPhoto(location.getLocationPhoto());
            response.setPhoto(location.getPhoto());
            locationResponses.add(response);

        }
        patientResponse.setLocations(locationResponses);
        patientResponse.setCaregivers(employeeResponses);
        patientResponse.setProfilePhoto(patient.getProfilePhoto());
        patientResponse.setCarePlanDue(patient.getCarePlanDue());
        return patientResponse;
    }

    public List<PatientResponse> getAllPatientResponses(Date startDate, Date endDate) {
        List<Patient> patients = null;
        List<PatientResponse> patientResponses = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)){
            patients = this.patientRepository.findByCreationDateBetween(startDate, DateUtil.getEndDate(endDate));
        }else {
            log.info("Getting all the patients info");
            patients = this.patientRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
        }
        for(Patient patient:patients){
            PatientResponse patientResponse = new PatientResponse();
            List<EmployeeResponse> employeeResponses = new ArrayList<>();
            List<LocationResponse> locationResponses = new ArrayList<>();
            patientResponse.setId(patient.getId());
            patientResponse.setFirstName(patient.getFirstName());
            patientResponse.setLastName(patient.getLastName());
            patientResponse.setUserName(patient.getUserName());
            patientResponse.setEmail(patient.getEmail());
            patientResponse.setMobileNumber(patient.getMobileNumber());
            patientResponse.setGender(patient.getGender());
            patientResponse.setAge(patient.getAge());
            patientResponse.setPatient245D(patient.isPatient245D());
            patientResponse.setPatientCode(patient.getPatientCode());
            patientResponse.setAddress(patient.getAddress());
            patientResponse.setAmount(patient.getAmount());
            patientResponse.setDateOfBirth(patient.getDateOfBirth());
            patientResponse.setDischargeDate(patient.getDischargeDate());
            patientResponse.setAdmissionDate(patient.getAdmissionDate());
            patientResponse.setPmi(patient.getPMI());
            patientResponse.setAvailableServices(patient.getAvailableServices());
            patientResponse.setCaseManager(patient.getCaseManager());
            patientResponse.setEmergencyContacts(patient.getEmergencyContacts());
            patientResponse.setHasReponsibleParty(patient.isHasReponsibleParty());
            patientResponse.setIapp(patient.getIapp());
            patientResponse.setInsuranceNumber(patient.getInsuranceNumber());
            patientResponse.setMailingAddress(patient.getMailingAddress());
            patientResponse.setMedicalSpendDown(patient.getMedicalSpendDown());
            patientResponse.setNotes(patient.getNotes());
            patientResponse.setPcaCarePlan(patient.getPcaCarePlan());
            patientResponse.setPcaChoice(patient.getPcaChoice());
            patientResponse.setPcaSupervision(patient.getPcaSupervision());
            patientResponse.setPlaceOfService(patient.getPlaceOfService());
            patientResponse.setPassword(patient.getPassword());
            for(Employee employee:patient.getCaregivers()){
                EmployeeResponse employeeResponse = new EmployeeResponse();
                employeeResponse.setId(employee.getId());
                employeeResponse.setUserName(employee.getUserName());
                employeeResponse.setFirstName(employee.getLastName());
                employeeResponse.setNotes(employee.getNotes());
                employeeResponses.add(employeeResponse);
            }
            for(Location location:patient.getLocations()){
                LocationResponse response = new LocationResponse();
                response.setId(location.getId());
                response.setLocationName(location.getLocationName());
                response.setManagerEmail(location.getManagerEmail());
                response.setManagerMobileNumber(location.getManagerMobileNumber());
                response.setManagerName(location.getManagerName());
                response.setLocationPhoto(location.getLocationPhoto());
                response.setPhoto(location.getPhoto());
                locationResponses.add(response);

            }
            patientResponse.setLocations(locationResponses);
            patientResponse.setCaregivers(employeeResponses);
            patientResponse.setProfilePhoto(patient.getProfilePhoto());
            patientResponse.setNotes(patient.getNotes());
            patientResponse.setCarePlanDue(patient.getCarePlanDue());
            patientResponses.add(patientResponse);
        }
        return patientResponses;
    }

    /**
     * Update patient with given details.
     *
     * @param patientId
     * @param patientRequest
     * @return Updated patient details
     */
    public Patient updatePatient(UUID patientId, PatientRequest patientRequest) {
        log.info("Updating details of patient {}", patientId);
        Patient patient = this.setPatient(patientRequest, this.getPatient(patientId));
        return patientRepository.save(patient);
    }

    /**
     * Delete Patient.
     *
     * @param patientId
     */
    public void deletePatient(UUID patientId) {
            this.getPatient(patientId);
            log.info("Deleting patient {}", patientId);
            this.patientRepository.deleteById(patientId);
    }

    public String checkPartyLoginInformation(String userName){
        Patient patient = patientRepository.findByUserName(userName);
        if(ObjectUtils.isEmpty(patient)){
            return "Username Not found";
        }
        return "Username Found";
    }
}
