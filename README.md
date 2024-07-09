# 폴리큐브 백엔드 개발자 코딩 테스트

**(주)폴리큐브**에서 시행하는 백엔드 개발자 코딩 테스트용 리포지토리입니다.

## 1. 시작하기

### 1.1. 개발 환경

- OpenJDK 17
- Spring Boot 3.2.1

### 1.2. 라이브러리

- Spring Web
- Lombok
- H2 Database ( ID : pc, PW : 2024 )
- 그 외 필요한 라이브러리는 `build.gradle`에 추가하시면 됩니다.

**라이브러리 추가 시, 어떠한 이유로 추가했는지 `Pull Request`에 간단히 적어주시면 됩니다.**

#### [✔︎] 라이브러리 추가 이유

```
'org.springframework.boot:spring-boot-starter-data-jpa'
'jakarta.persistence:jakarta.persistence-api:3.1.0'
- DB 상호작용을 위한 JPA 및 Spring Data 기능 활용을 위한 라이브러리 추가
'jakarta.servlet:jakarta.servlet-api:5.0.0'
'jakarta.validation:jakarta.validation-api:3.0.0'
'org.hibernate.validator:hibernate-validator:7.0.0.Final'
- HTTP 요청과 응답, 자바 빈 유효성 검사를 위한 라이브러리 추가
'org.springframework.batch:spring-batch-core:5.1.0'
'org.springframework.boot:spring-boot-starter-batch'
'org.springframework.batch:spring-batch-test'
- 스프링 배치 사용을 위한 라이브러 추가
'org.springframework.boot:spring-boot-starter-quartz'
- 스프링 애플리케이션 작업 스케쥴링을 위한 라이브러리 추가

```

### 1.3. 실행 방법

```shell
./gradlew bootRun
```

#### H2 서버 모드로 먼저 실행
- H2 DB 설치 경로로 이동 (cd /Users/leesfact/h2/bin)
- 서버 실행 (java -cp h2*.jar org.h2.tools.Server -tcp -tcpAllowOthers -tcpPort 9092 )  
  <img width="449" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/8b3b3e72-af43-475b-bef2-1cc925f5c452">
- 웹 애플리케이션 실행 (./gradlew bootRun)


## 2. 개발 요구사항

공통, 기본, 구현 문제로 구성되어 있으며, 각 문제에 대한 요구사항을 모두 만족해야 합니다.

### 2.1. 공통 (20점)

- [ ] `@ControllerAdvice`, `@ExceptionHandler`를 이용하여, 잘못된 요청에 대한 응답을 처리한다. (4점)
  - [ ] API를 호출할 때, 잘못된 요청이 들어오면 HTTP 400 상태의 `{"reason": 실제사유}`을 응답한다.
  - [ ] API에 대한 실패 상황 통합 테스트 코드 작성
  - [ ] 존재하지 않는 API 호출 시, HTTP 404 상태의 `{"reason": 실제사유}`을 응답한다.
- [ ] Spring MVC 아키텍처와 Restful API를 준수하여 개발한다. (8점)
  - [ ] `@RestController`, `@Service`, `@Repository`를 이용하여 개발한다.
  - [ ] HTTP Method와 URI를 적절하게 사용하여 개발한다.
- [ ] Clean Code를 준수하여 개발한다. (8점)
  - [ ] 코드 스타일을 일관되고 명확하게 작성한다.
  - [ ] 메소드와 클래스의 역할을 명확하게 작성한다.
     
|잘못된 요청 시|존재하지 않는 API 호출 시|
|:---------:|:------------------:|
|<img width="618" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/a9d659e8-3795-44a0-8251-71c202b029e6">|<img width="671" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/858177eb-dec2-4651-a483-77c736fbc309">|
|<img width="474" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/45ab66b0-735d-43ae-81f3-15c72892dd45">|<img width="391" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/b6060c41-8bc6-46af-aed3-72d98e0bbf45">|



### 2.2. 기본 문제 (50점)

- [ ] user 등록 API 구현 (8점)
  - [ ] `/users` API를 호출하면, `{"id": ?}`을 응답한다.
  - [ ] `/users` API에 대한 통합 테스트 코드 작성
