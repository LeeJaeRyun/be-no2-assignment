package com.example.beno2assignment.service;

import com.example.beno2assignment.dto.ScheduleResponseDto;
import com.example.beno2assignment.entity.Schedule;
import com.example.beno2assignment.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    /**
     * 일정 저장 비즈니스 로직
     * - 저장 성공 시 DTO 반환
     * - 실패 시 400 Bad Request 예외 발생
     */
    public ScheduleResponseDto save(String content, String password, String username) {
        Optional<Schedule> schedule = scheduleRepository.save(content, password, username);
        if (schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "일정 생성 실패");
        }
        return new ScheduleResponseDto(schedule.get());
    }

    /**
     * ID 기준 단일 일정 조회
     * - 존재하지 않으면 404 Not Found 예외 발생
     */
    public ScheduleResponseDto findById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다");
        }
        return new ScheduleResponseDto(schedule.get());
    }

    /**
     * 모든 일정 조회
     * - Schedule -> ScheduleResponseDto 로 변환하여 리스트로 반환
     */
    public List<ScheduleResponseDto> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

    /**
     * 특정 사용자(username)의 일정 조회
     * - DTO 리스트로 변환하여 반환
     */
    public List<ScheduleResponseDto> findByUsername(String username) {
        List<Schedule> schedules = scheduleRepository.findByUsername(username);
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

    /**
     * 특정 날짜(updatedAt)에 수정된 일정 조회
     * - DTO 리스트로 변환하여 반환
     */
    public List<ScheduleResponseDto> findByUpdatedAt(LocalDate updatedAt) {
        List<Schedule> schedules = scheduleRepository.findByUpdatedAt(updatedAt);
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

    /**
     * 특정 사용자(username)의 일정 중
     * 특정 날짜(updatedAt)에 수정된 일정 조회
     */
    public List<ScheduleResponseDto> findByUsernameAndUpdatedAt(String username, LocalDate updatedAt) {
        List<Schedule> schedules = scheduleRepository.findByUsernameAndUpdatedAt(username, updatedAt);
        return schedules.stream().map(ScheduleResponseDto::new).toList();
    }

}
