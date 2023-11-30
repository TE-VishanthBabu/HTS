package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PcaUmpiInfo extends UUIDEntity {
    private String payor;
    private String umpi;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activationDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiryDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date faxDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date receivedDate;
    private String faxDoc;
}
