package com.example.software.design.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectChangesEvent implements ObjectChangesEvent{
    private final String objectChangesName = "projects";

    private Integer objectChangesId;

    private String changesBodyMessage;

    private LocalDateTime changesTimestamp;

    @Override
    public String getObjectChangesName() {
        return objectChangesName;
    }

    @Override
    public Integer getObjectChangesId() {
        return objectChangesId;
    }

    @Override
    public String getChangesBodyMessage() {
        return changesBodyMessage;
    }

    @Override
    public LocalDateTime getChangesTimestamp() {
        return changesTimestamp;
    }
}
