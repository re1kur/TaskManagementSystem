package re1kur.projectservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import re1kur.projectservice.dto.ProjectPayload;
import re1kur.projectservice.entity.Project;
import re1kur.projectservice.mapper.ProjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultProjectMapper implements ProjectMapper {
    private final ObjectMapper serializer = new ObjectMapper();

    @Override
    @SneakyThrows
    public String mapJsonList(List<Project> projects) {
        return serializer.writeValueAsString(projects);
    }

    @Override
    public Project write(ProjectPayload payload) {
        return Project.builder()
                .title(payload.title())
                .description(payload.description())
                .build();
    }
}
