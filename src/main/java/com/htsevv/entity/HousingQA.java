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
public class HousingQA extends UUIDEntity {
    private String question;
    private String answer;

}
