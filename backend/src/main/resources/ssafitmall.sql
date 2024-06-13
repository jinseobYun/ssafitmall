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
