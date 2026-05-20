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

--[카테고리]
CREATE TABLE category(
    category_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

--[상품]
CREATE TABLE product (
    product_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    manufacturer_id BIGINT,
    product_name VARCHAR(50) NOT NULL,
    price BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    image_url VARCHAR(100),

    CONSTRAINT check_price CHECK(price >= 0),

    FOREIGN KEY (manufacturer_id)
        REFERENCES manufacturer(manufacturer_id)
        ON DELETE SET NULL,
    FOREIGN KEY (category_id)
        REFERENCES category(category_id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_product_name ON product(product_name);
CREATE INDEX idx_product_price ON product(price);

--[상품상세]
CREATE TABLE product_detail (
    product_detail_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    stock_quantity BIGINT NOT NULL,
    surcharge BIGINT NOT NULL DEFAULT 0,
    sales BIGINT NOT NULL DEFAULT 0,
    image_url VARCHAR(100),

    CONSTRAINT check_stock_quantity CHECK(stock_quantity >= 0),
    CONSTRAINT check_surcharge CHECK(surcharge >= 0),
    CONSTRAINT check_sales CHECK(sales >= 0),

    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_product_id ON product_detail(product_id);

--[옵션 종류 (예: 사이즈, 색상)]
CREATE TABLE option_type (
    option_type_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_type_name VARCHAR(30) NOT NULL
);

--[옵션 상세 (예: S, M, L / 빨강, 파랑]
CREATE TABLE option_detail (
    option_detail_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    option_type_id BIGINT,
    option_value VARCHAR(30) NOT NULL,

    FOREIGN KEY (option_type_id)
        REFERENCES option_type(option_type_id)
        ON DELETE RESTRICT
);

--[상품옵션 조합 테이블 (복합키 매핑)]
CREATE TABLE product_option (
    product_detail_id BIGINT NOT NULL,
    option_detail_id BIGINT NOT NULL,

    UNIQUE(product_detail_id, option_detail_id),

    FOREIGN KEY (product_detail_id)
        REFERENCES product_detail(product_detail_id)
        ON DELETE CASCADE,
    FOREIGN KEY (option_detail_id)
        REFERENCES option_detail(option_detail_id)
        ON DELETE RESTRICT,

    PRIMARY KEY (product_detail_id, option_detail_id)
);
