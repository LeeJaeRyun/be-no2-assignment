package com.example.beno2assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ScheduleUpdateRequestDto {
    private String content;
    private String username;
    private String password;
}