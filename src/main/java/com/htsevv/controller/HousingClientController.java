package com.htsevv.controller;

import com.htsevv.entity.*;
import com.htsevv.request.*;
import com.htsevv.response.CommonResponse;
import com.htsevv.service.HousingClientService;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/housingClient")
@CrossOrigin(origins = "http://localhost:4200")
public class HousingClientController {
    private final HousingClientService clientService;
    private final MessageSource messageSource;

    @PostMapping("")
    public ResponseEntity<CommonResponse> addHousingClient(@RequestBody HousingClientRequest request) {
       HousingClient housingClient = clientService.addHousingClient(request);
       CommonResponse response = new CommonResponse();
       response.setData(housingClient);
       response.setMessage(messageSource.getMessage("add.housingClient",null, Locale.getDefault()));
       return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/typeList")
    public ResponseEntity<CommonResponse> addTypeList(@RequestBody ClientTypeListRequest typeList) {
        ClientTypeList clientType = clientService.addClientTypeList(typeList);
        CommonResponse response = new CommonResponse();
        response.setData(clientType);
        response.setMessage(messageSource.getMessage("add.type",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/typeList/{typeListId}")
    public ResponseEntity<CommonResponse> updateTypeList(@PathVariable UUID typeListId, @RequestBody ClientTypeListRequest typeListRequest) {
        ClientTypeList clientType = clientService.updateClientType(typeListId, typeListRequest);
        CommonResponse response = new CommonResponse();
        response.setData(clientType);
        response.setMessage(messageSource.getMessage("update.type",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/typeList/{typeListId}")
    public ResponseEntity<CommonResponse> getTypeList(@PathVariable UUID typeListId) {
        CommonResponse response = new CommonResponse();
        response.setData(clientService.getTypeList(typeListId));
        response.setMessage(messageSource.getMessage("get.type",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/typeList")
    public ResponseEntity<CommonResponse> getTypeList() {
        CommonResponse response = new CommonResponse();
        response.setData(clientService.getAllTypeList());
        response.setMessage(messageSource.getMessage("getAll.type",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/typeList/{typeListId}")
    public ResponseEntity<CommonResponse> deleteTypeList(@PathVariable UUID typeListId) {
        clientService.deleteTypeList(typeListId);
        CommonResponse response = new CommonResponse();
        response.setMessage(messageSource.getMessage("delete.typeList",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/pharmacy")
    public ResponseEntity<CommonResponse> addPharmacy(@RequestBody ClientPharmacyRequest pharmacy) {
        ClientPharmacy newPharmacy = clientService.addNewPharmacy(pharmacy);
        log.info("Created new pharmacy");
        CommonResponse response = new CommonResponse();
        response.setData(newPharmacy);
        response.setMessage(messageSource.getMessage("add.pharmacy",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<CommonResponse> updatePharmacy(@PathVariable UUID pharmacyId,@RequestBody ClientPharmacyRequest request) {
        ClientPharmacy pharmacy = clientService.updatePharmacy(pharmacyId,request);
        log.info("Updated pharmacy for the id:{}",pharmacy.getId());
        CommonResponse response = new CommonResponse();
        response.setData(pharmacy);
        response.setMessage(messageSource.getMessage("update.pharmacy",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/pharmacy")
    public ResponseEntity<CommonResponse> getAllPharmacy() {
        List<ClientPharmacy> pharmacies = clientService.getAllPharmacy();
        log.info("Gathered all pharmacy details");
        CommonResponse response = new CommonResponse();
        response.setData(pharmacies);
        response.setMessage(messageSource.getMessage("getAll.pharmacy",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<CommonResponse> getPharmacy(@PathVariable UUID pharmacyId) {
        ClientPharmacy pharmacy = clientService.getPharmacyById(pharmacyId);
        log.info("Gathered details of pharmacy for the id: {}",pharmacy.getId());
        CommonResponse response = new CommonResponse();
        response.setData(pharmacy);
        response.setMessage(messageSource.getMessage("get.pharmacy",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/caseManager")
    public ResponseEntity<CommonResponse> addCaseManager(@RequestBody ClientCaseManagerRequest request) {
        ClientCaseManager caseManager = clientService.addNewCaseManager(request);
        log.info("Created new CaseManager");
        CommonResponse response = new CommonResponse();
        response.setData(caseManager);
        response.setMessage(messageSource.getMessage("add.caseManager",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/caseManager/{caseManagerId}")
    public ResponseEntity<CommonResponse> updateCaseManager(@PathVariable UUID caseManagerId,@RequestBody ClientCaseManagerRequest request) {
        ClientCaseManager caseManager = clientService.updateCaseManager(caseManagerId,request);
        log.info("Updated case manager");
        CommonResponse response = new CommonResponse();
        response.setData(caseManager);
        response.setMessage(messageSource.getMessage("update.caseManager",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/caseManager/{caseManagerId}")
    public ResponseEntity<CommonResponse> getCaseManager(@PathVariable UUID caseManagerId) {
        ClientCaseManager clientCaseManager = clientService.getCaseManagerById(caseManagerId);
        log.info("Gathered details of case manager for the id: {}",clientCaseManager.getId());
        CommonResponse response = new CommonResponse();
        response.setData(clientCaseManager);
        response.setMessage(messageSource.getMessage("get.caseManager",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/caseManager")
    public ResponseEntity<CommonResponse> getAllCaseManager() {
        List<ClientCaseManager> clientCaseManagers = clientService.getAllCaseManager();
        log.info("Gathered all details of case manager");
        CommonResponse response = new CommonResponse();
        response.setData(clientCaseManagers);
        response.setMessage(messageSource.getMessage("getAll.caseManager",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/caseManager/{caseManagerId}")
    public ResponseEntity<CommonResponse> deleteCaseManager(@PathVariable UUID caseManagerId) {
        clientService.deleteCaseManagerById(caseManagerId);
        CommonResponse response = new CommonResponse();
        response.setMessage(messageSource.getMessage("delete.caseManager",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/dischargeCode")
    public ResponseEntity<CommonResponse> addDischargeCode(@RequestBody ClientDischargeCodeRequest dischargeCodeRequest) {
        ClientDischargeCode dischargeCode = clientService.addClientDischargeCode(dischargeCodeRequest);
        log.info("Added new discharge code",HttpStatus.OK);
        CommonResponse response = new CommonResponse();
        response.setData(dischargeCode);
        response.setMessage(messageSource.getMessage("add.dischargeCode",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/care-coordinator")
    public ResponseEntity<CommonResponse> addCareCoordinator(@RequestBody ClientCareCoordinatorRequest coordinatorRequest) {
        ClientCareCoordinator careCoordinator = this.clientService.addNewCareCoordinator(coordinatorRequest);
        log.info("Created new care coordinator id:{}",careCoordinator.getId());
        CommonResponse response = new CommonResponse();
        response.setData(careCoordinator);
        response.setMessage(messageSource.getMessage("add.careCord",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/care-coordinator/{coordinatorId}")
    public ResponseEntity<CommonResponse> updateCareCoordinator(@PathVariable UUID coordinatorId,@RequestBody ClientCareCoordinatorRequest coordinatorRequest) {
       ClientCareCoordinator coordinator = this.clientService.updateCareCoordinator(coordinatorId,coordinatorRequest);
       log.info("Updated care coordinator for the id: {}",coordinator.getId());
       CommonResponse response = new CommonResponse();
       response.setData(coordinator);
       response.setMessage(messageSource.getMessage("update.careCoordinator",null,Locale.getDefault()));
       return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/care-coordinator")
    public ResponseEntity<CommonResponse> getAllCareCoordinator() {
        List<ClientCareCoordinator> careCoordinator = this.clientService.getAllCareCoordinator();
        log.info("Gathered all details of get all care coordinator");
        CommonResponse response = new CommonResponse();
        response.setData(careCoordinator);
        response.setMessage(messageSource.getMessage("getAll.careCoordinator",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/care-coordinator/{coordinatorId}")
    public ResponseEntity<CommonResponse> getCareCoordinator(@PathVariable UUID coordinatorId) {
        ClientCareCoordinator careCoordinator = this.clientService.getCareCoordinatorById(coordinatorId);
        log.info("Gathered all details of care coordinator id:{}",coordinatorId);
        CommonResponse response = new CommonResponse();
        response.setData(careCoordinator);
        response.setMessage(messageSource.getMessage("get.careCoordinator",null,Locale.getDefault()));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
