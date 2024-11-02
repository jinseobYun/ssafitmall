# 💪 SSAFITMALL

삼성 청년 소프트웨어 아카데미에서 실시한 1학기 최종 관통 프로젝트 <details><summary>❓관통 프로젝트</summary>

> 2인이 팀을 구성해 진행하는 웹 개발 프로젝트로 한 학기 동안 공부한 내용을 바탕으로 프로젝트 진행

</details>

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/b74ebbab-7e07-4738-9604-9095201298c5" width="500" height="400"/></center>

## 목차

- 기획 배경
- 기술 스택
- 화면 구성
- 주요 기능
- 아키텍처

## 기획 배경

### 서비스 내용

중고거래 사기 피해 예방을 위한 헬스 용품, 운동 보조제 중고 거래 쇼핑몰로 판매 이용자에게 상품 등록 서비스를 제공하며 구매 이용자에게 상품 주문 및 결제 서비스를 제공한다. 또한 헬스에 막 입문한 이용자를 위해 상품의 정보를 알려주는 챗봇 기능을 제공한다.

### 기획 의도

**🤝 중고 거래 중개를 통한 거래 사기 피해 예방**
디지털 기술의 발전에 따라 온라인 중고 거래 시장의 성장세가 높아지고 있습니다. 한편 하자 있는 상품을 판매하거나, 돈만 받고 물건을 보내지 않는 등 사기성 거래와 같은 중고 거래 사기 피해 또한 증가하고 있습니다. 저희 팀은 중고 거래를 중개하는 플랫폼을 제공하므로서 이러한 문제를 줄이고자 프로젝트를 기획하게 되었습니다.

