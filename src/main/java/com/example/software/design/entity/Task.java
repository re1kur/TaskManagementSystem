package com.example.software.design.entity;

import jakarta.persistence.*;
import lombok.*;

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

    String name;

    String description;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "dead_date")
    LocalDate deadDate;
}
