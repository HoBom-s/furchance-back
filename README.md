# FurChance
> Give every life a chance, choose adoption.

공공데이터포털 OpenAPI를 활용한 서울시 유기동물 조회 서비스 백엔드  

## 문제 해결(Troubleshoot)
- 노션 트러블슈팅: [링크](https://flawless-octagon-2a5.notion.site/FurChance-3840ce108d904c658c5e26b965919ba5)

## 기술 스택
`Java` `Spring Boot` `Spring Security` `JPA` `Hibernate` `MySQL` `Redis` `Spring Batch` `Quartz` `JWT` `OpenFeign` 

<br/>

## 기능
### 1. OpenAPI
- 유기동물 특성 상 매일 자정 공공데이터 자료 업데이트 (Spring Batch, Quartz)
- 공공데이터포털 측 인증키 오류대비 재시도 설정 (OpenFeign Retryer)

### 2. AbandonedAnimal
- 유기동물 데이터 페이지네이션: 공고시작일, 공고종료일, 유기동물 종류에 따라 조건부 페이지네이션 (JPA Specification)

### 3. Auth
- 유저 회원가입, 로그인
- JWT(Access Token, Refresh Token) 적용
  - Access Token: 글로벌 인터셉터 prehandling 후 유저 정보 추출
  - Refresh Token: Redis 연동 후 토큰 만료 여부에 따라 조건부 처리

### 4. User
- 유저 Read, Update, Soft Delete
- 비밀번호 해싱 (Bcrypt)
- 유저 인증을 위한 별도 UserValidationService 구현

### 5. Article 
- 게시글 CRUD
- 게시글 페이지네이션

<br/>
