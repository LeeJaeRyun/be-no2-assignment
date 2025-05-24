# 카카오 테크 캠퍼스 1단계 2차 과제


## API 명세서
| 기능 | Method | URL | request | response | status code |
|------|--------|-----|---------|----------|-------------|
| 일정 생성 | POST | /api/schedules | 요청 body | 등록된 일정 정보 | 201 Created |
| 전체 일정 조회 | GET | /api/schedules | 요청 param | 다건 일정 정보 | 200 OK |
| 단건 일정 조회 | GET | /api/schedules/{id} | 요청 param | 단건 일정 정보 | 200 OK |
| 일정 수정 | PATCH | /api/schedules/{id} | 요청 body | 수정된 일정 정보 | 200 OK |
| 일정 삭제 | DELETE | /api/schedules/{id} | 요청 body | - | 200 OK |


## API 상세 명세서
### 1. 일정 생성 [POST]
- RequestBody
  ```
  {
    "username" : "이재륜",
    "password" : "qwer1234",
    "title" : "일정1",
    "task" : "데베시숙제하기"
  }

- ResponseBody
  ```
  {
    "id" : 1,
    "username" : "이재륜",
    "title" : "일정1",
    "task" : "데베시숙제하기",
    "createdAt" : "2025-05-24 14:30:00",
    "modifiedAt" : "2025-05-25 14:30:00"
  }

### 2. 전체 일정 조회 [GET]
- RequestParam
    - modifiedAt : "2025-05-24"
    - username : "이재륜"

- ResponseBody
  ```
  [
    {
      "id": 1,
      "title": "회의 준비",
      "task" : "회의 관련 소통~",
      "username": "양준영",
      "createdAt": "2025-05-20 10:00:00",
      "modifiedAt": "2025-05-24 11:30:00"
    },
    {
      "id" : 2,
      "title" : "자바스터디 준비",
      "task" : "다형성에 대해 이해하기",
      "username" : "강민재",
      "createdAt" : "2025-05-19 08:00:00",
      "modifiedAt" : "2025-05-23 09:00:00"
    },
    {
      "id": 3,
      "title" : "영어스터디 준비",
      "task" : "실전 영어 회화1 다 끝내기",
      "username" : "주온유",
      "createdAt" : "2025-05-11 08:00:00",
      "modifiedAt" : "2025-05-22 09:00:00"
    }
  ]

### 3. 단건 일정 조회 [GET]
- PathVariable
    - id : 1

- ResponseBody
  ```
  {
    "id" : 1,
    "title" : "회의 준비",
    "task" : "회의 관련 소통~",
    "username" : "양준영",
    "createdAt" : "2025-05-20 10:00:00",
    "modifiedAt" : "2025-05-24 11:30:00"
  }

### 4. 일정 수정 [PATCH]
- PathVariable
    - id : 1

- RequestBody
  ```
  {
    "username" : "양준영",
    "password" : "qwer4321!",
    "title" : "DB 과제",
    "task" : "DB 데이터 정규화 과제 해결"
  }

- ResponseBody
    ```
    {
      "id": 1,
      "title": "DB 과제",
      "task" : "DB 데이터 정규화 과제 해결",
      "username": "양준영",
      "createdAt": "2025-05-20 10:00:00",
      "modifiedAt": "2025-05-24 11:30:00"
    }
    
### 5. 일정 삭제 [DELETE]
- PathVariable
    - id : 2

- RequestBody
  ```
  {
    "password" : "1q2w3e4r!"
  }



## ERD 설계
![image](https://github.com/user-attachments/assets/7fd1c3f9-a5e4-43fd-84f4-e6b44b6c142a)

## SQL 
```
  CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
  );

```
  CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    task TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
  );




