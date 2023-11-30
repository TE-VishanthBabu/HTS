package com.htsevv.service;

import com.htsevv.entity.*;
import com.htsevv.exception.CommonException;
import com.htsevv.repository.*;
import com.htsevv.request.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HousingClientService {
    private final HousingClientRepository clientRepository;
    private final ClientTypeRepository typeRepository;
    private final ClientPharmacyRepository pharmacyRepository;
    private final ClientCaseManagerRepository caseManagerRepository;
    private final ClientDischargeCodeRepository codeRepository;
    private final ClientCareCoordinatorRepository coordinatorRepository;
    private final ModelMapper modelMapper;

    public HousingClient addHousingClient(HousingClientRequest request) {

        HousingClient housingClient = null;
        HousingClient client = new HousingClient();
     if(request.getId()!=null) {
        housingClient = this.clientRepository.findById(request.getId()).orElseThrow(()->{
            throw new CommonException("Client Id not found",HttpStatus.NOT_FOUND);
        });
        if(ObjectUtils.isNotEmpty(housingClient)) {
            client = housingClient;
        }else {
            throw new CommonException("User id is not found",HttpStatus.NOT_FOUND);
        }
     }
     client = this.setHousingClient(request,client);
     return clientRepository.save(client);
    }

    public HousingClient setHousingClient(HousingClientRequest clientRequest, HousingClient client) {
        client.setFirstName(clientRequest.getFirstName());
        client.setMiddleName(clientRequest.getMiddleName());
        client.setLastName(clientRequest.getLastName());
        client.setGender(clientRequest.getGender());
        client.setDateOfBirth(clientRequest.getDateOfBirth());
        client.setMedicaId(clientRequest.getMedicaId());
        client.setMedicare(clientRequest.getMedicare());
        client.setLanguages(clientRequest.getLanguages());
        client.setWaiverTYpe(clientRequest.getWaiverTYpe());
        client.setWeight(clientRequest.getWeight());
        client.setAka(clientRequest.getAka());
        client.setAge(clientRequest.getAge());
        client.setClientType(clientRequest.getClientType());
        client.setClientInsuranceId(clientRequest.getClientInsuranceId());
        client.setPcaType(clientRequest.getPcaType());
        client.setHeight(clientRequest.getHeight());
        client.setStreet(clientRequest.getStreet());
        client.setApt(clientRequest.getApt());
        client.setPhone1(clientRequest.getPhone1());
        client.setPhone2(clientRequest.getPhone2());
        client.setCity(clientRequest.getCity());
        client.setState(clientRequest.getState());
        client.setCountry(clientRequest.getCountry());
        client.setZipCode(clientRequest.getZipCode());
        client.setInternalId(clientRequest.getInternalId());
        client.setAssessmentRating(clientRequest.getAssessmentRating());
        client.setPriorityStatus(clientRequest.getPriorityStatus());
        client.setBillName(clientRequest.getBillName());
        client.setBillStreet(clientRequest.getBillStreet());
        client.setBillCity(clientRequest.getBillCity());
        client.setBillState(clientRequest.getBillState());
        client.setBillZipCode(clientRequest.getBillZipCode());
        client.setBillPhone(clientRequest.getBillPhone());
        client.setBillEmail(clientRequest.getBillEmail());
        client.setUserName(clientRequest.getUserName());
        client.setPassword(clientRequest.getPassword());
        client.setClientNotes(clientRequest.getClientNotes());
        client.setCertifications(clientRequest.getCertifications());
        client.setCareList(clientRequest.getCareList());
        client.setAuthorizations(clientRequest.getAuthorizations());
        client.setMedications(clientRequest.getMedications());
        if(clientRequest.getPharmacyId()!=null) {
            ClientPharmacy pharmacy = pharmacyRepository.findById(clientRequest.getPharmacyId()).orElseThrow(() -> {
                throw new CommonException("Pharmacy id not found", HttpStatus.NOT_FOUND);
            });
            client.setPharmacy(pharmacy);
        }
        client.setClientContacts(clientRequest.getClientContacts());
        List<ClientCaseManager> clientCaseManagers = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(clientRequest.getClientCaseManagers())) {
            for (UUID caseManagerId : clientRequest.getClientCaseManagers()) {
                ClientCaseManager caseManager = caseManagerRepository.findById(caseManagerId).orElseThrow(() -> {
                    throw new CommonException("CaseManagerId not found", HttpStatus.NOT_FOUND);
                });
                clientCaseManagers.add(caseManager);
            }
        }
        List<ClientCareCoordinator> clientCareCoordinators = new ArrayList<>();
        if(ObjectUtils.isNotEmpty(clientRequest.getClientCareCoordinator())) {
            for (UUID careCoordinatorId : clientRequest.getClientCareCoordinator()) {
                ClientCareCoordinator coordinator = coordinatorRepository.findById(careCoordinatorId).orElseThrow(() -> {
                    throw new CommonException("Care coordinator Id not found", HttpStatus.NOT_FOUND);
                });
                clientCareCoordinators.add(coordinator);
            }
        }
        client.setClientCaseManagers(clientCaseManagers);
        client.setClientCareCoordinators(clientCareCoordinators);
        client.setRelationships(clientRequest.getRelationships());
        return client;
    }

    public ClientTypeList addClientTypeList(ClientTypeListRequest typeList) {
       ClientTypeList clientType = modelMapper.map(typeList,ClientTypeList.class);
       return typeRepository.save(clientType);
    }

    public ClientTypeList updateClientType(UUID typeListId, ClientTypeListRequest request) {
        ClientTypeList clientType = typeRepository.findById(typeListId).orElseThrow(() -> {
            throw new CommonException("TaskList id not found",HttpStatus.NOT_FOUND);
        });
        clientType.setStatus(request.getStatus());
        clientType.setNumber(request.getNumber());
        clientType.setTypeName(request.getTypeName());
        return typeRepository.save(clientType);
    }

    public ClientTypeList getTypeList(UUID clientTypeId) {
        return typeRepository.findById(clientTypeId).orElseThrow(()->{
            throw new CommonException("TaskList id not found",HttpStatus.NOT_FOUND);
        });
    }

    public List<ClientTypeList> getAllTypeList() {
        return typeRepository.findAll();
    }

    public void deleteTypeList(UUID typeListId) {
        ClientTypeList typeList = typeRepository.findById(typeListId).orElseThrow(()->{
            throw new CommonException("TaskList id not found",HttpStatus.NOT_FOUND);
        });
        typeList.setDeleted(true);
        typeRepository.save(typeList);
        log.info("TaskList deleted successfully for the id:{}",typeList.getId());
    }

    public ClientPharmacy addNewPharmacy(ClientPharmacyRequest pharmacyRequest) {
        ClientPharmacy pharmacy = modelMapper.map(pharmacyRequest,ClientPharmacy.class);
        return pharmacyRepository.save(pharmacy);
    }

    public ClientPharmacy updatePharmacy(UUID pharmacyId,ClientPharmacyRequest pharmacyRequest) {
        ClientPharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElseThrow(()->{
            throw new CommonException("Pharmacy id not found",HttpStatus.NOT_FOUND);
        });
        pharmacy.setName(pharmacyRequest.getName());
        pharmacy.setStreet(pharmacyRequest.getStreet());
        pharmacy.setState(pharmacyRequest.getState());
        pharmacy.setCity(pharmacyRequest.getCity());
        pharmacy.setZipCode(pharmacyRequest.getZipCode());
        pharmacy.setNumber(pharmacyRequest.getNumber());
        return pharmacyRepository.save(pharmacy);
    }

    public List<ClientPharmacy> getAllPharmacy() {
        return pharmacyRepository.findAll();
    }

    public ClientPharmacy getPharmacyById(UUID pharmacyId) {
        return pharmacyRepository.findById(pharmacyId).orElseThrow(()->{
            throw new CommonException("Pharmacy Id not found", HttpStatus.NOT_FOUND);
        });
    }

    public ClientCaseManager addNewCaseManager(ClientCaseManagerRequest request) {
        ClientCaseManager caseManager = modelMapper.map(request,ClientCaseManager.class);
        return  caseManagerRepository.save(caseManager);
    }

    public ClientCaseManager updateCaseManager(UUID caseManagerId, ClientCaseManagerRequest request) {
        ClientCaseManager caseManager = caseManagerRepository.findById(caseManagerId).orElseThrow(()->{
            throw new CommonException("CaseManagerId not found",HttpStatus.NOT_FOUND);
        });
        caseManager.setName(request.getName());
        caseManager.setEmail(request.getEmail());
        caseManager.setFax(request.getFax());
        caseManager.setTelephone(request.getTelephone());
        caseManager.setAlternateFax(request.getAlternateFax());
        caseManager.setCountry(request.getCountry());
        return caseManagerRepository.save(caseManager);
    }

    public ClientCaseManager getCaseManagerById(UUID caseManagerId) {
        return caseManagerRepository.findById(caseManagerId).orElseThrow(()->{
            throw new CommonException("CaseManagerId not found",HttpStatus.NOT_FOUND);
        });
    }

    public List<ClientCaseManager> getAllCaseManager() {
        return caseManagerRepository.findAll();
    }

    public void deleteCaseManagerById(UUID caseManagerId) {
        ClientCaseManager caseManager = this.caseManagerRepository.findById(caseManagerId).orElseThrow(()->{
            throw new CommonException("CaseManager Id not found",HttpStatus.NOT_FOUND);
        });
        caseManager.setDeleted(true);
        caseManagerRepository.save(caseManager);
        log.info("CaseManager deleted successfully for the id:{}",caseManagerId);

    }
    public ClientDischargeCode addClientDischargeCode(ClientDischargeCodeRequest dischargeCodeRequest) {
        ClientDischargeCode dischargeCode = modelMapper.map(dischargeCodeRequest,ClientDischargeCode.class);
        return codeRepository.save(dischargeCode);
    }

    public ClientCareCoordinator addNewCareCoordinator(ClientCareCoordinatorRequest coordinatorRequest) {
        ClientCareCoordinator careCoordinator = modelMapper.map(coordinatorRequest,ClientCareCoordinator.class);
        return coordinatorRepository.save(careCoordinator);
    }

    public ClientCareCoordinator updateCareCoordinator(UUID careCoordinatorId,ClientCareCoordinatorRequest coordinatorRequest) {
        ClientCareCoordinator careCoordinator = coordinatorRepository.findById(careCoordinatorId).orElseThrow(()->{
            throw new CommonException("Care Coordinator id not found",HttpStatus.NOT_FOUND);
        });
        careCoordinator.setName(coordinatorRequest.getName());
        careCoordinator.setFax(coordinatorRequest.getFax());
        careCoordinator.setTelephone(coordinatorRequest.getTelephone());
        careCoordinator.setCountry(coordinatorRequest.getCountry());
        careCoordinator.setEmail(coordinatorRequest.getEmail());
        careCoordinator.setAlternateFax(coordinatorRequest.getAlternateFax());
        return coordinatorRepository.save(careCoordinator);
    }

    public List<ClientCareCoordinator> getAllCareCoordinator() {
        return coordinatorRepository.findAll();
    }

    public ClientCareCoordinator getCareCoordinatorById(UUID careCoordinatorId) {
        return this.coordinatorRepository.findById(careCoordinatorId).orElseThrow(()->{
            throw new CommonException("Care coordinator Id not found",HttpStatus.NOT_FOUND);
        });
    }
}
