-- DROP TABLE IF EXISTS member_conn_test;
--
-- CREATE TABLE member_conn_test (
--     member_id INT AUTO_INCREMENT PRIMARY KEY,
--     nickname VARCHAR(50) NOT NULL
-- );

-- 회원
DROP TABLE IF EXISTS member;
CREATE TABLE member (
                        member_id     BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY, -- 로그인할 때도 member_id를 사용할 예정
                        password      VARCHAR(255)  NOT NULL,
                        email         VARCHAR(100)  NOT NULL,
                        phone_number  VARCHAR(20)   NOT NULL,
                        status_name   VARCHAR(20)   NOT NULL,
                        grade_name    VARCHAR(20)   NOT NULL,
                        FOREIGN KEY (status_name) REFERENCES activity_status(status_name)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT,
                        FOREIGN KEY (grade_name) REFERENCES member_grade(grade_name)
                            ON UPDATE CASCADE
                            ON DELETE RESTRICT
-- [주문상태] 테이블
DROP TABLE IF EXISTS order_status;

CREATE TABLE order_status
(
    status_id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE
);

-- [주문] 테이블
DROP TABLE IF EXISTS orders;

-- order by 예약어가 있어서 이름을 orders로 했습니다.
CREATE TABLE orders
(
    order_id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT       NOT NULL,
    order_date       DATETIME(6) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    total_price      BIGINT       NOT NULL,

    FOREIGN KEY (member_id)
        REFERENCES member (member_id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_orders_order_date ON orders (order_date);
CREATE INDEX idx_orders_member_id_order_date ON orders (member_id, order_date);

-- [주문목록] 테이블
DROP TABLE IF EXISTS order_item;

CREATE TABLE order_item
(
    order_item_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    order_id      BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    status_id     BIGINT NOT NULL,
    quantity      BIGINT NOT NULL DEFAULT 1,
    order_price   BIGINT NOT NULL,

    FOREIGN KEY (order_id)
        REFERENCES orders (order_id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES product (product_id)
        ON DELETE RESTRICT,
    FOREIGN KEY (status_id)
        REFERENCES order_status (status_id)
        ON DELETE RESTRICT
);

DROP TABLE IF EXISTS product_option;
DROP TABLE IF EXISTS product_detail;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS option_detail;
DROP TABLE IF EXISTS option_type;

--[제조업체]
CREATE TABLE manufacturer (
    manufacturer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(50)NOT NULL,
    owner VARCHAR(40)
);

-- 고객 회원
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
                          member_id  BIGINT      NOT NULL PRIMARY KEY,
                          FOREIGN KEY (member_id) REFERENCES member(member_id)
                              ON UPDATE CASCADE
                              ON DELETE RESTRICT
);

-- 업체 회원
DROP TABLE IF EXISTS business;
CREATE TABLE business (
                          member_id  BIGINT      NOT NULL PRIMARY KEY,
                          FOREIGN KEY (member_id) REFERENCES member(member_id)
                              ON UPDATE CASCADE
                              ON DELETE RESTRICT
);

-- 배송주소
DROP TABLE IF EXISTS delivery_address;
CREATE TABLE delivery_address (
                                  address_id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  member_id        BIGINT       NOT NULL,
                                  city             VARCHAR(50)  NOT NULL,
                                  district         VARCHAR(50)  NOT NULL,
                                  detail_address   VARCHAR(255) NOT NULL,
                                  FOREIGN KEY (member_id) REFERENCES member(member_id)
                                      ON UPDATE CASCADE
                                      ON DELETE RESTRICT
);

-- 활동 상태
DROP TABLE IF EXISTS activity_status;
CREATE TABLE activity_status (
                                 activity_status_id  BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                 status_name         VARCHAR(20) NOT NULL UNIQUE,  -- 'active' | 'suspended' | 'withdrawn'
                                 report_count        INT         NOT NULL DEFAULT 0
)

-- 회원 등급
DROP TABLE IF EXISTS member_grade;
CREATE TABLE member_grade (
                              grade_id              BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
                              grade_name            VARCHAR(20)    NOT NULL UNIQUE,  -- 'welcome' | 'silver' | 'gold'
                              total_purchase_amount DECIMAL(15,2)  NOT NULL DEFAULT 0
);
