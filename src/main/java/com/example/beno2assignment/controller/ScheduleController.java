package com.example.beno2assignment.controller;

import com.example.beno2assignment.dto.ScheduleCreateRequestDto;
import com.example.beno2assignment.dto.ScheduleDeleteRequestDto;
import com.example.beno2assignment.dto.ScheduleResponseDto;
import com.example.beno2assignment.dto.ScheduleUpdateRequestDto;
import com.example.beno2assignment.entity.Schedule;
import com.example.beno2assignment.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleCreateRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.save(requestDto.getContent(), requestDto.getPassword(), requestDto.getUsername());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAll(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String updatedAt) {

        if (username == null && updatedAt == null) {
            List<ScheduleResponseDto> responseDto = scheduleService.findAll();
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (username == null) {
            List<ScheduleResponseDto> responseDto = scheduleService.findByUpdatedAt(LocalDate.parse(updatedAt));
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else if (updatedAt == null) {
            List<ScheduleResponseDto> responseDto = scheduleService.findByUsername(username);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            List<ScheduleResponseDto> responseDto = scheduleService.findByUsernameAndUpdatedAt(username, LocalDate.parse(updatedAt));
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }

    }

    //일정 하나만 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long id) {
        ScheduleResponseDto responseDto = scheduleService.findById(id);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.update(id, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDeleteRequestDto requestDto) {
        scheduleService.delete(id, requestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
