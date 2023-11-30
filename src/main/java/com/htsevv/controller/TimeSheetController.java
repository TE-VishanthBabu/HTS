package com.htsevv.controller;

import com.htsevv.entity.TimeSheet;
import com.htsevv.request.ServiceRequest;
import com.htsevv.request.TimeSheetFilterRequest;
import com.htsevv.request.AddManualEntryRequest;
import com.htsevv.request.TimeSheetRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.response.EmployeeServiceResponse;
import com.htsevv.response.TimeSheetResponse;
import com.htsevv.service.TimeSheetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/billing")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class TimeSheetController {

    private final TimeSheetService timeSheetService;
    private final MessageSource messageSource;

    /**
     * Add manual entry timesheet.
     *
     * @param request
     * @return Manual entry timesheet
     */
    @PostMapping("/timesheet")
    public ResponseEntity<CommonResponse> addTimesheetEntry(@RequestBody AddManualEntryRequest request) {
        List<TimeSheet> timeSheet = this.timeSheetService.addManualEntry(request);
        log.info("Created Manual timesheet");
        CommonResponse response = new CommonResponse();
        response.setData(timeSheet);
        response.setMessage(messageSource.getMessage("timesheet.add",null, Locale.getDefault()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get service details.
     *
     * @param serviceId
     * @param request
     * @return service info
     */
    @GetMapping("/services/{serviceId}")
    public ResponseEntity<CommonResponse> getServices(@PathVariable String serviceId,
                                                      @RequestBody ServiceRequest request) {
        List<TimeSheet> timeSheet = this.timeSheetService.getServiceDetails(serviceId,request);
        CommonResponse response = new CommonResponse();
        response.setData(timeSheet);
        response.setMessage(messageSource.getMessage("get.serviceInfo",null, Locale.getDefault()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get employee service hours.
     *
     * @param employeeId
     * @param clientId
     * @return Employee service response
     */
    @GetMapping("/employee/{employeeId}/serviceHours")
    public ResponseEntity<CommonResponse> getEmployeeServiceHours(@PathVariable String employeeId,
                                                                  @RequestParam String clientId) {
        EmployeeServiceResponse employeeResponse = this.timeSheetService.getEmployeeServiceHours(employeeId,clientId);
        CommonResponse response = new CommonResponse();
        response.setData(employeeResponse);
        response.setMessage(messageSource.getMessage("get.employeeInfo",null, Locale.getDefault()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get timesheet.
     *
     * @param request
     * @return timesheet
     */
    @PostMapping("/timesheetInfo")
    public ResponseEntity<CommonResponse> getTimeSheet(@RequestBody(required = false) TimeSheetFilterRequest  request) {
        List<TimeSheetResponse> timeSheetInfo = this.timeSheetService.getTimeSheetInfo(request);
        CommonResponse response = new CommonResponse();
        response.setData(timeSheetInfo);
        response.setMessage(messageSource.getMessage("get.timesheet",null, Locale.getDefault()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update timesheet.
     *
     * @param timeSheetId
     * @param request
     * @return timesheet
     */
    @PutMapping("/timesheet/{timeSheetId}")
    public ResponseEntity<CommonResponse> updateTimeSheet(@PathVariable UUID timeSheetId, @RequestBody TimeSheetRequest request) {
        TimeSheet sheet = this.timeSheetService.updateTimeSheet(timeSheetId,request);
        CommonResponse response = new CommonResponse();
        response.setData(sheet);
        response.setMessage(messageSource.getMessage("update.timesheet",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Delete timesheet.
     *
     * @param timesheetId
     * @return deleted response
     */
    @DeleteMapping("/timesheet/{timesheetId}")
    public ResponseEntity<CommonResponse> deleteTimeSheet(@PathVariable UUID timesheetId) {
        this.timeSheetService.deleteTimeSheet(timesheetId);
        CommonResponse response = new CommonResponse();
        response.setMessage(messageSource.getMessage("delete.timesheet",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/timesheet/{timeSheetId}")
    public ResponseEntity<CommonResponse> getTimeSheet(@PathVariable UUID timeSheetId) {
        TimeSheetResponse timeSheetResponse = this.timeSheetService.getTimeSheet(timeSheetId);
        CommonResponse response = new CommonResponse();
        response.setData(timeSheetResponse);
        response.setMessage(messageSource.getMessage("get.timesheet",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
