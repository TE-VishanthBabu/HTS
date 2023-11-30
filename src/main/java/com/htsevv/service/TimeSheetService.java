package com.htsevv.service;

import com.htsevv.entity.Employee;
import com.htsevv.entity.Patient;
import com.htsevv.entity.TimeSheet;
import com.htsevv.exception.CommonException;
import com.htsevv.repository.*;
import com.htsevv.request.ServiceRequest;
import com.htsevv.request.TimeSheetFilterRequest;
import com.htsevv.request.AddManualEntryRequest;
import com.htsevv.request.TimeSheetRequest;
import com.htsevv.response.EmployeeServiceResponse;
import com.htsevv.response.TimeSheetResponse;
import com.htsevv.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeSheetService {
    private final TimeSheetRepository timeSheetRepository;
    private final EmployeeRepository employeeRepository;
    private final PatientRepository patientRepository;
    private final ServiceRepository serviceRepository;
    private final EmployeeService employeeService;
    private final PatientService patientService;
    private final DateUtil dateUtil;

    /**
     * Add manual entry timesheet.
     *
     * @param request
     * @return list of Timesheets
     */
    public List<TimeSheet> addManualEntry(AddManualEntryRequest request) {
        TimeSheet timeSheet = null;
        List<TimeSheet> timeSheets = new ArrayList<>();
        for(TimeSheet serviceRequest:request.getTimeSheetEntry()) {
            Employee employee = employeeRepository.findById(UUID.fromString(serviceRequest.getEmployeeId())).orElseThrow(()->{
                throw new CommonException("Employee Id not found");
            });
            Patient patient = patientRepository.findById(UUID.fromString(serviceRequest.getClientId())).orElseThrow(()->{
                throw new CommonException("Patient Id not found");
            });
            com.htsevv.entity.Service service = serviceRepository.findById(UUID.fromString(serviceRequest.getServiceId())).orElseThrow(()->{
                throw new CommonException("service Id not found");
            });
            timeSheet = new TimeSheet();
            timeSheet.setEmployeeId(employee.getId().toString());
            timeSheet.setClientId(patient.getId().toString());
            timeSheet.setServiceId(service.getId().toString());
            timeSheet.setTimeSheetDate(serviceRequest.getTimeSheetDate());
            timeSheet.setTotalHours(serviceRequest.getTotalHours());
            timeSheet.setNotes(serviceRequest.getNotes());
            timeSheet.setTimeSheetFile(serviceRequest.getTimeSheetFile());
            timeSheet.setETimeSheet(false);
            timeSheet= timeSheetRepository.save(timeSheet);
            timeSheets.add(timeSheet);
        }
        return timeSheets;

    }

    /**
     * Get service details within the date range
     *
     * @param serviceId
     * @param request
     * @return list of service info
     */
    public List<TimeSheet> getServiceDetails(String serviceId, ServiceRequest request) {
        return this.timeSheetRepository.findAllByServiceIdAndClientIdEqualsAndEmployeeIdEqualsAndTimeSheetDateBetween(serviceId,
                request.getClientId(),request.getEmployeeId(),request.getStartDate(),request.getEndDate());
    }

    /**
     * Get employee service total hours.
     *
     * @param employeeId
     * @param clientId
     * @return employee response
     */
    public EmployeeServiceResponse getEmployeeServiceHours(String employeeId,String clientId) {
        Double totalHours = this.timeSheetRepository.findTotalHoursByEmployeeId(employeeId,clientId);
        EmployeeServiceResponse response = new EmployeeServiceResponse();
        response.setTotalHours(totalHours);
        return response;
    }

    /**
     * Get timesheet info by filters.
     *
     * @param request
     * @return get timesheet info
     */
    public List<TimeSheetResponse> getTimeSheetInfo(TimeSheetFilterRequest request) {
        List<TimeSheet> timeSheets= timeSheetRepository.findAll().stream()
                .filter(timeSheet -> this.dateFilter(timeSheet.getTimeSheetDate(),request.getStartDate(),request.getEndDate()))
                .filter(timeSheet -> this.employeeFilter(timeSheet.getEmployeeId(),request.getEmployeeId()))
                .filter(timeSheet -> this.clientFilter(timeSheet.getClientId(),request.getClientId())).collect(Collectors.toList());
        return this.mapTimeSheetToTimeSheetResponse(timeSheets);
    }

    /**
     * Map timesheet info to response.
     *
     * @param sheets
     * @return timesheet response
     */
    public List<TimeSheetResponse> mapTimeSheetToTimeSheetResponse(List<TimeSheet> sheets) {
        List<TimeSheetResponse> timeSheetResponses = new ArrayList<>();
        sheets.forEach(timeSheet -> {
            TimeSheetResponse response = new TimeSheetResponse();
            response.setEmployeeName(employeeService.getEmployee(UUID.fromString(timeSheet.getEmployeeId())).getUserName());
            response.setClientName(patientService.getPatient(UUID.fromString(timeSheet.getClientId())).getUserName());
            com.htsevv.entity.Service service = this.serviceRepository.findById(UUID.fromString(timeSheet.getServiceId())).orElseThrow(()->{
                throw new CommonException("Service Id not found");
            });

            response.setTimeSheetId(timeSheet.getId().toString());
            response.setClientSign(false);
            response.setService(service.getName());
            response.setHousingCaseNotes(false);
            response.setEmployeeSign(false);
            response.setTimeSheetDate(timeSheet.getTimeSheetDate());
            response.setTotalHours(timeSheet.getTotalHours());
            response.setETimeSheet(timeSheet.getETimeSheet());
            timeSheetResponses.add(response);
        });
        return timeSheetResponses;
    }

    /**
     * Update timesheet.
     *
     * @param timeSheetId
     * @param request
     * @return timesheet
     */
    public TimeSheet updateTimeSheet(UUID timeSheetId, TimeSheetRequest request) {
        TimeSheet sheet = timeSheetRepository.findById(timeSheetId).orElseThrow(()->{
            throw new CommonException("TimeSheet Id not found");
        });
        if(ObjectUtils.isNotEmpty(sheet)) {
            sheet.setTimeSheetDate(request.getTimeSheetDate());
            sheet.setServiceId(request.getServiceId());
            sheet.setTotalHours(request.getTotalHours());
            sheet.setNotes(request.getNotes());
        }
        return sheet;
    }

    /**
     * Delete timesheet.
     * @param timeSheetId
     */
    public void deleteTimeSheet(UUID timeSheetId) {
        TimeSheet sheet = this.timeSheetRepository.findById(timeSheetId).orElseThrow(()->{
            throw new CommonException("Timesheet id not found");
        });
        sheet.setDeleted(true);
        timeSheetRepository.save(sheet);
        log.info("TimeSheet deleted for the Id",sheet.getId());
    }

    public TimeSheetResponse getTimeSheet(UUID timeSheetId) {
        TimeSheet sheet = this.timeSheetRepository.findById(timeSheetId).orElseThrow(()->{
            throw new CommonException("Timesheet id not found");
        });
        Employee employee = this.employeeRepository.findById(UUID.fromString(sheet.getEmployeeId())).orElseThrow(()->{
            throw new CommonException("Employee id not found");
        });
        Patient patient = this.patientRepository.findById(UUID.fromString(sheet.getClientId())).orElseThrow(()->{
            throw new CommonException("Patient id not found");
        });
        TimeSheetResponse response = new TimeSheetResponse();
        response.setTimeSheetDate(sheet.getTimeSheetDate());
        com.htsevv.entity.Service service = this.serviceRepository.findById(UUID.fromString(sheet.getServiceId())).orElseThrow(()->{
            throw new CommonException("Service Id not found");
        });
        response.setService(service.getName());
        response.setTotalHours(sheet.getTotalHours());
        response.setServiceId(sheet.getServiceId());
        response.setClientId(sheet.getClientId());
        response.setEmployeeId(sheet.getEmployeeId());
        response.setEmployeeName(employee.getUserName());
        response.setClientName(patient.getUserName());
        response.setNotes(sheet.getNotes());
        return response;
    }

    /**
     * Date filter
     *
     * @param timeSheetDate
     * @param fromTimeSheetDate
     * @param toTimeSheetDate
     * @return boolean
     */
    public boolean dateFilter(Date timeSheetDate,Date fromTimeSheetDate,Date toTimeSheetDate) {
        if(fromTimeSheetDate !=null && toTimeSheetDate!=null){
            Date toDate = dateUtil.convertToDateUsingInstant(dateUtil.convertToLocalDateViaInstant(toTimeSheetDate).plusDays(1));
            return timeSheetDate.after(fromTimeSheetDate) && timeSheetDate.before(toDate);
        } else {
            return true;
        }
    }

    public boolean employeeFilter(String employeeId,String employeeRequestId) {
        if(ObjectUtils.isNotEmpty(employeeRequestId)) {
            return employeeId.equals(employeeRequestId);
        } else {
            return true;
        }
    }

    public boolean clientFilter(String clientId,String clientRequestId) {
        if(ObjectUtils.isNotEmpty(clientRequestId)) {
            return clientId.equals(clientRequestId);
        } else {
            return true;
        }
    }
}
