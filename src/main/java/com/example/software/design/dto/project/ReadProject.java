package com.example.software.design.dto.project;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ReadProject {
    int id;
    String name;
}
