package com.example.beno2assignment.dao;

import com.example.beno2assignment.entity.Schedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Slf4j // 로깅을 위한 롬복 어노테이션
@Repository // DAO(데이터 접근 객체) 빈으로 등록
@RequiredArgsConstructor // final 필드를 가진 생성자 자동 생성
public class ScheduleDao { // 실제 DB 쿼리 실행을 담당하는 DAO 클래스

    private final JdbcTemplate jdbcTemplate; // 쿼리 실행을 위한 Spring JDBC 템플릿

    // 쿼리 결과 ResultSet을 Schedule 객체로 변환하는 RowMapper 정의
    private RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("id"));
            schedule.setContent(rs.getString("content"));
            schedule.setUsername(rs.getString("username"));
            schedule.setPassword(rs.getString("password"));
            schedule.setCreatedAt(rs.getDate("created_at").toLocalDate()); // java.sql.Date → java.time.LocalDate
            schedule.setUpdatedAt(rs.getDate("updated_at").toLocalDate());
            return schedule;
        };
    }

    // id 기준으로 일정 하나 조회 (없으면 null 리턴)
    public Schedule findById(Long id) {
        List<Schedule> schedules = jdbcTemplate.query(
                "SELECT * FROM schedule WHERE id = ?",
                scheduleRowMapper(),
                id
        );
        return schedules.isEmpty() ? null : schedules.get(0);
    }

    // 전체 일정 조회
    public List<Schedule> findAll() {
        return jdbcTemplate.query("SELECT * FROM schedule", scheduleRowMapper());
    }

    // 새로운 일정 저장
    // - 성공 시 생성된 PK(id)를 리턴
    // - 실패 시 -1L 리턴
    public Long save(String content, String password, String username) {
        String query = "INSERT INTO schedule(content, password, username, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 자동 생성된 키(PK) 저장용 객체
        Date today = new Date(System.currentTimeMillis()); // 현재 날짜를 created_at, updated_at에 저장하기 위함

        // SQL 쿼리에 들어갈 PreparedStatement 객체를 생성하는 람다
        PreparedStatementCreator preparedStatementCreator = con -> {
            PreparedStatement pstmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, content);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setDate(4, today);
            pstmt.setDate(5, today);
            return pstmt;
        };

        // INSERT 실행 + 자동 생성된 키 획득
        int rowCount = jdbcTemplate.update(preparedStatementCreator, keyHolder);

        if (rowCount > 0) {
            // 생성된 PK 꺼내기
            return ((Number) keyHolder.getKeyList().get(0).get("GENERATED_KEY")).longValue();
        } else {
            return -1L;
        }
    }

    // 일정 내용(content)만 수정 (updated_at도 함께 갱신)
    public int updateContentById(Long id, String content) {
        Date today = new Date(System.currentTimeMillis());
        return jdbcTemplate.update(
                "UPDATE schedule SET content = ?, updated_at = ? WHERE id = ?",
                content, today, id
        );
    }

    // 작성자(username)만 수정 (updated_at도 함께 갱신)
    public int updateUsernameById(Long id, String username) {
        Date today = new Date(System.currentTimeMillis());
        return jdbcTemplate.update(
                "UPDATE schedule SET username = ?, updated_at = ? WHERE id = ?",
                username, today, id
        );
    }

    // 일정 삭제
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }
}