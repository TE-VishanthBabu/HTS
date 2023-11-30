package com.htsevv.entity;

import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingCaseNotesQuestion extends AbstractEntity {
    private String question;
}
