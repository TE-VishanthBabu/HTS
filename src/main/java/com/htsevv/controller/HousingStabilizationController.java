package com.htsevv.controller;

import com.htsevv.entity.HousingCaseNotesQuestion;
import com.htsevv.entity.HousingCaseNotesTask;
import com.htsevv.entity.HousingStabilization;
import com.htsevv.request.HousingStabilizationFilterRequest;
import com.htsevv.request.HousingStabilizationRequest;
import com.htsevv.request.HousingCaseNotesQuestionRequest;
import com.htsevv.request.HousingCaseNotesTaskRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.response.HousingStabilizationResponse;
import com.htsevv.service.HousingStabilizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/housingStabilization")
@CrossOrigin(origins = "http://localhost:4200")
public class HousingStabilizationController {
    private final HousingStabilizationService housingStabilizationService;
    private final MessageSource messageSource;

    @PostMapping("")
    public ResponseEntity<CommonResponse> addHousingStabilization(@RequestBody HousingStabilizationRequest request) {
        CommonResponse response = new CommonResponse();
        response.setData(this.housingStabilizationService.addNewHousingStabilizationEntry(request));
        response.setMessage(messageSource.getMessage("add.houseStabilization",null, Locale.getDefault()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{housingStabilizationId}")
    public ResponseEntity<CommonResponse> updateHousingStabilization(@PathVariable UUID housingStabilizationId,
                                                                     @RequestBody HousingStabilizationRequest request) {
        HousingStabilization stabilization = this.housingStabilizationService.updateHousingStabilizationEntry(housingStabilizationId,request);
        CommonResponse response = new CommonResponse();
        response.setData(stabilization);
        response.setMessage(messageSource.getMessage("update.housingStabilization",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{housingStabilizationId}")
    public ResponseEntity<CommonResponse> deleteHousingStabilization(@PathVariable UUID housingStabilizationId) {
        this.housingStabilizationService.deleteHousingStabilizationEntry(housingStabilizationId);
        CommonResponse response = new CommonResponse();
        response.setMessage(messageSource.getMessage("delete.housingStabilization",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/caseNotes/task")
    public ResponseEntity<CommonResponse> addCaseNotesTask(@RequestBody HousingCaseNotesTaskRequest request) {
        HousingCaseNotesTask caseNotesTask = this.housingStabilizationService.addCaseNotesTask(request);
        CommonResponse response = new CommonResponse();
        response.setData(caseNotesTask);
        response.setMessage(messageSource.getMessage("add.caseNotesTask",null,Locale.getDefault()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/caseNotes/question")
    public ResponseEntity<CommonResponse> addCaseNotesQuestion(@RequestBody HousingCaseNotesQuestionRequest questionRequest) {
       HousingCaseNotesQuestion notesQuestion = this.housingStabilizationService.addCaseNotesQuestion(questionRequest);
       CommonResponse response = new CommonResponse();
       response.setData(notesQuestion);
       response.setMessage(messageSource.getMessage("add.caseNotesQuestion",null,Locale.getDefault()));
       return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/caseNotes/task/{caseNotesTaskId}")
    public ResponseEntity<CommonResponse> updateCaseNotesTask(@PathVariable UUID caseNotesTaskId,@RequestBody HousingCaseNotesTaskRequest request) {
        HousingCaseNotesTask caseNotesTask = this.housingStabilizationService.updateCaseNotesTask(caseNotesTaskId,request);
        CommonResponse response = new CommonResponse();
        response.setData(caseNotesTask);
        response.setMessage(messageSource.getMessage("update.caseNotesTask",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/caseNotes/question/{caseNotesQuestionId}")
    public ResponseEntity<CommonResponse> updateCaseNotesQuestion(@PathVariable UUID caseNotesQuestionId,@RequestBody HousingCaseNotesQuestionRequest questionRequest) {
        HousingCaseNotesQuestion housingCaseNotesQuestion = this.housingStabilizationService.updateCaseNotesQuestion(caseNotesQuestionId,questionRequest);
        CommonResponse response = new CommonResponse();
        response.setData(housingCaseNotesQuestion);
        response.setMessage(messageSource.getMessage("update.caseNotesQuestion",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/caseNotes/task")
    public ResponseEntity<CommonResponse> getCaseNotesTask() {
       List<HousingCaseNotesTask> caseNotesTask = this.housingStabilizationService.getAllCaseNotesTask();
       CommonResponse response = new CommonResponse();
       response.setData(caseNotesTask);
       response.setMessage(messageSource.getMessage("get.caseNotesTasks",null,Locale.getDefault()));
       return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/caseNotes/task/{taskId}")
    public ResponseEntity<CommonResponse> getCaseNotesTaskById(@PathVariable UUID taskId) {
        CommonResponse response = new CommonResponse();
        response.setData(this.housingStabilizationService.getCaseNotesTaskById(taskId));
        response.setMessage(messageSource.getMessage("get.caseTask",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/caseNotes/question")
    public ResponseEntity<CommonResponse> getAllCaseNotesQuestion() {
        List<HousingCaseNotesQuestion> caseNotesQuestions = this.housingStabilizationService.getAllCaseNotesQuestions();
        CommonResponse response = new CommonResponse();
        response.setData(caseNotesQuestions);
        response.setMessage(messageSource.getMessage("get.caseNotesQuestions",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/caseNotes/question/{questionId}")
    public ResponseEntity<CommonResponse> getCaseNotesQuestion(@PathVariable UUID questionId) {
        CommonResponse response = new CommonResponse();
        response.setData( this.housingStabilizationService.getCaseNotesQuestionById(questionId));
        response.setMessage(messageSource.getMessage("get.caseQuestion",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<CommonResponse> getAllHousingStabilization(@RequestBody(required = false) HousingStabilizationFilterRequest request) {
        List<HousingStabilizationResponse> stabilizationResponses = this.housingStabilizationService.getAllHousingStabilization(request);
        CommonResponse response = new CommonResponse();
        response.setData(stabilizationResponses);
        response.setMessage(messageSource.getMessage("getAll.housingStabilization",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
