package com.example.beno2assignment.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Schedule {

    private Long id;

    private String content;

    private String username;

    private String password;

    private LocalDate createdAt;

    private LocalDate updatedAt;

}
