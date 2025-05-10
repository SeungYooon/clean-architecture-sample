# 📱 Pokemon 즐겨찾기 앱

Jetpack Compose와 Clean Architecture 기반

## 🧠 아키텍처 구조 (MVVM + Clean Architecture)

<p align="center">
  <img src="https://github.com/user-attachments/assets/20c2eabb-687e-466f-bf83-22bf9be9262f" alt="Architecture Diagram" width="500"/>
</p>

## 🧱 프로젝트 구조

| 모듈                 | 설명                                                   |
| ------------------ | ---------------------------------------------------- |
| `app`              | 앱의 진입 지점. DI 설정 및 Navigation 그래프 관리                  |
| `core`             | 공통 UI 컴포넌트, 테마, 네비게이션 설정, 유틸 함수 등                    |
| `domain`           | 비즈니스 로직 계층 (Entity, UseCase, Repository Interface 등) |
| `data`             | 데이터 계층. API 통신, Room DB, Mapper, Repository 구현 포함    |
| `feature:list`     | 포켓몬 목록 화면 기능 모듈                                      |
| `feature:detail`   | 포켓몬 상세 정보 화면 기능 모듈                                   |
| `feature:favorite` | 즐겨찾기 목록 화면 기능 모듈                                     |
| `feature:common`   | 여러 feature 모듈에서 공유하는 UI 모델 및 매퍼                      |
| `build-logic`      | Gradle Convention Plugin. 버전 관리 및 빌드 설정 일괄 관리        |



## 🛠 기술 스택

| 영역           | 사용 기술                                  |
|----------------|---------------------------------------------|
| UI             | Jetpack Compose, Material3                  |
| 아키텍처       | MVVM, Clean Architecture + Multi-Module 구조      |
| DI             | Hilt                                        |
| 비동기 처리    | Kotlin Coroutines, Flow                     |
| DB             | Room, TypeConverter                         |
| 네트워크       | Retrofit2, OkHttp3, Gson                    |
| 페이징         | Paging 3                                    |
| 테스트         | JUnit, AndroidX Test                        |
| 빌드 설정      | Kotlin DSL + Version Catalog + Convention Plugin |
