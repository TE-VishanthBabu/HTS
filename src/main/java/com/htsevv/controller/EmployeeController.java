package com.htsevv.controller;

import com.htsevv.entity.Employee;
import com.htsevv.request.EmployeeRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.response.EmployeeResponse;
import com.htsevv.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RequestMapping("/employees")
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MessageSource messageSource;

    /**
     * To create a new Employee.
     *
     * @param request Employee request
     * @return created employee information
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse> addEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee employeeInfo = this.employeeService.addEmployee(request);
        log.info("New Employee have been added. Id: {}", employeeInfo.getId());
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(employeeInfo);
        commonResponse.setMessage(messageSource.getMessage("employee.add",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PostMapping("/upload/image")
    public ResponseEntity<CommonResponse> uploadImage(@RequestPart MultipartFile file){
        String fileName = this.employeeService.uploadImage(file);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(fileName);
        commonResponse.setMessage(messageSource.getMessage("employee.image.upload",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PostMapping("/upload/pdf")
    public ResponseEntity<CommonResponse> uploadPdf(@RequestPart MultipartFile file){
        String fileName = this.employeeService.uploadPdfFile(file);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(fileName);
        commonResponse.setMessage(messageSource.getMessage("employee.file.upload",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all the Employees.
     *
     * @return List of employees
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse> getAllEmployee(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        List<EmployeeResponse> employees =this.employeeService.getAllEmployeeInfo(startDate, endDate);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(employees);
        commonResponse.setMessage(messageSource.getMessage("employees.getAll",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get employee by employeeId.
     *
     * @param employeeId Id of the employee
     * @return employee information
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<CommonResponse> getEmployee(@PathVariable UUID employeeId) {
        EmployeeResponse employee=this.employeeService.getEmployeeResponse(employeeId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(employee);
        commonResponse.setMessage("Details of Employee "+employeeId);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Update Employee information with the given information.
     *
     * @param employeeId id of the employee
     * @param request Employee request
     * @return Updated employee information
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<CommonResponse> updateEmployee(@PathVariable UUID employeeId, @Valid @RequestBody EmployeeRequest request){
        Employee employeeInfo = this.employeeService.updateEmployee(request,employeeId);
        log.info("Details of the  employee {} has been updated",employeeInfo.getId());
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(employeeInfo);
        commonResponse.setMessage("Employee details for the id "+employeeInfo.getId()+" has been updated successfully");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Delete employee by employee id.
     *
     * @param employeeId id of the employee
     * @return Deleted response message
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<CommonResponse> deleteEmployee(@PathVariable UUID employeeId){
        this.employeeService.deleteEmployee(employeeId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage("Employee with id " + employeeId + " has been deleted.");
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @GetMapping("/account-info")
    public ResponseEntity<CommonResponse> checkAccountLoginInformation(@RequestParam String userName){
        String employee = this.employeeService.checkAccountInformation(userName);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(employee);
        commonResponse.setMessage((messageSource.getMessage("user.account",null, Locale.getDefault())));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
