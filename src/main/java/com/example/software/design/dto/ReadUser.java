package com.example.software.design.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReadUser {
    private int id;
    private String nickname;
    private String password;
}
