package com.htsevv.controller;

import com.htsevv.entity.EvvVisit;
import com.htsevv.exception.IncorrectServiceDateException;
import com.htsevv.exception.RejectionNotesRequiredException;
import com.htsevv.exception.VisitStartTimeMissingException;
import com.htsevv.request.EvvVisitRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.service.EvvService;
import com.htsevv.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class EvvController {

    private final EvvService evvService;

    @Autowired
    private MessageSource messageSource;


    /**
     * Add new visit information.
     *
     * @param evvVisitRequest
     * @return Added visit information
     */
    @PostMapping
    public ResponseEntity<CommonResponse> addNewVisit(@Valid @RequestBody EvvVisitRequest evvVisitRequest){
        if(ObjectUtils.isEmpty(evvVisitRequest.getStartTime())){
            throw new VisitStartTimeMissingException();
        } else if(ObjectUtils.isEmpty(evvVisitRequest.getStartTime()) && ObjectUtils.isNotEmpty(evvVisitRequest.getEndTime())){
            throw new VisitStartTimeMissingException();
        }
        if(!DateUtils.isSameDay(DateUtil.getDateOnly(evvVisitRequest.getDateOfService()),
                DateUtil.getDateOnly(new Date()))){
            throw new IncorrectServiceDateException();
        }
        EvvVisit visit = this.evvService.addNewVisit(evvVisitRequest);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(visit);
        commonResponse.setMessage(messageSource.getMessage("add.visit",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Update visit.
     *
     * @param evvVisitRequest
     * @param visitId
     * @return updated visit information
     */
    @PutMapping("/{visitId}")
    public ResponseEntity<CommonResponse> updateVisit(@Valid @RequestBody EvvVisitRequest evvVisitRequest,
                                                      @PathVariable UUID visitId){
        if(ObjectUtils.isEmpty(evvVisitRequest.getStartTime()) && ObjectUtils.isNotEmpty(evvVisitRequest.getEndTime())){
            throw new VisitStartTimeMissingException();
        }
        if(evvVisitRequest.getStatus() != null
                && EvvVisit.Status.valueOf(evvVisitRequest.getStatus()).equals(EvvVisit.Status.REJECTED) &&
        ObjectUtils.isEmpty(evvVisitRequest.getNotes())){
            throw new RejectionNotesRequiredException();
        }
        EvvVisit visit = this.evvService.updateVisit(evvVisitRequest, visitId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(visit);
        commonResponse.setMessage(messageSource.getMessage("update.visit",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get visit by visitId.
     *
     * @param visitId
     * @return visit
     */
    @GetMapping("/{visitId}")
    public ResponseEntity<CommonResponse> getVisit(@PathVariable UUID visitId){
        EvvVisit visit = this.evvService.getVisit(visitId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(visit);
        commonResponse.setMessage("Visit details of " + visitId);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get visit by status
     *
     * @param status
     * @param startDate
     * @param endDate
     * @return visit by status
     */
    @GetMapping()
    public ResponseEntity<CommonResponse> getVisits(@RequestParam(required = false) String status,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                                                    @RequestParam(required = false) boolean deleted){
        List<EvvVisit> visits = this.evvService.getVisits(status, startDate, endDate, deleted);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(visits);
        commonResponse.setMessage(messageSource.getMessage("visits.getAll",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get top requested services.
     *
     * @param startDate
     * @param endDate
     * @return top requested services
     */
    @GetMapping("/top-requested")
    public ResponseEntity<CommonResponse> getTopRequestedServices(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                                  @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate){
        List<Map<String, String>> requestedServiceResponses = this.evvService.getTopRequestedServices(startDate, endDate);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(requestedServiceResponses);
        commonResponse.setMessage(messageSource.getMessage("topService.visit",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<CommonResponse> deleteVisit(@PathVariable UUID id){
        this.evvService.deleteVisit(id);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage(messageSource.getMessage("visit.deleted",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
