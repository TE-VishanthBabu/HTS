package com.htsevv.entity;

import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientDischargeCode extends AbstractEntity {
    private String dischargeCode;
    private String codeDescription;
}
