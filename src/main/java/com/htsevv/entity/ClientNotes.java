package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.UUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientNotes extends UUIDEntity {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date noteDate;
    private String initialName;
    private String type;
    private String notes;
}
