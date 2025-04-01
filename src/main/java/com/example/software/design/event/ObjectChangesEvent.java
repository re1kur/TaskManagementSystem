package com.example.software.design.event;

import java.time.LocalDateTime;

public interface ObjectChangesEvent {

    String getObjectChangesName();

    Integer getObjectChangesId();

    String getChangesBodyMessage();

    LocalDateTime getChangesTimestamp();
}
