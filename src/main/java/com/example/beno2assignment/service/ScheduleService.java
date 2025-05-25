package com.example.beno2assignment.service;

import com.example.beno2assignment.dto.ScheduleDeleteRequestDto;
import com.example.beno2assignment.dto.ScheduleResponseDto;
import com.example.beno2assignment.dto.ScheduleUpdateRequestDto;
import com.example.beno2assignment.entity.Schedule;
import com.example.beno2assignment.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleUpdateRequestDto requestDto) {
        // 일정 존재 여부 확인 및 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));

        // 비밀번호 인증
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 수정할 내용이 있는지 확인
        if (requestDto.getContent() == null && requestDto.getUsername() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 내용이 없습니다.");
        }

        // 일정 수정
        Optional<Schedule> updatedSchedule = Optional.of(schedule);

        if (requestDto.getContent() != null) {
            updatedSchedule = scheduleRepository.updateContentById(id, requestDto.getContent());
            if (updatedSchedule.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "일정 내용 수정에 실패했습니다!");
            }
        }

        if (requestDto.getUsername() != null) {
            updatedSchedule = scheduleRepository.updateUsernameById(id, requestDto.getUsername());
            if (updatedSchedule.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 이름 수정에 실패했습니다!");
            }
        }

        return new ScheduleResponseDto(updatedSchedule.get());
    }

    @Transactional
    public void delete(Long id, ScheduleDeleteRequestDto requestDto) {
        // 일정 존재 여부 확인 및 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));

        // 비밀번호 인증
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 삭제 시도
        boolean deleted = scheduleRepository.deleteUserById(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정 삭제에 실패했습니다.");
        }
    }


}
