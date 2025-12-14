# 📌 프로젝트 소개

이 프로젝트는 [간단한 프로젝트 목적/기능 설명]을 위해 Spring Boot와 Gradle을 사용하여 개발된 백엔드 API 서버입니다.

### 주요 기능

* **인증 및 인가:** JWT(JSON Web Token)를 사용한 사용자 로그인 및 권한 관리
* **데이터 관리:** MySQL 데이터베이스를 사용한 데이터 영속성 관리
* **[기타 주요 기능 목록 추가]** (예: 도서 목록 조회, 사용자 등록 등)

## 🛠️ 기술 스택 (Tech Stack)

| 구분 | 기술 스택 | 버전 |
| :--- | :--- | :--- |
| **백엔드** | Spring Boot | 3.2.x 이상 |
| | Java | OpenJDK 17 |
| **빌드 도구** | Gradle | 8.x 이상 |
| **데이터베이스** | MySQL | 8.0 |
| **데이터 마이그레이션** | Flyway | (사용 시) |
| **API 문서** | Springdoc (Swagger UI) | (사용 시) |

## 🚀 개발 환경 설정

### 1. 전제 조건

* Java Development Kit (JDK) 17 이상
* MySQL 8.0 데이터베이스 서버
* Gradle 8.x 이상

### 2. 프로젝트 실행 방법

#### 로컬 환경 (Local Environment)

1.  **환경 변수 설정:** 프로젝트 루트 디렉토리에 `.env` 파일을 생성하고 다음 변수를 설정합니다.
    ```env
    # .env
    SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/bookstore?serverTimezone=UTC
    SPRING_DATASOURCE_USERNAME=bookstore_user
    SPRING_DATASOURCE_PASSWORD=1234
    JWT_SECRET=[길고 복잡한 비밀 키]
    ```
2.  **의존성 설치 및 빌드:**
    ```bash
    ./gradlew build
    ```
3.  **애플리케이션 실행:**
    ```bash
    java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
    ```

#### 서버 환경 (Deployment Environment)

* **배포 환경:** JCloud VM (Ubuntu/CentOS 등)
* **실행 방식:** `systemd` 서비스를 이용한 백그라운드 실행
* **환경 변수 설정:** `.env` 파일 대신 `/etc/systemd/system/bookstore-api.service` 파일 내 `Environment` 옵션을 사용합니다.

## 📝 API 문서 (Swagger)

애플리케이션이 실행되면, 다음 주소에서 API 문서를 확인할 수 있습니다.

* **접근 주소:** `http://113.198.66.68:8080/swagger-ui.html`