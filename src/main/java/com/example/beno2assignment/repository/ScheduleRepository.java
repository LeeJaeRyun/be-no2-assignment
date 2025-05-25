package com.example.beno2assignment.repository;

import com.example.beno2assignment.dao.ScheduleDao;
import com.example.beno2assignment.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final ScheduleDao scheduleDao;

    /**
     * 일정 저장 (트랜잭션 단위)
     * 실패 시 Optional.empty() 반환
     * 성공 시 저장된 Schedule 객체 반환
     */
    @Transactional
    public Optional<Schedule> save(String content, String password, String username) {
        Long id = scheduleDao.save(content, password, username);
        if (id < 0) return Optional.empty();
        return Optional.ofNullable(scheduleDao.findById(id));
    }

    /**
     * ID 기준으로 일정 조회
     * 존재하지 않으면 Optional.empty() 반환
     */
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(scheduleDao.findById(id));
    }

    /**
     * 모든 일정을 최신 수정순으로 정렬하여 조회
     */
    public List<Schedule> findAll() {
        return scheduleDao.findAll().stream()
                .sorted(Comparator.comparing(Schedule::getUpdatedAt).reversed())
                .toList();
    }

    /**
     * 특정 사용자(username)의 일정만 필터링하여
     * 최신 수정순으로 정렬 후 반환
     */
    public List<Schedule> findByUsername(String username) {
        return scheduleDao.findAll().stream()
                .filter(schedule -> schedule.getUsername().equals(username))
                .sorted(Comparator.comparing(Schedule::getUpdatedAt).reversed())
                .toList();
    }

    /**
     * 특정 날짜(LocalDate)에 수정된 일정만 반환
     * 날짜 일치 여부는 Schedule의 updatedAt 기준
     */
    public List<Schedule> findByUpdatedAt(LocalDate updatedAt) {
        return scheduleDao.findAll().stream()
                .filter(schedule -> schedule.getUpdatedAt().equals(updatedAt))
                .toList(); // 날짜 기준 필터만 수행, 정렬 필요 없음
    }

    /**
     * 특정 사용자(username)의 일정 중
     * 특정 날짜(LocalDate)에 수정된 일정만 필터링해서 반환
     */
    public List<Schedule> findByUsernameAndUpdatedAt(String username, LocalDate updatedAt) {
        return scheduleDao.findAll().stream()
                .filter(schedule ->
                        schedule.getUsername().equals(username) &&
                                schedule.getUpdatedAt().equals(updatedAt)
                ).toList();
    }

    /**
     * ID로 일정의 content를 수정
     * 성공 시 수정된 Schedule 반환, 실패 시 Optional.empty()
     */
    @Transactional
    public Optional<Schedule> updateContentById(Long id, String content) {
        int updatedRows = scheduleDao.updateContentById(id, content);
        if (updatedRows > 0) {
            return Optional.ofNullable(scheduleDao.findById(id));
        }
        return Optional.empty();
    }

    /**
     * ID로 일정의 username을 수정
     * 성공 시 수정된 Schedule 반환, 실패 시 Optional.empty()
     */
    @Transactional
    public Optional<Schedule> updateUsernameById(Long id, String username) {
        int updatedRows = scheduleDao.updateUsernameById(id, username);
        if (updatedRows > 0) {
            return Optional.ofNullable(scheduleDao.findById(id));
        }
        return Optional.empty();
    }

    /**
     * ID로 일정 삭제
     * 성공 시 true, 실패 시 false 반환
     */
    public boolean deleteUserById(Long id) {
        return (scheduleDao.deleteById(id) > 0);
    }


}