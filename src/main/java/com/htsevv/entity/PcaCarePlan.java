package com.htsevv.entity;

import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PcaCarePlan extends UUIDEntity {
    private String clientHistory;
    private String allergies;
    private String frequencyOfVisits;
}
