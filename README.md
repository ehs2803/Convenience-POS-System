# Convenience-POS-System

**스프링프레임워크(소프트웨어학과전공) 과목 텀프로젝트**

## 프로젝트명  
편의점 POS(Point Of Sale) 시스템

<br>

## 프로젝트 소개 
스프링부트 프레임워크를 이용한 편의점 POS 시스템 개발. 스프링프레임워크(소프트웨어학과전공) 과목 텀프로젝트

<br>

## 개발기간

2022.05.09~2022.06.03

<br>


## 작품 기능

- 계정기능
- 재고관리(입고) 기능
- 판매기능
- 통계기능

<br>


## 요구사항

- Spring Boot 사용

- Spring MVC를 이용하여 웹버전으로 개발

- 계정정보, 재고정보, 판매정보 등은 MySQL 사용(Spring JDBC 이용)

- Spring Context객체를 사용할 것. (xml 또는 자바설정 방식 선택)

- 주요 객체들간의 dependency를 spring 설정기능을 이용해 constructor/setter injection을 사용하여 연결

- Bean 객체 7개 이상 등록(setter, constructor injection 사용) - **17개 Bean 객체 사용**

- 웹화면은 Thymeleaf 사용

<br>
<br>

## 작품 구성도

1) 서비스 구성도
![pos](./images/pos.jpg)


2) 기능 구성도
![pos](./images/menu.jpg)

<br>
<br>


## 기능

![pos](./images/function.jpg)

<br>
<br>


## 작품 개발 환경

|OS|개발환경 IDE|개발도구|빌드관리도구|개발언어|형상관리도구|
|---|---|---|---|---|---|
|window11|IntelliJ IDEA 2021.2.2|Spring Boot 2.6.7|Maven|JAVA 11|Github|
<br>
<br>


## 의존성

|groupId|artifactId|version|
|-----|-----|-----|
|org.springframework.boot|spring-boot-starter-web|2.6.7|
|org.springframework.boot|spring-boot-starter-test|2.6.7|
|org.springframework.boot|spring-boot-starter-thymeleaf|2.6.7|
|org.springframework.boot|spring-boot-starter-data-jdbc|2.3.4|
|org.springframework.boot|spring-boot-starter-jdbc|2.6.7|
|org.springframework.boot|spring-boot-starter-validation|2.6.7|
|mysql|mysql-connector-java|8.0.28|
|org.apache.tomcat|tomcat-jdbc|9.0.62|
|nz.net.ultraq.thymeleaf|thymeleaf-layout-dialect|3.0.0|
|com.google.code.gson|gson|2.8.7|
|org.hibernate|hibernate-validator|5.2.4.Final|

<br>
<br>


## view

1. 부트스트랩 무료 템플릿

https://startbootstrap.com/template/heroic-features

<br>

2. CSS, JS 라이브러리

|구분|이름|버전|사용방식|
|--|---|---|---|
|CSS|Bootstrap icons|1.4.1|CDN|
|JS|jQuery|3.5.0|CDN|
|JS|gstatic - charts(google chart)|-|CDN|

<br>
<br>


## 프로그램 구조

1) java 소스

![pos](./images/program1.jpg)

<br>

2) view 소스

![pos](./images/program2.jpg)

<br>
<br>


## 데이터베이스 구조

![pos](./images/database.jpg)

**member_tb** : 회원가입 시 회원의 정보를 저장하는 테이블입니다. 회원의 이메일, 비밀번호, 회원 이름, 회원 역할, 회원 등록 날짜가 저장됩니다.

**product_tb** : 상품 정보가 저장되는 테이블입니다. 상품코드, 상품이름, 상품가격, 상품 재고 수량, 현재 판매여부 정보가 저장됩니다.

**product_history_tb** : 상품 수량 변동 내역 기록이 저장되는 테이블입니다. member_tb와 product_tb를 참조하고, 상품이름, 상품가격, 상품 변동 수량, 날짜, 변동 방법이 저장됩니다

**product_state_history_tb** : 상품 상태 변동 내역 기록이 저장되는 테이블입니다. member_tb와 product_tb를 참조하고, 기존 상품이름, 새로운 상품이름, 기존 상품 가격, 새로운 상품 가격, 상태 변동 구분, 날짜가 저장됩니다.

**sale_tb** : 판매 내역이 저장되는 테이블입니다. member_tb를 참조하고, 총 판매가격, 날짜가 저장됩니다.

**sale_detail_tb** : 판매 상세 내역이 저장되는 테이블입니다. sale_tb, product_tb 테이블을 참조하고, 상품이름, 상품 가격, 판매 수량이 저장됩니다.

**sale_cart_tb** : 계산 대기 상품을 저장하는 테이블입니다. member_tb, product_tb를 참조하고, 계산 할 대기 상품 수량을 의미합니다.

<br>
<br>

## 오픈소스 

1) 테이블 페이징 구현

 https://codepen.io/jaehee/pen/mRmNEX
 
 위 사이트의 소스코드를 기반으로 테이블 페이징을 구현했습니다. 

<br>
<br>

## 기타

1. 로그
스프링 인터셉터를 이용해 로그를 기록하게 함. logs 폴더에 일 단위로 로그 파일이 
생성됨. 로그파일의 최대크기는 1MB임.
