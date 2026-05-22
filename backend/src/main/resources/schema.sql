DROP TABLE IF EXISTS review_report;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS product_option;
DROP TABLE IF EXISTS product_detail;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS delivery_address;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS business;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS activity_status;
DROP TABLE IF EXISTS member_grade;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS option_detail;
DROP TABLE IF EXISTS option_type;

-- [활동 상태] 테이블
CREATE TABLE activity_status
(
    activity_status_id BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_name        VARCHAR(20) NOT NULL UNIQUE, -- 'active' | 'suspended' | 'withdrawn'
    report_count       INT         NOT NULL DEFAULT 0
);

-- [회원 등급] 테이블
CREATE TABLE member_grade
(
    grade_id              BIGINT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    grade_name            VARCHAR(20)    NOT NULL UNIQUE, -- 'welcome' | 'silver' | 'gold'
    total_purchase_amount DECIMAL(15, 2) NOT NULL DEFAULT 0
);

-- [사용자] 테이블
CREATE TABLE member
(
    member_id    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login_id     VARCHAR(50)  NOT NULL UNIQUE, -- 기존의 문자열 ID 역할을 할 로그인용 아이디
    password     VARCHAR(255) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20)  NOT NULL,
    status_name  VARCHAR(20)  NOT NULL,
    grade_name   VARCHAR(20)  NOT NULL,
    FOREIGN KEY (status_name) REFERENCES activity_status (status_name)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    FOREIGN KEY (grade_name) REFERENCES member_grade (grade_name)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- [배송주소] 테이블
