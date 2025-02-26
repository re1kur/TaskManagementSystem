package com.example.software.design.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"projects"})
@Getter
@Table(schema = "ver1", name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String nickname;
    String password;

    @Column(insertable = false)
    String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_users")
    Set<Project> projects;
}
