package com.htsevv.service;

import com.htsevv.entity.Location;
import com.htsevv.exception.LocationNotFoundException;
import com.htsevv.repository.LocationRepository;
import com.htsevv.request.LocationRequest;
import com.htsevv.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
@Validated
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationRepository locationRepository;
    private final FileStorageService fileStorageService;
    private final ValidationUtils validationUtils;

    /**
     * Add new location.
     *
     * @param request
     * @param locationPhoto
     * @return Created location details
     */
    public Location addNewLocation(@Valid LocationRequest request, MultipartFile locationPhoto, MultipartFile profilePhoto) {
        log.info("Adding new location");
        Location location = new Location(request);
        location = this.locationRepository.save(location);
        validationUtils.validateUploadPhoto(locationPhoto);
        validationUtils.validateUploadPhoto(profilePhoto);
        String fileName = fileStorageService.storeFile(locationPhoto,  "location_" + location.getId());
        String photoFile = fileStorageService.storeFile(profilePhoto,  "profile_" + location.getId());
        location.setLocationPhoto(fileName);
        location.setPhoto(photoFile);
        return this.locationRepository.save(location);
    }

    /**
     * Get all locations.
     *
     * @return list of locations
     */
    public List<Location> getAllLocations() {
        log.info("Getting all locations");
        return this.locationRepository.findAll(Sort.by(Sort.Direction.DESC, "creationDate"));
    }

    /**
     * Get location by location id
     *
     * @param locationId
     * @return Location details
     */
    public Location getLocation(UUID locationId) {
        log.info("Get location: {}", locationId);
        return locationRepository.findById(locationId).orElseThrow(() -> {
            throw new LocationNotFoundException();
        });
    }

    /**
     * Update Location with given details.
     *
     * @param request
     * @param locationPhoto
     * @param locationId
     * @return Updated location details
     */
    public Location updateLocation(@Valid LocationRequest request, MultipartFile locationPhoto, MultipartFile profilePhoto, String photoUrl, String locationUrl, UUID locationId) {
        log.info("Updating location with id {}", locationId);
        Location location = this.getLocation(locationId);
        location.setLocationName(request.getLocationName());
        location.setAddress(request.getAddress());
        location.setManagerName(request.getManagerName());
        location.setManagerMobileNumber(request.getManagerMobile());
        location.setManagerEmail(request.getManagerEmail());
        if (ObjectUtils.isNotEmpty(locationPhoto) && locationPhoto!=null) {
            validationUtils.validateUploadPhoto(locationPhoto);
            location.setLocationPhoto(fileStorageService.storeFile(locationPhoto,  "location_" + location.getId()));
        } else {
            location.setLocationPhoto(locationUrl);
        }
        if(ObjectUtils.isNotEmpty(profilePhoto) && profilePhoto!=null) {
            validationUtils.validateUploadPhoto(profilePhoto);
            location.setPhoto(fileStorageService.storeFile(profilePhoto,  "profile_" + location.getId()));
        } else {
            location.setPhoto(photoUrl);
        }
        return locationRepository.save(location);
    }

    /**
     * Delete location by id
     *
     * @param locationId
     */
    public void deleteLocation(UUID locationId) {
        log.info("Deleting location {}", locationId);
        this.getLocation(locationId);
        this.locationRepository.deleteById(locationId);
    }
}
