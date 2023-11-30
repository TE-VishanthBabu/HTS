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
public class ClientCareCoordinator extends AbstractEntity {
    private String name;
    private String country;
    private String telephone;
    private String fax;
    private String alternateFax;
    private String email;
}
