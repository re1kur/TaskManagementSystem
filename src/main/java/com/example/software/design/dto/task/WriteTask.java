package com.example.software.design.dto.task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class WriteTask {

    @NotNull
    private int projectId;

    @Size(min = 1, max = 64)
    private String name;

    private String description;

    @Future
    @NotNull
    private LocalDate deadDate;

}
