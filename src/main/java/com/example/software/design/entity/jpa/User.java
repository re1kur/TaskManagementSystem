package com.example.software.design.entity.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"projects"})
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String email;
    String username;
    String password;

    @Column(insertable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    boolean isOauth;

    @Column(insertable = false)
    boolean isVerified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_users",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "project_id"))
    Set<Project> projects;

    public void addProject(Project project) {
        if (projects == null) {
            projects = new HashSet<>();
        }
        projects.add(project);
        project.users.add(this);
    }
}
