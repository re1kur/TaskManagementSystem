package com.example.software.design.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Generated;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"project"})
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    @Column(columnDefinition = "unnamed")
    String name;
    String description;

    @Column(insertable = false)
    LocalDate startDate;

    LocalDate deadDate;

    @Column(insertable = false)
    String status;
}
