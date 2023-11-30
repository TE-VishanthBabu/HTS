package com.htsevv.entity;

import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareGiverNotes extends UUIDEntity {
    private String allergies;
    private String specialRequests;
    private String comments;
}
