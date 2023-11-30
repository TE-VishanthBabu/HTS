package com.htsevv.service;

import com.htsevv.entity.*;
import com.htsevv.exception.CommonException;
import com.htsevv.repository.*;
import com.htsevv.request.HousingStabilizationFilterRequest;
import com.htsevv.request.HousingStabilizationRequest;
import com.htsevv.request.HousingCaseNotesQuestionRequest;
import com.htsevv.request.HousingCaseNotesTaskRequest;
import com.htsevv.response.HousingStabilizationResponse;
import com.htsevv.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HousingStabilizationService {

    private final HousingStabilizationRepository stabilizationRepository;
    private final HousingCaseNotesTaskRepository taskRepository;
    private final HousingCaseNotesQuestionRepository questionRepository;
    private final EmployeeRepository employeeRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final DateUtil dateUtil;

    public HousingStabilization addNewHousingStabilizationEntry(HousingStabilizationRequest request) {
       HousingStabilization housingStabilization = modelMapper.map(request,HousingStabilization.class);
       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String formattedStartDate = dateFormat.format(request.getStartDate());
           Date startDate = dateFormat.parse(formattedStartDate);
           String formattedEndDate = dateFormat.format(request.getEndDate());
           Date endDate = dateFormat.parse(formattedEndDate);
           housingStabilization.setContactType(request.getContactType());
           housingStabilization.setStartDate(startDate);
           housingStabilization.setEndDate(endDate);
           return stabilizationRepository.save(housingStabilization);
       } catch (Exception e) {
           throw new CommonException(e.getMessage());
       }
    }

    public HousingStabilization updateHousingStabilizationEntry(UUID housingStabilizationId, HousingStabilizationRequest request) {
        HousingStabilization housingStabilization = this.stabilizationRepository.findById(housingStabilizationId).orElseThrow(()->{
            throw new CommonException("Housing stabilization entry ID is not found");
        });
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedStartDate = dateFormat.format(request.getStartDate());
            Date startDate = dateFormat.parse(formattedStartDate);
            String formattedEndDate = dateFormat.format(request.getEndDate());
            Date endDate = dateFormat.parse(formattedEndDate);
            housingStabilization.setContactType(request.getContactType());
            housingStabilization.setStartDate(startDate);
            housingStabilization.setEndDate(endDate);
            housingStabilization.setHours(request.getHours());
            housingStabilization.setUnits(request.getUnits());
            housingStabilization.setServiceCode(request.getServiceCode());
            housingStabilization.setServiceDescription(request.getServiceDescription());
            housingStabilization.setTask(request.getTask());
            housingStabilization.setTaskDescription(request.getTaskDescription());
            housingStabilization.setNotes(request.getNotes());
            return housingStabilization;
        }catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    public void deleteHousingStabilizationEntry(UUID housingStabilizationId) {
        HousingStabilization stabilization = this.stabilizationRepository.findById(housingStabilizationId).orElseThrow(()->{
            throw new CommonException("Housing stabilization entry ID is not found");
        });
        stabilization.setDeleted(true);
        stabilizationRepository.save(stabilization);
        log.info("Deleted housing stabilization entry: {}",stabilization.getId());
    }

    public HousingCaseNotesTask addCaseNotesTask(HousingCaseNotesTaskRequest request) {
        HousingCaseNotesTask caseNotesTask = modelMapper.map(request,HousingCaseNotesTask.class);
        return taskRepository.save(caseNotesTask);
    }

    public HousingCaseNotesQuestion addCaseNotesQuestion(HousingCaseNotesQuestionRequest questionRequest) {
        HousingCaseNotesQuestion question = modelMapper.map(questionRequest,HousingCaseNotesQuestion.class);
        return questionRepository.save(question);
    }

    public HousingCaseNotesTask updateCaseNotesTask(UUID housingStabilizationId,HousingCaseNotesTaskRequest request) {
        HousingCaseNotesTask caseNotesTask = taskRepository.findById(housingStabilizationId).orElseThrow(()->{
            throw new CommonException("Housing case notes task is not found");
        });
        caseNotesTask.setTask(request.getTask());
        caseNotesTask.setTaskDescription(request.getTaskDescription());
        return taskRepository.save(caseNotesTask);
    }

    public HousingCaseNotesQuestion updateCaseNotesQuestion(UUID housingStabilizationId, HousingCaseNotesQuestionRequest request) {
        HousingCaseNotesQuestion housingCaseNotesQuestion = questionRepository.findById(housingStabilizationId).orElseThrow(()->{
            throw new CommonException("Housing case notes question is not found");
        });
        housingCaseNotesQuestion.setQuestion(request.getQuestion());
        return questionRepository.save(housingCaseNotesQuestion);
    }

    public List<HousingCaseNotesTask> getAllCaseNotesTask() {
        return this.taskRepository.findAll();
    }

    public HousingCaseNotesTask getCaseNotesTaskById(UUID taskId) {
        return this.taskRepository.findById(taskId).orElseThrow(()->{
            throw new CommonException("Task Id not found");
        });
    }

    public List<HousingCaseNotesQuestion> getAllCaseNotesQuestions(){
        return this.questionRepository.findAll();
    }

    public HousingCaseNotesQuestion getCaseNotesQuestionById(UUID questionId){
        return this.questionRepository.findById(questionId).orElseThrow(()->{
            throw new CommonException("Case notes question id not found");
        });
    }
    public List<HousingStabilizationResponse> getAllHousingStabilization(HousingStabilizationFilterRequest request) {
        List<HousingStabilization> housingStabilization = stabilizationRepository.findAll().stream()
                .filter(housing -> this.dateFilter(housing.getStartDate(),housing.getEndDate(),request.getStartDate(),request.getEndDate()))
                .filter(housing -> this.employeeFilter(housing.getEmployeeId(),request.getEmployeeId()))
                .filter(housing -> this.clientFilter(housing.getClientId(),request.getClientId()))
                .filter(housing -> this.contactTypeFilter(housing.getContactType(),request.getContactType())).collect(Collectors.toList());

        return this.mapHousingStabilizationToHousingStabilizationResponse(housingStabilization);
    }

    public List<HousingStabilizationResponse> mapHousingStabilizationToHousingStabilizationResponse(List<HousingStabilization> housingStabilization) {
        List<HousingStabilizationResponse> stabilizationResponses = new ArrayList<>();
        housingStabilization.forEach(stabilization -> {
            HousingStabilizationResponse response = new HousingStabilizationResponse();
            Employee employee = employeeRepository.findById(UUID.fromString(stabilization.getEmployeeId())).orElseThrow(()->{
                throw new CommonException("Employee id not found");
            });
            Patient patient = patientRepository.findById(UUID.fromString(stabilization.getClientId())).orElseThrow(()->{
                throw new CommonException("Client id not found");
            });
            response = modelMapper.map(stabilization,HousingStabilizationResponse.class);
            response.setEmployeeName(employee.getUserName());
            response.setClientName(patient.getUserName());
            response.setExported(false);
            stabilizationResponses.add(response);
        });
        return stabilizationResponses;
    }

    public boolean dateFilter(Date housingStabilizationStartDate,
                              Date housingStabilizationEndDate,
                              Date requestStartDate,
                              Date requestEndDate) {
        if(requestStartDate !=null && requestEndDate!=null) {
            Date toDate = dateUtil.convertToDateUsingInstant(dateUtil.convertToLocalDateViaInstant(requestEndDate).plusDays(1));
            return housingStabilizationStartDate.after(requestStartDate) && housingStabilizationEndDate.before(toDate);
        } else {
            return true;
        }
    }
    public boolean employeeFilter(String employeeId,String requestEmployeeId) {
        if(ObjectUtils.isNotEmpty(requestEmployeeId)) {
            return employeeId.equals(requestEmployeeId);
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

    public boolean contactTypeFilter(String contactType,String contactTypeRequest) {
        if(ObjectUtils.isNotEmpty(contactTypeRequest)) {
            return contactType.equals(contactTypeRequest);
        } else {
            return true;
        }
    }
}
