# 📚 Spring Boot 도서 판매 API 서버 프로젝트

## 📌 프로젝트 및 코드 설명

이 프로젝트는 Spring Boot를 사용하여 구현된 도서 관련 RESTful API 서버입니다.

* **주요 기능:** 사용자 인증 및 인가(JWT), 도서 정보 조회/등록, 사용자 계정 관리.
* **데이터베이스:** MySQL을 사용하며, Flyway를 통해 마이그레이션을 관리합니다.
* **코드 구조:** 일반적인 Spring Boot MVC 패턴을 따르며, 서비스(Service), 레포지토리(Repository), 컨트롤러(Controller) 계층으로 구성되어 있습니다.

## 🌐 API 접근 정보

애플리케이션이 JCloud VM 서버의 8080 포트에서 실행 중이라고 가정합니다.

| 구분 | 주소 형식 |
| :--- | :--- |
| **Swagger 문서 주소** | `http://[JCloud Public IP]:8080/swagger-ui.html` |
| **API Root 주소** | `http://[JCloud Public IP]:8080/v1` |

## 🚀 코드 설치 및 실행 방법

### 1. 전제 조건

* Java Development Kit (JDK) 17 이상
* MySQL 8.0 데이터베이스 서버
* Gradle 설치

### 2. 프로젝트 설정 (패키지 관리)

프로젝트를 클론한 후, 필요한 환경 변수를 설정해야 합니다.

* **환경 변수 설정:** 로컬 환경에서 실행 시, `.env` 파일 등에 DB 정보와 JWT 비밀 키를 설정해야 합니다. (VM 배포 시에는 `systemd` 서비스 파일에 설정)

### 3. 애플리케이션 빌드 및 실행

#### A. 빌드 (Package Management)

Gradle Wrapper를 사용하여 프로젝트 의존성을 다운로드하고 실행 가능한 JAR 파일을 생성합니다.

```bash
# 의존성 다운로드 및 JAR 파일 생성
./gradlew clean build
