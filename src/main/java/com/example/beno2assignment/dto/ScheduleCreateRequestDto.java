package com.example.beno2assignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleCreateRequestDto {
    private String content;
    private String password;
    private String username;
}