CREATE TABLE delivery_address
(
    address_id     BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id      BIGINT       NOT NULL,
    city           VARCHAR(50)  NOT NULL,
    district       VARCHAR(50)  NOT NULL,
    detail_address VARCHAR(255) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- [사용자: 고객] 테이블
CREATE TABLE customer
(
    member_id BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- [사용자: 업체] 테이블
CREATE TABLE business
(
    member_id BIGINT NOT NULL PRIMARY KEY,
    FOREIGN KEY (member_id) REFERENCES member (member_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- [장바구니] 테이블
CREATE TABLE cart
(
    member_id BIGINT NOT NULL PRIMARY KEY,

    FOREIGN KEY (member_id)
        REFERENCES member (member_id)
        ON DELETE CASCADE
);

-- [주문상태] 테이블
CREATE TABLE order_status
(
    status_id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL UNIQUE
);

-- [주문] 테이블
-- order by 예약어가 있어서 이름을 orders로 했습니다.
CREATE TABLE orders
(
    order_id         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT       NOT NULL,
    order_date       DATETIME     NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    total_price      BIGINT       NOT NULL,

    FOREIGN KEY (member_id)
        REFERENCES member (member_id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_orders_order_date ON orders (order_date);
CREATE INDEX idx_orders_member_id_order_date ON orders (member_id, order_date);

--[제조업체]
CREATE TABLE manufacturer
(
    manufacturer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name    VARCHAR(50) NOT NULL,
    owner           VARCHAR(40)
);

--[카테고리]
CREATE TABLE category
(
    category_id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

--[상품]
CREATE TABLE product
(
    product_id      BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    manufacturer_id BIGINT,
    product_name    VARCHAR(50) NOT NULL,
    price           BIGINT      NOT NULL,
    category_id     BIGINT      NOT NULL,
    image_url       VARCHAR(100),

    CONSTRAINT check_price CHECK (price >= 0),

    FOREIGN KEY (manufacturer_id)
        REFERENCES manufacturer (manufacturer_id)
        ON DELETE SET NULL,
    FOREIGN KEY (category_id)
        REFERENCES category (category_id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_product_name ON product (product_name);
CREATE INDEX idx_product_price ON product (price);

--[상품상세]
CREATE TABLE product_detail
(
    product_detail_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id        BIGINT NOT NULL,
    stock_quantity    BIGINT NOT NULL,
    surcharge         BIGINT NOT NULL DEFAULT 0,
    sales             BIGINT NOT NULL DEFAULT 0,
    image_url         VARCHAR(100),

    CONSTRAINT check_stock_quantity CHECK (stock_quantity >= 0),
    CONSTRAINT check_surcharge CHECK (surcharge >= 0),
    CONSTRAINT check_sales CHECK (sales >= 0),

    FOREIGN KEY (product_id)
        REFERENCES product (product_id)
        ON DELETE CASCADE
);

-- [주문목록] 테이블
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

--[옵션 종류 (예: 사이즈, 색상)]
CREATE TABLE option_type
(
    option_type_id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_type_name VARCHAR(30) NOT NULL
);

--[옵션 상세 (예: S, M, L / 빨강, 파랑]
CREATE TABLE option_detail
(
    option_detail_id BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_type_id   BIGINT,
    option_value     VARCHAR(30) NOT NULL,

    FOREIGN KEY (option_type_id)
        REFERENCES option_type (option_type_id)
        ON DELETE RESTRICT
);

--[상품옵션 조합 테이블 (복합키 매핑)]
CREATE TABLE product_option
(
    product_detail_id BIGINT NOT NULL,
    option_detail_id  BIGINT NOT NULL,

    FOREIGN KEY (product_detail_id)
        REFERENCES product_detail (product_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (option_detail_id)
        REFERENCES option_detail (option_detail_id)
        ON DELETE RESTRICT,

    PRIMARY KEY (product_detail_id, option_detail_id)
);

-- [장바구니 아이템] 테이블
CREATE TABLE cart_item
(
    cart_item_id      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    member_id         BIGINT NOT NULL,
    product_detail_id BIGINT NOT NULL,
    quantity          BIGINT NOT NULL DEFAULT 1,

    CONSTRAINT check_cart_quantity CHECK (quantity >= 1),
    UNIQUE (member_id, product_detail_id),

    FOREIGN KEY (member_id)
        REFERENCES cart (member_id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_detail_id)
        REFERENCES product_detail (product_detail_id)
        ON DELETE CASCADE
);


-- [리뷰] 테이블
CREATE TABLE review
(
    review_id      BIGINT        NOT NULL AUTO_INCREMENT,
    member_id      BIGINT        NOT NULL,
    product_id     BIGINT        NOT NULL,

    rating         INT           NOT NULL
        CHECK (rating BETWEEN 1 AND 5),

    review_content VARCHAR(1000) NOT NULL,

    created_at     DATETIME      NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    updated_at     DATETIME NULL,

    review_status  VARCHAR(20)   NOT NULL
        DEFAULT 'NORMAL',

    PRIMARY KEY (review_id),

    UNIQUE (member_id, product_id),

    FOREIGN KEY (member_id)
        REFERENCES member (member_id)
        ON DELETE CASCADE,

    FOREIGN KEY (product_id)
        REFERENCES product (product_id)
        ON DELETE CASCADE
);

-- created_at 인덱스 추가
CREATE INDEX idx_review_created_at ON review (created_at);

-- [리뷰 신고 테이블]
CREATE TABLE review_report
(
    report_id          BIGINT       NOT NULL AUTO_INCREMENT,

    review_id          BIGINT       NOT NULL,

    reporter_member_id BIGINT       NOT NULL,

    report_reason      VARCHAR(255) NOT NULL,

    report_date        DATETIME     NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    report_status      VARCHAR(20)  NOT NULL
        DEFAULT 'WAITING',

    PRIMARY KEY (report_id),

    UNIQUE (review_id, reporter_member_id),

    FOREIGN KEY (review_id)
        REFERENCES review (review_id)
        ON DELETE CASCADE,

    FOREIGN KEY (reporter_member_id)
        REFERENCES member (member_id)
        ON DELETE CASCADE
);

-- report_status 인덱스 추가
CREATE INDEX idx_review_report_status ON review_report (report_status);