- [ ] user 조회 API 구현 (8점)
  - [ ] `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.
  - [ ] `/users/{id}` API에 대한 통합 테스트 코드 작성
- [ ] user 수정 API 구현 (8점)
  - [ ] `/users/{id}` API를 호출하면, `{"id": ?, "name": "?"}`을 응답한다.
  - [ ] `/users/{id}` API에 대한 통합 테스트 코드 작성
- [ ] 필터 구현 (12점)
  - [ ] URL에 `? & = : //`를 제외한 특수문자가 포함되어 있을경우 접속을 차단하는 Filter 구현한다.
  - [ ] `/users/{id}?name=test!!` API 호출에 대한 통합 테스트 코드 작성
- [ ] Spring AOP를 활용한 로깅 구현 (14점)
  - [ ] user 등록, 조회, 수정 API에 대해 Request시 Console에 Client Agent를 출력한다.

`user` 테이블

```csv
id,name
```
[✔︎] user 등록 API 통합 테스트
<img width="989" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/6a36712e-0182-4b88-a10d-62baf035ec81">
<img width="101" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/09d95d1c-4973-4bbf-a94c-e885ff44e6fb">

[✔︎] user 조회 API 통합 테스트
<img width="994" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/fea862d4-81a1-407c-85ca-4b0a102dbf08">
<img width="253" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/7ccd6d73-e0d4-4ae0-a92a-34d8c01034eb">

[✔︎] user 수정 API 통합 테스트
<img width="994" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/c15c63aa-c0f1-49bc-b631-db5baed72c8e">
<img width="262" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/aa712b8d-4a8c-4f7b-ba2b-3a59142b6779">

[✔︎] H2 DB id, name 최신화

<img width="182" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/6034d1cf-f5d8-4f22-98d4-7a0c6b1a79d2">



[✔︎] 필터 통합 테스트

<img width="870" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/5d90c3cd-df48-4944-bf83-d0b1dc69abda">
<img width="634" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/19937dce-e60d-44e9-927f-bdb953e5d815">

[✔︎] Spring AOP를 활용한 로깅 통합 테스트
|단위 테스트 API 요청 시|postman API 호출 시|
|:---------:|:------------------:|
|<img width="867" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/215f6d40-0648-41b7-8a35-3781e559f8df">|<img width="500" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/54a14c49-c220-437c-821d-89ecb30ed2f2">|
|<img width="346" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/f6887a14-8fd1-4254-babd-9f2c9e05fad1">|<img width="403" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/f7a45638-8f25-4960-a834-a23858033a38">|





### 2.3. 구현 문제 (30점)

#### 로또 번호 발급 API 구현 (10점)

- [ ] `POST /lottos` API를 호출하면, `{"numbers": [?, ?, ?, ?, ?, ?]}`을 응답한다.
- [ ] `POST /lottos` API에 대한 통합 테스트 코드 작성

##### Request

```shell
curl -X POST -H "Content-Type: application/json" http://localhost:8080/lottos
```

##### Response

```json
{
  "numbers": [?, ?, ?, ?, ?, ?]
}
```

[✔︎] 로또 번호 발급 API 통합 테스트
<img width="984" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/f7e0575c-4dc6-4f64-90e8-59fc221c4a95">
<img width="392" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/8f67a83c-eaa6-489e-af18-1ec196922f0e">

- curl -X POST -H "Content-Type: application/json" http://localhost:8080/lottos
  
<img width="587" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/2df1325b-bf48-4b20-80d5-7ca80945ee4d">




#### 로또 번호 당첨자 검수 Batch 구현 (20점)

- [ ] 랜덤하게 로또 번호를 발급하여, 당첨 번호와 비교하여 당첨자를 검수하는 Batch를 구현한다.
  - [ ] 당첨자의 등수는 1등, 2등, 3등, 4등, 5등이 있다.
  - [ ] 당첨자의 등수는 당첨 번호와 일치하는 번호의 개수로 판단한다.
  - [ ] 당첨자 정보는 `winner` 테이블에 저장한다.
- [ ] Batch는 매주 일요일 0시에 실행되도록 구현한다.
- [ ] Batch에 대한 통합 테스트 코드 작성

##### Input Data

