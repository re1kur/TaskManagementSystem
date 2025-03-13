package com.example.software.design.dto.project;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WriteProject {
    @Size(min = 6, max = 128)
    @NotNull
    String name;
}
