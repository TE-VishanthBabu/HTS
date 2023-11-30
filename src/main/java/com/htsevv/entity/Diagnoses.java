package com.htsevv.entity;

import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Diagnoses extends UUIDEntity {

    @NotEmpty(message = "Diagnoses should be required")
    @NotNull(message = "Diagnosis should not be null")
    private String name;
}
