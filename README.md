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
    "username": "이재륜",
    "password": "qwer1234",
    "content": "데베시 숙제하기"
  }

- ResponseBody
  ```
  {
    "id": 1,
    "username": "이재륜",
    "content": "데베시 숙제하기",
    "createdAt": "2025-05-24 14:30:00",
    "modifiedAt": "2025-05-25 14:30:00"
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
      "content": "회의 관련 소통~",
      "username": "양준영",
      "createdAt": "2025-05-20 10:00:00",
      "modifiedAt": "2025-05-24 11:30:00"
    },
    {
      "id": 2,
      "content": "다형성에 대해 이해하기",
      "username": "강민재",
      "createdAt": "2025-05-19 08:00:00",
      "modifiedAt": "2025-05-23 09:00:00"
    },
    {
      "id": 3,
      "content": "실전 영어 회화1 다 끝내기",
      "username": "주온유",
      "createdAt": "2025-05-11 08:00:00",
      "modifiedAt": "2025-05-22 09:00:00"
    }
  ]


### 3. 단건 일정 조회 [GET]
- PathVariable
    - id : 1

- ResponseBody
  ```
  {
    "id": 1,
    "content": "회의 관련 소통~",
    "username": "양준영",
    "createdAt": "2025-05-20 10:00:00",
    "modifiedAt": "2025-05-24 11:30:00"
  }


### 4. 일정 수정 [PATCH]
- PathVariable
    - id : 1

- RequestBody
  ```
  {
    "username": "양준영",
    "password": "qwer4321!",
    "content": "DB 데이터 정규화 과제 해결"
  }

- ResponseBody
  ```
  {
    "id": 1,
    "content": "DB 데이터 정규화 과제 해결",
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

### lv2 까지                            
![image](https://github.com/user-attachments/assets/c61c35ea-ca15-4654-83f9-9c3b44d2dfd0)

### lv3 이후
![image](https://github.com/user-attachments/assets/3d545f0a-952b-495f-99f3-6f109c964d49)

## CHECK
### 1. 적절한 관심사 분리를 적용하셨나요? (Controller, Service, Repository)
http요청을 받고 dto로 변환해서 service계층에 넘기고 반환받은걸 응답해주는 controller
controller로부터 받은 데이터로 비즈니스 로직을 처리해주는 service
DB와 직접적인 접근을하는 repository 로 적절하게 분리한것같다.

### 2. RESTful한 API를 설계하셨나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
생성,조회,수정,삭제 즉 http메서드의 의미를 파악하고 사용하였고 명사 기반의 URI를 사용하였고
상태코드를 사용하였으며 json기반의 요청/응답을 사용하였습니다.

### 3. 수정, 삭제 API의 request를 어떤 방식으로 사용 하셨나요? (param, query, body)
수정의 경우에는 pathvariable로 id를 전달하고 RequestBody로 객체를 전달하였음
삭제의 경우도 똑같이 pathvariable로 id를 전달하고 RequestBody로 객체를 전달하였음. 
삭제의 경우에 비밀번호 하나만 RequestBody에 담았는데 직접 노출되는 방식인 param이나 pathvariable보다 보안상 안전할 것 같다고 생각했음.