["안 속을 수 없어"…중고거래 사기는 '진화 중' 출처 : SBS 뉴스 ](https://news.sbs.co.kr/news/endPage.do?news_id=N1007643436)
[“직거래도 두드려보고 송금해야”…‘중고거래 제3자 사기’ 등장 [취재후] 출처 : KBS 뉴스](https://news.kbs.co.kr/news/pc/view/view.do?ncd=7918860)

### 개발 기간

     24.05.16 ~ 24.05.24

### 개발 인원

총 2명

- 윤진섭 ( BackEnd Main - FrontEnd Sub)
- 김수아 ( FrontEnd Main - BackEnd Sub)

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

### 후기

SSAFY ( 삼성 청년 소프트웨어 아카데미 ) 에 들어와서 Spring Boot, My Batis, My SQL, Vue.js 를 공부했고 이 내용을 바탕으로 처음으로 기획부터 개발까지 진행한 프로젝트입니다.

프로젝트를 진행하면서 느낀점은

**어려웠던 점**

1. 이번 프로젝트는 처음으로 아이디어 구상부터 설계, 개발까지 주도한 경험이었습니다. 주제를 정한 후 페어와 함께 여러 개발자 블로그를 참고하며 DB와 API 설계 방법, 그리고 문서 작성법 등을 학습하였고, 이를 바탕으로 열심히 회의하며 직접 설계하고 문서를 작성했습니다. 특히, 처음으로 DB를 설계하고 API를 설계하다 보니 서툰 부분이 많았고, 개발을 진행하면서 테이블 구조와 API를 계속해서 수정하는 일이 빈번하게 발생했습니다. 


2. 개발을 시작할 때 저희 팀은 역할을 백엔드와 프론트엔드로 구분하지 않고 기능별로 분담했습니다. 저는 상품 관련 기능을, 페어는 유저 기능을 먼저 구현하기로 했습니다. 그러나 상품 추가 기능은 판매자 페이지에서 이루어져야 했기 때문에 유저 기능이 필요했습니다. 처음에는 권한 없이 상품 추가 기능을 구현한 후, 페어가 유저 기능을 완성하면 다시 수정하는 방식으로 개발을 진행했는데, 이 과정에서 많은 시간을 소모하게 되었습니다. 또한, 각자 맡은 부분을 개발하고 통합하는 과정에서, 개발 당시에는 알아차리지 못했던 오류들이 통합 후에 발견되었고, 이를 수정하는 데에도 상당한 시간이 소요되었습니다.
이후부턴 백엔드와 프론트엔드 영역으로 나누어 제가 백엔드 쪽을 맡아 프로젝트를 진행하였습니다.



**배운점**
단순히 개발만 잘한다고 되는 것이 아니라, 설계와 기획의 중요성을 실감했습니다. 잘 작성된 설계서는 개발을 훨씬 더 원활하게 진행할 수 있게 한다는 점도 배울 수 있었습니다.

처음부터 명확한 역할 분담이 필요하다는 점도 느꼈습니다. 백엔드와 프론트엔드로 분리하지 않고 기능별로 개발할 경우, 의존성이 있는 기능에 대해 미리  구체적인 계획을 세우지 않으면 효율성이 떨어질 수 있다는 것을 알았습니다.

팀원과의 의사소통을 중요시하며 작업 영역을 명확히 정의해야 프로젝트를 더 효율적으로 진행할 수 있다는 것을 알았습니다.

## 주요 기능

### 메인 페이지

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/81d71dcc-2b0b-4115-b9d1-552d292d914c" width="500" height="400"/></center>

- 상품 카테고리 nav 바
- 프로모션 배너 슬라이더 윈도우
- 관심 등록 수 많은 상품과 최근 등록 상품 노출

### 회원가입 및 로그인

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/63b8fc34-043d-478f-bea7-dffcaf693198" width="500" height="400"/></center>

### 카테고리 별 상품 목록 조회

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/852867f5-ab89-4fa6-bb98-2d53f15bd866" width="500" height="400"/></center>

### 상품 주문 및 결제

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/ce9f17d2-1f70-4f6a-80f8-5412ad029a44" width="500" height="400"/></center>

- 상품 주문 및 주문 상세 페이지
- 카카오페이 결제 API 활용
- 다음 주소 API 활용

### 결제 취소 및 주문 취소

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/f4ece0b7-924e-483b-8bba-adedc4fe18b2" width="500" height="400"/></center>

- 카카오페이 결제 취소 API 활용

### 마이페이지

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/e1727acc-a7a6-4fb3-81eb-fb0574d1bc36" width="500" height="400"/></center>

- 주문 상세 정보
- 등록 상품 상세 정보
- 회원 정보 수정
- 판매자 정보 등록

### 관심 상품

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/d4c81fad-b6b3-4192-a3d6-4eddc1317f41" width="500" height="400"/></center>

### 챗봇

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/da4c98fd-0747-42b5-8632-35e6cdce9a0a" width="500" height="400"/></center>

- Chat GPT API를 활용한 챗봇기능
- 상품 이름을 추출해 부작용,효능 등 상품 정보 질의 프롬프트 또는 가격 비교 질의 프롬프트를 선택해 Chat GPT API를 사용해 질문 후 응답한 답변을 화면에 출력
- 🚨 현재 API KEY 만료로 사용 불가

### 관리자 페이지

#### 상품 관리

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/e290c1ef-4828-4b1c-ab7b-c077621af7c8" width="500" height="400"/></center>

#### 유저 관리

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/df1ef21e-97ca-46d4-8826-71386d3b680f" width="500" height="400"/></center>

### 판매자 페이지

#### 상품 등록

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/e083e80b-711a-4d86-a644-c031e3b5b98b" width="500" height="400"/></center>

#### 상품 수정 및 삭제

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/07680ddb-1592-4426-b365-8acc0938dc05" width="500" height="400"/></center>

### 상품 검색

<center><img src="https://github.com/jinseobYun/ssafitmall/assets/103829767/b89ea457-9621-461a-8ce6-69ce7a4af95b" width="500" height="400"/></center>
