package com.htsevv.service;

import com.htsevv.entity.Employee;
import com.htsevv.entity.Patient;
import com.htsevv.entity.generic.BasicInfoEntity;
import com.htsevv.exception.EmployeeExistsException;
import com.htsevv.exception.EmployeeNotFoundException;
import com.htsevv.repository.EmployeeRepository;
import com.htsevv.request.EmployeeRequest;
import com.htsevv.response.EmployeeResponse;
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
import org.springframework.web.multipart.MultipartFile;

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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final ValidationUtils validationUtils;
    private final FileStorageService fileStorageService;
    private final PatientService patientService;

    /**
     * Create a new employee
     *
     * @param request Employee request
     * @return Created employee information
     */
    public Employee addEmployee(EmployeeRequest request) {
        log.info("Creating new employee with email: {}", request.getEmail());
        Employee emp = this.employeeRepository.findByEmail(request.getEmail());
        if(ObjectUtils.isNotEmpty(emp)){
            log.error("Email with employee already exists: {}", request.getEmail());
            throw new EmployeeExistsException();
        }
        Employee employee = this.setEmployee(request, new Employee());
        return employeeRepository.save(employee);
    }

    /**
     * This method to set the employee request into employee entity.
     *
     * @param request Employee request
     * @param employee Employee entity
     * @return Employee entity
     */
    private Employee setEmployee(EmployeeRequest request, Employee employee){
        employee.setFirstName(validationUtils.truncateString(request.getFirstName(), 30));
        employee.setLastName(validationUtils.truncateString(request.getLastName(), 30));
        employee.setPosition(validationUtils.truncateString(request.getPosition(), 15));
        employee.setEmail(request.getEmail());
        employee.setGender(request.getGender());
        employee.setMobileNumber(request.getMobileNumber());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setUserName(request.getUsername());
        if(StringUtils.isNotEmpty(request.getPassword())){
            employee.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        employee.setAddress(request.getAddress());
        employee.setMailingAddress(request.getMailingAddress());
        employee.setCareGiverUMPI(request.getCareGiverUMPI());
        employee.setCareGiverLevel(request.getCareGiverLevel());
        employee.setSsn(request.getSsn());
        employee.setPayRate(request.getPayRate());
        employee.setDateOfHire(request.getDateOfHire());
        employee.setTerminationDate(request.getTerminationDate());
        employee.setEmergencyContactName(request.getEmergencyContactName());
        employee.setEmergencyContactNumber(request.getEmergencyContactNumber());
        employee.setLicenced(request.getLicenced());
        employee.setCareGiverNotes(request.getCareGiverNotes());
        employee.setPcaUmpiInfoList(request.getPcaUmpiInfoList());
        employee.setNotes(request.getNotes());
        employee.setMedicalTest(request.getMedicalTest());
        employee.setDriverLicense(request.getDriverLicense());
        employee.setRole(Employee.Role.valueOf(request.getRole()));
        employee.setAccountStatus(BasicInfoEntity.Status.valueOf(request.getAccountStatus()));
        List<Patient> patients = new ArrayList<>();
        for(UUID patientId: request.getPatients()){
            patients.add(patientService.getPatient(patientId));
        }
        employee.setPatients(patients);
        employee.setProfilePhoto(request.getProfilePhoto());
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = simpleDateFormat.format(date);
            Date formattedCurrentDate = simpleDateFormat.parse(currentDate);
            LocalDate birthDate = DateUtil.convertToLocalDateViaInstant(request.getDateOfBirth());
            LocalDate currentLocalDate = DateUtil.convertToLocalDateViaInstant(formattedCurrentDate);
            employee.setAge(DateUtil.calculateAge(birthDate,currentLocalDate));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * Get all Employees.
     *
     * @return List of employee information
     */
    public List<Employee> getAllEmployee(Date startDate, Date endDate){
        if(ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)){
            return this.employeeRepository.findByCreationDateBetween(startDate, DateUtil.getEndDate(endDate));
        }else {
            log.info("Getting all employees info");
            return this.employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
        }
    }

    public List<EmployeeResponse> getAllEmployeeInfo(Date startDate, Date endDate){
        List<Employee> employees = null;
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(startDate) && ObjectUtils.isNotEmpty(endDate)){
           employees = this.employeeRepository.findByCreationDateBetween(startDate, DateUtil.getEndDate(endDate));
        } else {
            employees = this.employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
        }
        for(Employee employee:employees){
            EmployeeResponse employeeResponse= new EmployeeResponse();
            List<PatientResponse> patients = new ArrayList<>();
            employeeResponse.setId(employee.getId());
            employeeResponse.setFirstName(employee.getFirstName());
            employeeResponse.setLastName(employee.getLastName());
            employeeResponse.setAge(employee.getAge());
            employeeResponse.setRole(employee.getRole().toString());
            employeeResponse.setCareGiverUMPI(employee.getCareGiverUMPI());
            employeeResponse.setCareGiverLevel(employee.getCareGiverLevel());
            employeeResponse.setDateOfBirth(employee.getDateOfBirth());
            employeeResponse.setCareGiverNotes(employee.getCareGiverNotes());
            employeeResponse.setAddress(employee.getAddress());
            employeeResponse.setSsn(employee.getSsn());
            employeeResponse.setPayRate(employee.getPayRate());
            employeeResponse.setDateOfHire(employee.getDateOfHire());
            employeeResponse.setGender(employee.getGender());
            employeeResponse.setProfilePhoto(employee.getProfilePhoto());
            employeeResponse.setMobileNumber(employee.getMobileNumber());
            employeeResponse.setEmergencyContactName(employee.getEmergencyContactName());
            employeeResponse.setEmergencyContactNumber(employee.getEmergencyContactNumber());
            employeeResponse.setLicenced(employee.getLicenced());
            employeeResponse.setPosition(employee.getPosition());
            for(Patient patient: employee.getPatients()){
                PatientResponse patientResponse = new PatientResponse();
                patientResponse.setId(patient.getId());
                patientResponse.setFirstName(patient.getFirstName());
                patientResponse.setLastName(patient.getLastName());
                patients.add(patientResponse);
            }
            employeeResponse.setPatients(patients);
            employeeResponse.setPcaUmpiInfoList(employee.getPcaUmpiInfoList());
            employeeResponse.setNotes(employee.getNotes());
            employeeResponse.setMailingAddress(employee.getMailingAddress());
            employeeResponse.setMedicalTest(employee.getMedicalTest());
            employeeResponse.setDriverLicense(employee.getDriverLicense());
            employeeResponse.setUserName(employee.getUserName());
            employeeResponse.setEmail(employee.getEmail());
            employeeResponse.setPassword(employee.getPassword());
            employeeResponses.add(employeeResponse);
        }
        return employeeResponses;
    }

    /**
     * Get employee by employee id.
     *
     * @param employeeId id of the employee
     * @return Employee information
     */
    public Employee getEmployee(UUID employeeId){
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EmployeeNotFoundException();
        });
    }

    public EmployeeResponse getEmployeeResponse(UUID employeeId){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new EmployeeNotFoundException();
        });
        EmployeeResponse employeeResponse = new EmployeeResponse();
        List<PatientResponse> patients = new ArrayList<>();
        employeeResponse.setId(employee.getId());
        employeeResponse.setFirstName(employee.getFirstName());
        employeeResponse.setLastName(employee.getLastName());
        employeeResponse.setAge(employee.getAge());
        employeeResponse.setPosition(employee.getPosition());
        employeeResponse.setRole(employee.getRole().toString());
        employeeResponse.setCareGiverUMPI(employee.getCareGiverUMPI());
        employeeResponse.setCareGiverLevel(employee.getCareGiverLevel());
        employeeResponse.setDateOfBirth(employee.getDateOfBirth());
        employeeResponse.setCareGiverNotes(employee.getCareGiverNotes());
        employeeResponse.setAddress(employee.getAddress());
        employeeResponse.setSsn(employee.getSsn());
        employeeResponse.setPayRate(employee.getPayRate());
        employeeResponse.setDateOfHire(employee.getDateOfHire());
        employeeResponse.setGender(employee.getGender());
        employeeResponse.setProfilePhoto(employee.getProfilePhoto());
        employeeResponse.setMobileNumber(employee.getMobileNumber());
        employeeResponse.setEmergencyContactName(employee.getEmergencyContactName());
        employeeResponse.setEmergencyContactNumber(employee.getEmergencyContactNumber());
        employeeResponse.setLicenced(employee.getLicenced());
        for(Patient patient: employee.getPatients()){
            PatientResponse patientResponse = new PatientResponse();
            patientResponse.setId(patient.getId());
            patientResponse.setFirstName(patient.getFirstName());
            patientResponse.setLastName(patient.getLastName());
            patients.add(patientResponse);
        }
        employeeResponse.setPatients(patients);
        employeeResponse.setPcaUmpiInfoList(employee.getPcaUmpiInfoList());
        employeeResponse.setNotes(employee.getNotes());
        employeeResponse.setMailingAddress(employee.getMailingAddress());
        employeeResponse.setMedicalTest(employee.getMedicalTest());
        employeeResponse.setDriverLicense(employee.getDriverLicense());
        employeeResponse.setUserName(employee.getUserName());
        employeeResponse.setEmail(employee.getEmail());
        employeeResponse.setPassword(employee.getPassword());

        return employeeResponse;
    }

    /**
     * Update employee with the given employee information.
     *
     * @param request employee request
     * @param employeeId id of the employee
     * @return Updated employee information
     */
    public Employee updateEmployee(EmployeeRequest request, UUID employeeId){
        Employee employee = this.setEmployee(request, this.getEmployee(employeeId));
        return employeeRepository.save(employee);
    }

    /**
     * Delete employee by employee id.
     *
     * @param employeeId id of the employee
     */
    public void deleteEmployee(UUID employeeId){
        if(employeeRepository.existsById(employeeId)) {
            log.info("Deleting employee {}", employeeId);
            this.employeeRepository.deleteById(employeeId);
        } else {
          throw new EmployeeNotFoundException();
        }
    }

    /**
     * To upload image to the server
     *
     * @param file Image to be uploaded
     * @return Filename of uploaded image
     */
    public String uploadImage(MultipartFile file) {
        if (ObjectUtils.isNotEmpty(file)) {
            this.validationUtils.validateUploadPhoto(file);
            return fileStorageService.storeFile(file,  UUID.randomUUID() + "_"+file.getOriginalFilename());
        }else {
            return null;
        }
    }

    /**
     * To upload pdf file to the server.
     *
     * @param file Pdf to be uploaded
     * @return Uploaded pdf filename
     */
    public String uploadPdfFile(MultipartFile file) {
        if (ObjectUtils.isNotEmpty(file)) {
            this.validationUtils.validateUploadPdf(file);
            return fileStorageService.storeFile(file,  UUID.randomUUID() + "_"+file.getOriginalFilename());
        }else {
            return null;
        }
    }

    public String checkAccountInformation(String userName){
        Employee user = employeeRepository.findByUserName(userName);
        if(ObjectUtils.isEmpty(user)) {
            return "Username Not found";
        }
        return "Username Found";
    }
}
