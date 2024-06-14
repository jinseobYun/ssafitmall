# 💪 SSAFITMALL

삼성 청년 소프트웨어 아카데미에서 실시한 1학기 최종 관통 프로젝트 <details><summary>❓관통 프로젝트</summary>

> 2인이 팀을 구성해 진행하는 웹 개발 프로젝트로 한 학기 동안 공부한 내용을 바탕으로 프로젝트 진행

</details>

<center><img src="localhost_5173_.png" width="500" height="400"/></center>

---

## 목차

- 기획 배경
- 기술 스택
- 화면 구성
- 주요 기능
- 아키텍처

---

## 기획 배경

> ❓ 관통 프로젝트란 2인이 팀을 구성해 진행하는 명세서 기반 웹 개발 프로젝트로 한 학기 동안 공부한 내용을 바탕으로 프로젝트 진행

### 개요

### 서비스 내용

중고거래 사기 피해 예방을 위한 헬스 용품, 운동 보조제 중고 거래 쇼핑몰로 판매 이용자에게 상품 등록 서비스를 제공하며 구매 이용자에게 상품 주문 및 결제 서비스를 제공한다. 또한 헬스에 막 입문한 이용자를 위해 상품의 정보를 알려주는 챗봇 기능을 제공한다.

### 기획 의도

- **🤝 중고 거래 중개를 통한 거래 사기 피해 예방**
  디지털 기술의 발전에 따라 온라인 중고 거래 시장의 성장세가 높아지고 있다. 한편 하자 있는 상품을 판매하거나, 돈만 받고 물건을 보내지 않는 사기성 거래 등 중고 거래 사기 피해 역시 증가하고 있다. 중고 거래를 중개하는 플랫폼을 제공하므로서 이러한 문제를 줄이고자 프로젝트를 기획하게 되었다.
  ["안 속을 수 없어"…중고거래 사기는 '진화 중' 출처 : SBS 뉴스 ](https://news.sbs.co.kr/news/endPage.do?news_id=N1007643436)
  [“직거래도 두드려보고 송금해야”…‘중고거래 제3자 사기’ 등장 [취재후] 출처 : KBS 뉴스](https://news.kbs.co.kr/news/pc/view/view.do?ncd=7918860)

### 개발 기간

     24.05.16 ~ 24.05.24

### 개발 인원

총 2명

- 윤진섭 ( BackEnd Main - FrontEnd Sub)
- 김수아 ( FrontEnd Main - BackEnd Sub)

---

### 기술 스택

#### Frontend

- Language : JavaScript
- Library & Framework : Vue.js, Pinia, Axios, BootStrap

#### Backend

- Language : Java
- Library & Framework : SpringBoot
- Database : MySQL
- SQL-Mapper : Mybatis

### Tools

- Github, Figma, Notion

---

## 주요 기능

---

### 메인 페이지

![main](main.gif)

- 상품 카테고리 nav 바
- 프로모션 배너 슬라이더 윈도우
- 관심 등록 수 많은 상품과 최근 등록 상품 노출

### 회원가입 및 로그인

![login](signup_login.gif)

### 카테고리 별 상품 목록 조회

![카테고리별 상품](category.gif)

### 상품 주문 및 결제

![payment](PAYMENT_ORDER.gif)

- 상품 주문 및 주문 상세 페이지
- 카카오페이 결제 API 활용
- 다음 주소 API 활용

### 결제 취소 및 주문 취소

![canclepayment](depayment_deorder.gif)

- 카카오페이 결제 취소 API 활용

### 마이페이지

![mypage](mypage.gif)

- 주문 상세 정보
- 등록 상품 상세 정보
- 회원 정보 수정
- 판매자 정보 등록

### 관심 상품

![like](likebtn.gif)

### 챗봇

![chatbot](chatbot.gif)

- Chat GPT API를 활용한 챗봇기능
- 상품 이름을 추출해 부작용,효능 등 상품 정보 질의 프롬프트 또는 가격 비교 질의 프롬프트를 선택해 Chat GPT API를 사용해 질문 후 응답한 답변을 화면에 출력
- 🚨 현재 API KEY 만료로 사용 불가

### 관리자 페이지

#### 상품 관리

![admin_product](admin_product.gif)

#### 유저 관리

![user](admin_user.gif)

### 판매자 페이지

#### 상품 등록

![addproduct](product_add.gif)

#### 상품 수정 및 삭제

![updateProduct](productUpdate.gif)

### 상품 검색

![search](search.gif)

---
