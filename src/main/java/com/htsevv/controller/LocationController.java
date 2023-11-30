package com.htsevv.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htsevv.entity.Location;
import com.htsevv.request.LocationRequest;
import com.htsevv.response.CommonResponse;
import com.htsevv.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    private MessageSource messageSource;

    /**
     * Create new location
     *
     * @param locationRequest
     * @param locationPhoto
     * @return created location details
     */
    @PostMapping("")
    public ResponseEntity<CommonResponse> addNewLocation(@RequestPart String locationRequest,
                                                         @RequestPart MultipartFile locationPhoto,
                                                         @RequestPart MultipartFile profilePhoto) {
//        TODO validate location exists
        Type collectionType = new TypeToken<LocationRequest>() {
        }.getType();
        Gson g = new Gson();
        LocationRequest request = g.fromJson(locationRequest, collectionType);
        Location location = this.locationService.addNewLocation(request, locationPhoto,profilePhoto);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(location);
        commonResponse.setMessage(messageSource.getMessage("location.add",null, Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get all locations
     *
     * @return list of locations
     */
    @GetMapping("")
    public ResponseEntity<CommonResponse> getAllLocations() {
        List<Location> locationList = this.locationService.getAllLocations();
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(locationList);
        commonResponse.setMessage(messageSource.getMessage("locations.getAll",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    /**
     * Get location by location id
     *
     * @param locationId
     * @return location details
     */
    @GetMapping("/{locationId}")
    public ResponseEntity<CommonResponse> getLocation(@PathVariable UUID locationId) {
        Location location = this.locationService.getLocation(locationId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(location);
        commonResponse.setMessage("Details of location " + locationId);
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<CommonResponse> updateLocation(@RequestPart String locationRequest,
                                                         @RequestPart(required = false) MultipartFile locationPhoto,
                                                         @RequestPart(required = false) MultipartFile profilePhoto,
                                                         @RequestParam String photoUrl,
                                                         @RequestPart String locationUrl,
                                                         @PathVariable UUID locationId) {
        Type collectionType = new TypeToken<LocationRequest>() {
        }.getType();
        Gson g = new Gson();
        LocationRequest request = g.fromJson(locationRequest, collectionType);
        Location location = this.locationService.updateLocation(request, locationPhoto, profilePhoto, photoUrl, locationUrl,locationId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(location);
        commonResponse.setMessage(messageSource.getMessage("location.update",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<CommonResponse> deleteLocation(@PathVariable UUID locationId) {
        this.locationService.deleteLocation(locationId);
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setMessage(messageSource.getMessage("location.delete",null,Locale.getDefault()));
        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }
}
