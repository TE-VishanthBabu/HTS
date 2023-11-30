package com.htsevv.entity.generic;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalTest extends UUIDEntity{
    private String medicalTest;
    private String record;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;

}
