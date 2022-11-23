package com.wazv.sistemagestiontareas.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wazv.sistemagestiontareas.models.enums.EnumState;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"tasks", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private User user;

    @Enumerated(EnumType.STRING)
    private EnumState state;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "update_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

}
