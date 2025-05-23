# 카카오 테크 캠퍼스 1단계 2차 과제

## API 명세서
| 기능 | Method | URL | request | response | status code |
|------|--------|-----|---------|----------|-------------|
| 일정 생성 | POST | /api/schedules | 요청 body | 등록된 일정 정보 | 201 |
| 전체 일정 조회 | GET | /api/schedules | 요청 param | 다건 일정 정보 | 200 |
| 단건 일정 조회 | GET | /api/schedules/{id} | 요청 param | 단건 일정 정보 | 200 |
| 일정 수정 | PUT | /api/schedules/{id} | 요청 body | 수정된 일정 정보 | 200 |
| 일정 삭제 | DELETE | /api/schedules/{id} | 요청 param | - | 200 |
