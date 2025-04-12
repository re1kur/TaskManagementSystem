package re1kur.projectservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import re1kur.projectservice.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
