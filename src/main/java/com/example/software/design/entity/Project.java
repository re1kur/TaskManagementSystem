package com.example.software.design.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"users", "tasks"})
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToMany
    @JoinTable(name = "project_users")
    Set<User> users;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    Set<Task> tasks;
}
