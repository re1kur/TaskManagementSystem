package re1kur.projectservice.mapper;

import re1kur.projectservice.dto.ProjectPayload;
import re1kur.projectservice.entity.Project;

import java.util.List;

public interface ProjectMapper {
    String mapJsonList(List<Project> projects);

    Project write(ProjectPayload payload);
}
