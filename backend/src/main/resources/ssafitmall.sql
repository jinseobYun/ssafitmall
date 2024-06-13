DROP DATABASE ssafitmall_db;
CREATE DATABASE ssafitmall_db;
USE ssafitmall_db;

CREATE TABLE User
(
    user_id       VARCHAR(30) NOT NULL  COMMENT '유저 아이디',
    user_pwd      VARCHAR(50) NOT NULL COMMENT '유저 비밀번호',
    user_name     VARCHAR(20) NOT NULL COMMENT '유저 이름',
    user_phone    VARCHAR(20) UNIQUE NOT NULL COMMENT '유저 전화번호',
    user_email    VARCHAR(50) UNIQUE NOT NULL COMMENT '유저 이메일',
    user_nickname VARCHAR(20) UNIQUE NOT NULL COMMENT '유저 닉네임',
    user_role     VARCHAR(10) NOT NULL DEFAULT "customer" COMMENT '유저 권한',
    user_regdate  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '유저 생성일',
    PRIMARY KEY (user_id)
) COMMENT '유저 정보';

CREATE TABLE Seller
(
    seller_code    INT         NOT NULL AUTO_INCREMENT COMMENT '판매자 코드',
    account_number TEXT        NOT NULL COMMENT '계좌 정보',
    bank           VARCHAR(20) NOT NULL COMMENT '계좌 은행',
    user_id        VARCHAR(30) NOT NULL COMMENT '유저 아이디',
    PRIMARY KEY (seller_code),
    FOREIGN KEY (user_id) REFERENCES User(user_id)  ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '판매자 정보';

CREATE TABLE Category
(
    category_code VARCHAR(30) NOT NULL COMMENT '카테고리 코드',
    category_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (category_code)
) COMMENT '상품 카테고리';

CREATE TABLE Product
(
    product_code  INT         NOT NULL AUTO_INCREMENT COMMENT '상품번호',
    product_thumbnail TEXT NULL COMMENT '썸네일 이미지',
    product_name  VARCHAR(50) NOT NULL COMMENT '상품명',
    product_price INT         NOT NULL COMMENT '가격',
    product_desc  TEXT        NOT NULL COMMENT '상품 설명',
    product_date  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록 날짜',
    product_hist  INT         NOT NULL DEFAULT 0 COMMENT '조회수',
    like_cnt INT NOT NULL DEFAULT 0 COMMENT '관심 상품 등록 횟수',
    category_code VARCHAR(30) NOT NULL COMMENT '카테고리 코드',
    user_id       VARCHAR(30) NOT NULL COMMENT '판매자 아이디',
    is_sold       VARCHAR(10)     NOT NULL DEFAULT "판매중" COMMENT '판매 상태',
    inspection VARCHAR(10)  NOT NULL DEFAULT "검수전" COMMENT '상품 준비 상태',
    PRIMARY KEY (product_code),
    FOREIGN KEY (category_code) REFERENCES Category(category_code)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)  ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '상품 정보';

CREATE TABLE Cart
(
    cart_id          INT         NOT NULL AUTO_INCREMENT COMMENT '장바구니 번호',
    user_id          VARCHAR(30) NOT NULL COMMENT '유저 아이디',
    product_code     INT         NOT NULL COMMENT '상품번호',
    PRIMARY KEY (cart_id),
    UNIQUE (user_id, product_code),
    FOREIGN KEY (user_id) REFERENCES User(user_id)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_code) REFERENCES Product(product_code)  ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '장바구니';

CREATE TABLE Orders
(
    order_number      BIGINT      NOT NULL AUTO_INCREMENT COMMENT '주문번호',
    receiver_name     VARCHAR(20) NOT NULL COMMENT '받는 사람',
    receiver_phone    VARCHAR(20) NOT NULL COMMENT '받는 사람 연락처',
    recipient_address VARCHAR(255) NOT NULL COMMENT '받는 사람 주소',
    create_at         DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '주문 일자',
    delivered_at      DATETIME    NULL COMMENT '배송 일자',
    user_id           VARCHAR(30) NOT NULL COMMENT '유저 아이디',
    delivery_status   VARCHAR(10) NOT NULL DEFAULT "결제대기" COMMENT '주문 현황',
    product_code INT     NOT NULL COMMENT '상품번호',
    is_delivered BOOLEAN NOT NULL DEFAULT FALSE COMMENT '배송 완료 상태',
    PRIMARY KEY (order_number),
    FOREIGN KEY (user_id) REFERENCES User(user_id)  ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (product_code) REFERENCES Product(product_code) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '주문 정보';


CREATE TABLE UserAddress
(
    address_id INT         NOT NULL AUTO_INCREMENT COMMENT '주소 아이디',
    road_addr    VARCHAR(50) NULL COMMENT '도로명 주소',
    extra_road_addr VARCHAR(50) NULL COMMENT '주소 참고 부분',
    zone_code varchar(20) NULL COMMENT '우편 번호',
    jibun_addr VARCHAR(20) NULL COMMENT '지번 주소',
    detail_addr VARCHAR(20) NULL COMMENT '상세주소',
    is_default BOOLEAN     NOT NULL COMMENT '기본배송지 여부',
    user_id    VARCHAR(30) NOT NULL COMMENT '유저 아이디',
    PRIMARY KEY (address_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)  ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '유저 배송지 정보';

CREATE TABLE ImgPath
(
    img_id       INT        NOT NULL AUTO_INCREMENT COMMENT '이미지 아이디',
    original_name  VARCHAR(255) NOT NULL comment '이미지 원본 이름',
    save_name     VARCHAR(255) NOT NULL COMMENT '이미지 저장 이름',
    product_code INT        NOT NULL COMMENT '상품번호',
    PRIMARY KEY (img_id),
    FOREIGN KEY (product_code) REFERENCES Product(product_code) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT '이미지 경로';


CREATE TABLE payment (
                         payment_id int PRIMARY KEY auto_increment,
                         partner_order_id bigint NOT NULL,
                         partner_user_id VARCHAR(30) NOT NULL,
                         item_name TEXT NOT NULL,
                         quantity int NOT NULL,
                         total_amount int NOT NULL,
                         vat_amount int NOT NULL,
                         tax_free_amount int NOT NULL,
                         product_code int NOT NULL,
                         tid TEXT NOT NULL,
                         FOREIGN KEY (partner_order_id) REFERENCES Orders(order_number) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (partner_user_id) REFERENCES `user` (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (product_code) REFERENCES `product` (product_code) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 카테고리 추가
INSERT INTO Category (category_code, category_name) VALUES
                                                        ('ORG', '유기농'),
                                                        ('PRO', '프로틴'),
                                                        ('GAI', '게이너'),
                                                        ('ENE', '에너지'),
                                                        ('AMI', '아미노산'),
                                                        ('DIET', '다이어트'),
                                                        ('NUT', '영양제'),
                                                        ('SHA', '쉐이커'),
                                                        ('HEA', '헬스용품');



INSERT INTO `user` VALUES ('admin','admin','관리자','01012345678','admin@example.com','admin_nick','admin','2024-05-21 10:40:39'),('buyer','buyer','구매자','01099990000','buyer6@example.com','buyernick','customer','2024-05-21 10:40:39'),('seller','seller','판매자','01011112222','seller1@example.com','sellernick','seller','2024-05-21 10:40:39'),('seller2','seller2','seller2','010303030303','seller2@sell','seller2','seller','2024-05-21 10:49:55'),('seller3','seller3','seller3','0130103013','t@t','seller3','seller','2024-05-21 10:55:40'),('seller4','seller4','seller4','030123023','seller4@seller4','seller4','seller','2024-05-21 11:00:25'),('seller5','seller5','seller5','12312213123','seller5@seller5','seller5','seller','2024-05-21 11:05:32'),('seller6','seller6','seller6','seller6','seller6@seller6','seller6','seller','2024-05-21 11:06:16'),('seller7','seller7','seller7','0329389428','seller7@seller7','seller7','seller','2024-05-21 11:06:37'),('seller8','seller8','seller8','2132130231','seller8@seller8','seller8','seller','2024-05-21 11:06:50'),('seller9','seller9','seller9','21399213','seller9@seller9','seller9','seller','2024-05-21 11:07:08');

INSERT INTO `product` VALUES (1,'1716255884865_유기농게이너.png','유기농게이너',12000,'윈스타운 유기농 게이너 싸게 팝니다.','2024-05-21 10:44:44',33,3,'ORG','seller','판매중','검수완료'),(2,'1716255942780_유기농단백질보충제.jpg','유기농프로틴',24000,'프로게이너 유기농 프로틴 거래합니다','2024-05-21 10:45:42',6,0,'ORG','seller','거래중','검수완료'),(3,'1716255998711_유기농식물성단백질파우더.jpg','식물성단백질',30000,'해외 직구한 프로틴인데 운동 접어서 팝니다.','2024-05-21 10:46:38',1,0,'ORG','seller','판매중','검수완료'),(4,'1716256056108_유기농식물성프로틴.jpg','맛있는 식물성 단백질 ㅍㅍ',22000,'너무 맛있는 비건 단백질 팝니다','2024-05-21 10:47:36',3,0,'ORG','seller','판매중','검수완료'),(5,'1716256108133_체중증가용게이너.png','바닐라맛 오리지널 게이너 팝니다~~',33000,'바닐라맛 게이너 사놓고 한번도 안먹었어요','2024-05-21 10:48:28',0,0,'ORG','seller','판매중','검수완료'),(6,'1716256159975_프로게이너식물성단백질.jpg','프로게이너 비건 단백질 팔아요',42000,'프로게이너 비건 단백질 저랑 안맞아서 팝니다.','2024-05-21 10:49:19',0,0,'ORG','seller','판매중','검수완료'),(7,'1716256303213_단밸질바.png','맛있는 단백질바 팔아요',9000,'단백질바 미개봉 8개입 팝니다~','2024-05-21 10:51:43',9,0,'PRO','seller2','판매중','검수완료'),(8,'1716256342197_머슬팜프로틴.jpg','머슬팜 프로틴 초코맛',42000,'머슬팜 mp 프로틴 초코맛 팔아요 운동할때 세번인가 먹고 안먹었어요','2024-05-21 10:52:22',8,0,'PRO','seller2','판매중','검수완료'),(9,'1716256396586_신타식스프로틴.jpg','씬타 프로틴 새상품 사실분',52000,'사놓고 다른 프로틴 선물 받아서 한번도 안먹었어요 ','2024-05-21 10:53:16',1,0,'PRO','seller2','판매중','검수완료'),(10,'1716256449386_쿠키앤크림프로틴.jpg','프로틴 분말형 쿠키앤크림맛 ',10000,'반절정도 남았는데 싸게 사가실 분','2024-05-21 10:54:09',0,0,'PRO','seller2','판매중','검수완료'),(11,'1716256480846_파워쉐이크프로틴.png','김종국의 파워쉐이크',34000,'칼로바이 프로틴 파라요~','2024-05-21 10:54:40',1,0,'PRO','seller2','판매중','검수완료'),(12,'1716256511524_프로틴쿠키.jpg','프로틴 쿠키 6개',5000,'프로틴 쿠키 아침에 먹으면 든든하니 좋아요 6개 남았어요','2024-05-21 10:55:11',0,0,'PRO','seller2','판매중','검수완료'),(13,'1716256619230_리얼매스게이너.jpg','.리얼메스꺼 게이너 사실분',50000,'사놓고 뜯지도않았어요 사가세요','2024-05-21 10:56:59',33,5,'GAI','seller3','판매중','검수완료'),(14,'1716256660867_마이프로틴게이너.png','마프꺼 게이너',45000,'마프 게이너 한번 먹고 안먹었어요 싸게 팝니다 상태 좋아요','2024-05-21 10:57:40',17,0,'GAI','seller3','거래중','검수완료'),(15,'1716256692723_소이프로틴게이너.jpg','소이프로틴 게이너',52000,'소이프로틴 게이너 새상품 팔아요','2024-05-21 10:58:12',1,0,'GAI','seller3','판매중','검수완료'),(16,'1716256733740_익스트림게이너.jpg','익스트림 게이너',30000,'익스트림 게이너 2/3 정도 남은거 싸게 팔아요~','2024-05-21 10:58:53',0,0,'GAI','seller3','판매중','검수완료'),(17,'1716256772731_칼로리몬스터쿠키앤크림.jpg','칼몬 쿠앤크 게이너',54000,'칼몬꺼 쿠앤크 게이너 입니다 효과 좋고 맛있어서 꾸준히 먹어요','2024-05-21 10:59:32',0,0,'GAI','seller3','판매중','검수완료'),(18,'1716256807755_컴뱃게이너.jpg','머슬컴뱃 게이넢ㅍ',50000,'게이너 머슬컴뱃꺼 제일 잘나가는건데 두개 시켜서 하나 팔아요','2024-05-21 11:00:07',0,0,'GAI','seller3','판매중','검수완료'),(19,'1716256895024_bvcaa.jpg','bcaa바닐라',20000,'bcaa 바닐라맛 ','2024-05-21 11:01:35',3,1,'AMI','seller4','판매중','검수완료'),(20,'1716256944454_eaa아미노산.jpg','뉴트라바이오bcaa',18000,'뉴트라 바이오 BCAA 암미노산 입니다 사가세여','2024-05-21 11:02:24',4,0,'AMI','seller4','판매중','검수완료'),(21,'1716256998818_레드비시에이.jpg','레드비씨에이',14000,'레드비씨에이 운동전에 먹으면 좋아요 선물 받은거 있어서 팝니다..','2024-05-21 11:03:18',1,0,'AMI','seller4','판매중','검수완료'),(22,'1716257029404_소다맛bcaa.jpg','오리지널 소다맛 비씨에이',17000,'비씨에이 소다맛 맛있어요 운동할때 먹으면 최고','2024-05-21 11:03:49',0,0,'AMI','seller4','판매중','검수완료'),(23,'1716257080245_엘아르기닌.jpg','L-아르기닌 + 아미노산 영양제',20000,'아르기닌 + 아미노산 운동 영양제입니다~','2024-05-21 11:04:40',0,0,'AMI','seller4','판매중','검수완료'),(24,'1716257116885_콜라게너지.jpg','콜라게너지 아미노산',16000,'콜라게너지 아미노산 입니다. 하나씩 간편하게 먹을 수 잇어서 좋아요 ','2024-05-21 11:05:16',0,0,'AMI','seller4','판매중','검수완료'),(25,'1716257314755_뉴티르다이어트.jpg','뉴티르 다이어트 식품',24000,'다이어트 할때 먹기 좋은 뉴티르 다이어트 보조제 팔아요~','2024-05-21 11:08:34',0,0,'DIET','seller5','판매중','검수완료'),(26,'1716257343434_다이어트쉐이크.jpg','다이어트 쉐이크',24000,'다이어트 할때 먹기 좋은 단백질 쉐이크 팝니다~','2024-05-21 11:09:03',2,0,'DIET','seller5','판매중','검수완료'),(27,'1716257369090_다이어트쉐이크쿠키앤크림.jpg','다이어트쉐이크 쿠앤크',22000,'쿠앤크맛 쉐이크 팔아요 진짜 맛있어요!','2024-05-21 11:09:29',1,0,'DIET','seller5','판매중','검수완료'),(28,'1716257405568_다이어트프로틴딸기.jpg','다이어트 프로틴 새상품',26000,'다이어트 프로틴 딸기맛 팔아요 새상품 입니다!','2024-05-21 11:10:05',0,0,'DIET','seller5','판매중','검수완료'),(29,'1716257435947_코코밥다이어트쉐이크.jpg','코코팝 다이어트 쉐이크',28000,'코코밥 다이어트 쉐이크 팔아요 한번 먹고 안먹었어요 ','2024-05-21 11:10:35',8,0,'DIET','seller5','거래중','검수완료'),(30,'1716257473839_한끼대신오트밀.jpg','한끼 대신 오트밀',10000,'한끼 대신 오트밀 5개 팝니다~ 아침에 식사 대용으로 추천해요','2024-05-21 11:11:13',0,0,'DIET','seller5','판매중','검수완료'),(31,'1716257507474_레드필에너지부스터.jpg','레드필 부스터',8000,'레드필 에너지 부스터 파라요','2024-05-21 11:11:47',1,0,'ENE','seller6','판매중','검수완료'),(32,'1716257544729_리볼드아르기닌.jpg','운동 보조 필수 아르기닌!',25000,'리볼드 아르기닌 입니다운동 전 먹으면 운동할때 좋아요','2024-05-21 11:12:24',5,0,'ENE','seller6','판매중','검수완료'),(33,'1716257592133_마이프로틴크레아틴.jpg','크레아틴 마프꺼 ',41000,'마프 크레아틴 벌크업 할 때 먹으면 좋습니다.','2024-05-21 11:13:12',4,1,'ENE','seller6','판매중','검수완료'),(35,'1716257628861_아르기닌맥스.jpg','아르기닌 맥스 상품 ',20000,'아르기닌 맥스 입니다. 미개봉 상태입니다.','2024-05-21 11:13:48',0,0,'ENE','seller6','판매중','검수완료'),(36,'1716257671480_에너지볼트.jpg','에너지볼트 복분자맛',12000,'10개입 팝니다.','2024-05-21 11:14:31',2,0,'ENE','seller6','판매중','검수완료'),(37,'1716257716480_크레아틴파우더.jpg','크레아틴 파우더',20000,'크레아틴 파우더 팔아요 싸게 사가세요~','2024-05-21 11:15:16',0,0,'ENE','seller6','판매중','검수완료'),(38,'1716257752222_닥터지비타민d.jpg','닥터지 비타민',31000,'닥터지 멀티비타민입니다.','2024-05-21 11:15:52',0,0,'NUT','seller7','판매중','검수완료'),(39,'1716257788911_멀티비타민belif키트. jpg.webp','벨프 멀티비타민 키트 팔아요',52000,'멀티 비타민 키트 미개봉 상품 입니다.','2024-05-21 11:16:28',3,1,'NUT','seller7','판매중','검수완료'),(40,'1716257813332_비타하임발포비타민.jpg','비타하임 발포 비타민',8000,'발포 비타민 팔아요 맛있어요!','2024-05-21 11:16:53',1,0,'NUT','seller7','판매중','검수완료'),(41,'1716257860499_센트룸멀티비타민.jpg','센트룸 비타민 새거',41000,'유명한 센트롬 비타민입니다. 선물 받았는데 먹고잇는거 있어서 팔아요 새상품','2024-05-21 11:17:40',0,0,'NUT','seller7','판매중','검수완료'),(42,'1716257920568_엠비엘멀티비타민.png','MBL 멀티비타민 아이크림',24000,'멀티비타민 아이크림 입니다 ','2024-05-21 11:18:40',0,0,'NUT','seller7','판매중','검수완료'),(43,NULL,'오쏘몰 멀티 비타민',40000,'현대인 필수 멀티비타민 사세요','2024-05-21 11:19:25',1,0,'NUT','seller7','판매중','검수완료'),(44,'1716258009404_마이프로틴신형메탈쉐이커.jpg','마프 신형 메탈 쉐이커 ',17000,'마프꺼 이번에 나온 메탈 쉐이커입니다.','2024-05-21 11:20:09',0,0,'SHA','seller8','판매중','검수완료'),(45,'1716258038790_마이프로틴파워쉐이커.jpg','마프파워쉐이커 미개봉',12000,'미개봉 단백질 쉐이커 팝니다.','2024-05-21 11:20:38',0,0,'SHA','seller8','판매중','검수완료'),(46,'1716258057915_말왕쉐이커.jpg','말왕 쉐이커 팔아요',20000,'새거입니다','2024-05-21 11:20:57',0,0,'SHA','seller8','판매중','검수완료'),(47,'1716258433999_메탈쉐이커.jpg','스테인리스 쉐이커 팔아요~',18000,'메탈 재질이라 닦기 편해요 ','2024-05-21 11:21:22',3,0,'SHA','seller8','판매중','검수완료'),(48,'1716258114172_삼대오백프로틴쉐이커.jpg','삼대오백 쉐이커 ',19000,'삼대오백 쉐이커 미개봉 상품입니다.','2024-05-21 11:21:54',1,0,'SHA','seller8','판매중','검수완료'),(49,'1716258155077_피트니스스타쉐이커.jpg','피트니스 스타 쉐이커',14000,'피스 쉐이커 팔아요 상태 깨끗합니다.','2024-05-21 11:22:35',0,0,'SHA','seller8','판매중','검수완료'),(50,'1716258517104_나이키더플백.jpg','나이키 더플백 ',32000,'상태 완전 좋아요~','2024-05-21 11:23:13',1,0,'HEA','seller9','판매중','검수완료'),(51,'1716258220033_나이키헬스장갑.jpg','나이키 헬스장갑',14000,'나이키 헬스장갑 두번 사용하고 저랑 안맞아서 팝니다','2024-05-21 11:23:40',1,0,'HEA','seller9','판매중','검수완료'),(52,'1716258243516_파워벨트.jpg','파워벨트',15000,'가죽 파워벨트 팔아요~','2024-05-21 11:24:03',0,0,'HEA','seller9','판매중','검수완료'),(53,'1716258273016_폼롤러.jpg','폼롤러 팝니다~',10000,'보라색 폼롤러 팔아요 사이즈 사진 확인하세요','2024-05-21 11:24:33',1,0,'HEA','seller9','판매중','검수완료'),(54,'1716258309052_필라테스요가메트.jpg','요가메트',10000,'요가메트 팔아요 이제 안쓰게 되서 팝니다 상태 사용하는데 문제 없어요','2024-05-21 11:25:09',1,0,'HEA','seller9','판매중','검수완료'),(55,'1716258345016_헬스스트랩.jpg','헬스 스트랩 팔아요',8000,'데드 들때 필요한 스트랩 상태 좋아요~','2024-05-21 11:25:45',2,0,'HEA','seller9','판매중','검수완료'),(56,'1716278662282_image.png','tes',123,'123','2024-05-21 17:04:22',0,0,'ORG','seller','판매중','검수전');

INSERT INTO `imgpath` VALUES (1,'유기농게이너.png','1716255884876_유기농게이너.png',1),(2,'유기농단백질보충제.jpg','1716255942789_유기농단백질보충제.jpg',2),(3,'유기농식물성단백질파우더.jpg','1716255998721_유기농식물성단백질파우더.jpg',3),(4,'유기농식물성프로틴.jpg','1716256056116_유기농식물성프로틴.jpg',4),(5,'체중증가용게이너.png','1716256108140_체중증가용게이너.png',5),(6,'프로게이너식물성단백질.jpg','1716256159983_프로게이너식물성단백질.jpg',6),(7,'단밸질바.png','1716256303221_단밸질바.png',7),(8,'머슬팜프로틴.jpg','1716256342205_머슬팜프로틴.jpg',8),(9,'신타식스프로틴.jpg','1716256396595_신타식스프로틴.jpg',9),(10,'쿠키앤크림프로틴.jpg','1716256449394_쿠키앤크림프로틴.jpg',10),(11,'파워쉐이크프로틴.png','1716256480853_파워쉐이크프로틴.png',11),(12,'프로틴쿠키.jpg','1716256511532_프로틴쿠키.jpg',12),(13,'리얼매스게이너.jpg','1716256619238_리얼매스게이너.jpg',13),(14,'마이프로틴게이너.png','1716256660874_마이프로틴게이너.png',14),(15,'소이프로틴게이너.jpg','1716256692731_소이프로틴게이너.jpg',15),(16,'익스트림게이너.jpg','1716256733748_익스트림게이너.jpg',16),(17,'칼로리몬스터쿠키앤크림.jpg','1716256772739_칼로리몬스터쿠키앤크림.jpg',17),(18,'컴뱃게이너.jpg','1716256807763_컴뱃게이너.jpg',18),(19,'bvcaa.jpg','1716256895032_bvcaa.jpg',19),(20,'eaa아미노산.jpg','1716256944463_eaa아미노산.jpg',20),(21,'레드비시에이.jpg','1716256998825_레드비시에이.jpg',21),(22,'소다맛bcaa.jpg','1716257029411_소다맛bcaa.jpg',22),(23,'엘아르기닌.jpg','1716257080253_엘아르기닌.jpg',23),(24,'콜라게너지.jpg','1716257116893_콜라게너지.jpg',24),(25,'뉴티르다이어트.jpg','1716257314762_뉴티르다이어트.jpg',25),(26,'다이어트쉐이크.jpg','1716257343441_다이어트쉐이크.jpg',26),(27,'다이어트쉐이크쿠키앤크림.jpg','1716257369097_다이어트쉐이크쿠키앤크림.jpg',27),(28,'다이어트프로틴딸기.jpg','1716257405575_다이어트프로틴딸기.jpg',28),(29,'한끼대신오트밀.jpg','1716257473847_한끼대신오트밀.jpg',30),(30,'레드필에너지부스터.jpg','1716257507481_레드필에너지부스터.jpg',31),(31,'리볼드아르기닌.jpg','1716257544736_리볼드아르기닌.jpg',32),(32,'마이프로틴크레아틴.jpg','1716257592140_마이프로틴크레아틴.jpg',33),(33,'아르기닌맥스.jpg','1716257628868_아르기닌맥스.jpg',35),(34,'에너지볼트.jpg','1716257671487_에너지볼트.jpg',36),(35,'크레아틴파우더.jpg','1716257716487_크레아틴파우더.jpg',37),(36,'닥터지비타민d.jpg','1716257752230_닥터지비타민d.jpg',38),(37,'멀티비타민belif키트. jpg.webp','1716257788918_멀티비타민belif키트. jpg.webp',39),(38,'비타하임발포비타민.jpg','1716257813341_비타하임발포비타민.jpg',40),(39,'센트룸멀티비타민.jpg','1716257860508_센트룸멀티비타민.jpg',41),(40,'엠비엘멀티비타민.png','1716257920580_엠비엘멀티비타민.png',42),(41,'오쏘뮬멀티비타민.jpg','1716257965992_오쏘뮬멀티비타민.jpg',43),(42,'마이프로틴신형메탈쉐이커.jpg','1716258009412_마이프로틴신형메탈쉐이커.jpg',44),(43,'마이프로틴파워쉐이커.jpg','1716258038798_마이프로틴파워쉐이커.jpg',45),(44,'말왕쉐이커.jpg','1716258057923_말왕쉐이커.jpg',46),(45,'나이키헬스장갑.jpg','1716258220041_나이키헬스장갑.jpg',51),(46,'파워벨트.jpg','1716258243523_파워벨트.jpg',52),(47,'폼롤러.jpg','1716258273024_폼롤러.jpg',53),(48,'필라테스요가메트.jpg','1716258309059_필라테스요가메트.jpg',54),(49,'헬스스트랩.jpg','1716258345023_헬스스트랩.jpg',55),(50,'메탈쉐이커.jpg','1716258433999_메탈쉐이커.jpg',47),(51,'나이키더플백.jpg','1716258517104_나이키더플백.jpg',50),(52,'image.png','1716278662296_image.png',56);
-- 셀러 추가
INSERT INTO Seller (account_number, bank, user_id) VALUES
    ('111-111-1111', '은행1', 'seller');
