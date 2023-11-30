package com.htsevv.service;

import com.htsevv.entity.EvvVisit;
import com.htsevv.exception.CommonException;
import com.htsevv.exception.VisitAlreadyApprovedException;
import com.htsevv.exception.VisitNotFoundException;
import com.htsevv.repository.EvvVisitRepository;
import com.htsevv.request.EvvVisitRequest;
import com.htsevv.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EvvService {
    private final EvvVisitRepository evvVisitRepository;
    private final EmployeeService employeeService;
    private final PatientService patientService;
    private final LocationService locationService;

    /**
     * Adding new visit.
     *
     * @param evvVisitRequest
     * @return New visit
     */
    @SneakyThrows
    public EvvVisit addNewVisit(EvvVisitRequest evvVisitRequest) {
        EvvVisit evvVisit = new EvvVisit();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        evvVisit.setServiceName(evvVisitRequest.getServiceName());
        evvVisit.setDateOfService(evvVisitRequest.getDateOfService());
        try{
            LocalDateTime startTime = this.convertToLocalTime(simpleDateFormat.parse(evvVisitRequest.getStartTime()));
            evvVisit.setStartTime(startTime);
            if(ObjectUtils.isNotEmpty(evvVisitRequest.getEndTime())){
                LocalDateTime endTime = this.convertToLocalTime(simpleDateFormat.parse(evvVisitRequest.getEndTime()));
                evvVisit.setEndTime(endTime);
                evvVisit.setTimeDuration(this.timeDiff(startTime, endTime));
            }
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw new CommonException(e.getLocalizedMessage());
        }
        evvVisit.setStatus(EvvVisit.Status.PENDING);
        evvVisit.setNotes(evvVisitRequest.getNotes());
        evvVisit.setProvider(employeeService.getEmployee(evvVisitRequest.getProviderId()));
        evvVisit.setPatient(patientService.getPatient(evvVisitRequest.getPatientId()));
        evvVisit.setLocation(locationService.getLocation(evvVisitRequest.getLocationId()));
        return this.evvVisitRepository.save(evvVisit);
    }

    @SneakyThrows
    private LocalDateTime timeDiff(LocalDateTime startTime, LocalDateTime endTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        LocalDateTime tempDateTime = LocalDateTime.from( startTime );
        long hours = tempDateTime.until( endTime, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );
        long minutes = tempDateTime.until( endTime, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );
        return this.convertToLocalTime(simpleDateFormat.parse(hours + ":" + minutes));
    }

    public LocalDateTime convertToLocalTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Get visit by visitId.
     *
     * @param visitId
     * @return visit info by visitId
     */
    public EvvVisit getVisit(UUID visitId){
        return this.evvVisitRepository.findById(visitId).orElseThrow(()->{
            throw new VisitNotFoundException();
        });
    }

    /**
     * Updating visit.
     *
     * @param evvVisitRequest
     * @param visitId
     * @return updated visit
     */
    @SneakyThrows
    public EvvVisit updateVisit(EvvVisitRequest evvVisitRequest, UUID visitId) {
        EvvVisit evvVisit = this.getVisit(visitId);
        if(ObjectUtils.isNotEmpty(evvVisit.getStatus()) &&
                evvVisit.getStatus().equals(EvvVisit.Status.ACCEPTED)){
            throw new VisitAlreadyApprovedException();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        evvVisit.setServiceName(evvVisitRequest.getServiceName());
        evvVisit.setDateOfService(evvVisitRequest.getDateOfService());
        try{
            LocalDateTime startTime = this.convertToLocalTime(simpleDateFormat.parse(evvVisitRequest.getStartTime()));
            evvVisit.setStartTime(startTime);
            if(ObjectUtils.isNotEmpty(evvVisitRequest.getEndTime())){
                LocalDateTime endTime = this.convertToLocalTime(simpleDateFormat.parse(evvVisitRequest.getEndTime()));
                evvVisit.setEndTime(endTime);
                evvVisit.setTimeDuration(this.timeDiff(startTime, endTime));
            }
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw new CommonException(e.getLocalizedMessage());
        }
        if(ObjectUtils.isNotEmpty(evvVisitRequest.getStatus()))
            evvVisit.setStatus(EvvVisit.Status.valueOf(evvVisitRequest.getStatus()));
        evvVisit.setLocation(locationService.getLocation(evvVisitRequest.getLocationId()));
        evvVisit.setNotes(evvVisitRequest.getNotes());
        return this.evvVisitRepository.save(evvVisit);
    }

    /**
     * Get visit by visit status.
     *
     * @param status
     * @param startDate
     * @param endDate
     * @return visit information.
     */
    public List<EvvVisit> getVisits(String status, Date startDate, Date endDate, boolean deleted) {
        if (StringUtils.isNotEmpty(status) && ObjectUtils.isNotEmpty(startDate)
                && ObjectUtils.isNotEmpty(endDate)){
            return this.evvVisitRepository.findByStatusAndLastModificationDateBetween(
                    EvvVisit.Status.valueOf(status), startDate, DateUtil.getEndDate(endDate));
        } else if(StringUtils.isNotEmpty(status)){
            return this.evvVisitRepository.findByStatus(EvvVisit.Status.valueOf(status));
        } else if(BooleanUtils.isTrue(deleted)){
            return this.evvVisitRepository.findByDeleted(deleted);
        } else {
            return this.evvVisitRepository.findAll();
        }
    }

    /**
     * Getting top requested services.
     *
     * @param startDate
     * @param endDate
     * @return top requested services
     */
    public List<Map<String, String>> getTopRequestedServices(Date startDate, Date endDate) {
        return this.evvVisitRepository.getTopRequestedServices(startDate, DateUtil.getEndDate(endDate));
    }

    public void deleteVisit(UUID id) {
        EvvVisit evvVisit = this.evvVisitRepository.findById(id).orElseThrow(()->{
            throw new VisitNotFoundException();
        });
        evvVisit.setDeleted(true);
        evvVisitRepository.save(evvVisit);
    }
}
