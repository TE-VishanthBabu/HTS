package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted=false")
public class HousingStabilization extends AbstractEntity {
    private String clientId;
    private String employeeId;
    private String contactType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private Double hours;
    private Integer units;
    private String serviceCode;
    private String serviceDescription;
    @OneToMany(cascade = CascadeType.ALL)
    private List<HousingQA> qas;
    private String task;
    @Column(length = 10000)
    private String taskDescription;
    @Column(length = 10000)
    private String notes;
    private String staffSignature;
}
