package com.htsevv.entity;

import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HousingCaseNotesTask extends AbstractEntity {
    private String task;
    private String taskDescription;
}