`lotto` 테이블

```csv
id,number_1,number_2,number_3,number_4,number_5,number_6
1,7,28,33,2,45,19
2,26,14,41,3,22,35
3,15,29,38,6,44,21
4,31,16,42,9,23,36
5,17,30,39,10,45,24
```

##### Output Data

`winner` 테이블

```csv
id,lotto_id,rank
```

- `id`: generated value
- `lottos_id`: 당첨 번호의 `id`
- `rank`: 당첨 등수 (1등, 2등, 3등, 4등, 5등)

[✔︎] 로또 번호 당첨자 검수 Batch 테스트

- 랜덤한 번호 6개와 data.sql에 저장된 당첨 번호 5개와 비교
- id로 순회하여 matchCount를 리스트 형태로 저장
- 6개 일치 시 1등, 5개 일치 시 2등, 4개 일치시 3등 ...
- 1개 또는 0개 일치 시, 예외 처리

- Input Data 설정
  
<img width="157" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/4c421df9-b2d1-4158-b4d3-c6837fd01b8a">

- Batch 테스트
  
<img width="1067" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/2277fb8e-22cb-4ae6-ab4d-5a0079ffad5d">

- 테스트 결과

<img width="442" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/3ec29580-a0d3-4318-9d6d-c183a92d44e3">



#### 추가 설명

- `?`는 임의의 값으로, 실제로 응답할 때는 해당 값이 들어가야 합니다.
- `id`는 `Long` 타입입니다.
- `@ExceptionHandler`는 `@RestControllerAdvice`를 이용하여 구현합니다.

## 제출 방법

- 개발이 완료되면, 이 리포지토리에 `Pull Request`를 보내주시면 됩니다.
- `Pull Request`에는 응시자가 개발하면서 고민했던 점, 혹은 어려웠던 점을 간단히 적어주시면 됩니다. (선택사항)

#### 개발하면서 고민했던 점 혹은 어려웠던 점

1. 처음 h2 DB를 사용하면서 임베디드 모드, 파일 모드, 서버 모드가 있다는 것을 알고 이번 테스트에 맞는 `서버 모드`를 결정

> 엔티티 클래스의 인식 문제: User 클래스가 JPA 엔티티로 인식되지 않았습니다

    - BackendTestApplication 클래스에서 명시적으로 JPA 리포지토리와 엔티티를 스캔할 패키지를 지정
      @SpringBootApplication
      @EnableJpaRepositories(basePackages = "kr.co.polycube.backendtest.repository")
      @EntityScan(basePackages = "kr.co.polycube.backendtest.model")
    👉🏻 실패

    - 라이브러리 의존성 변경
       - 구글링 결과, Spring Boot 3.x.x 버전에서는 javax 패키지에서 jakarta 패키지로 마이그레이션되었음을 알게 되었습니다.
          - 기존의 저는 Spring Boot 2.x.x 버전을 사용했기에 javax 패키지를 그대로 사용하였습니다
    👉🏻 성공

2.  Batch 구현 시 발생 했던 가장 어려웠던 문제

> BATCH_STEP_EXECUTION 테이블에는 `START_TIME`이 있는데, 배치 테스트에서 계속 NULL 값이 검출 되었음

- Spring Boot 2.x.x 버전에서는 beforeStep(StepExecution stepExecution)의 매개변수 stepExecution이 Date() 객체를 받아왔는데,
  Spring Boot 3.x.x 버전 및 spring-batch-core 5.x.x 버전에서는 stepExecution이 LocalDateTime() 객체를 매개변수를 받게 끔 변경되었음
  따라서, schema.sql `START_TIME`에 Not Null 옵션을 주었음

  <img width="301" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/f71289fc-bbbe-49ac-aae4-45d90f2778b3">

  👉🏻 최종적으로 START_TIME에 NULL 값이 들어가는 이유는 schema.sql의 초기세팅과 stepExecution이 LocalDateTime()의 값이 충돌하면서 NULL 값으로 인식한다고 판단, Not Null 속성을 제거

  <img width="466" alt="image" src="https://github.com/LeeKangyong/backendTest/assets/17938829/73e05cf6-1e18-4dc8-bab0-576cc535a986">

 

        


   


