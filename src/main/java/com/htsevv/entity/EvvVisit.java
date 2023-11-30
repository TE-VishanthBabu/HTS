package com.htsevv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.htsevv.entity.generic.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EvvVisit extends AbstractEntity {
    public enum Status { ACCEPTED, REJECTED, PENDING};

    private String serviceName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfService;

    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalDateTime timeDuration;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee provider;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Location location;

    private Status status;

    private String notes;
}
