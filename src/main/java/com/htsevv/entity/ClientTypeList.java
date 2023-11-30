package com.htsevv.entity;

import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted=false")
public class ClientTypeList extends AbstractEntity {
    private Boolean status;
    private Integer number;
    private String typeName;
}
