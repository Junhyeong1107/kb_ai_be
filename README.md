# kb_ai
2024 KB AI challenge

# 감정분석
## 기능흐름도
![image](https://github.com/user-attachments/assets/1faadbe2-f064-47e5-b3bb-8ef504c1645f)
|---------------|

# API Documentation
## AuthController

### 로그인
- **Endpoint:** `POST /login`
- **Request:** 사용자 ID와 비밀번호
- **Response:** 로그인 성공/실패 메시지

---

## CsvController

### 주간 카테고리 데이터 조회
- **Endpoint:** `GET /csv/data`
- **Request:** `referenceDate` (날짜 문자열)
- **Response:** 주간 카테고리별 데이터 (JSON)

---

## EmotionController

### 감정 데이터 수신
- **Endpoint:** `POST /receiveEmotionData`
- **Request:** 감정 데이터 리스트 (JSON)
- **Response:** 없음

### 감정 데이터 조회
- **Endpoint:** `GET /getEmotionData`
- **Response:** 감정 데이터 JSON 파일 다운로드

### 스트레스 데이터 수신
- **Endpoint:** `POST /receiveStressData`
- **Request:** 스트레스 데이터 리스트 (JSON)
- **Response:** 없음

### 스트레스 데이터 조회
- **Endpoint:** `GET /getStressData`
- **Response:** 스트레스 데이터 JSON 파일 다운로드
