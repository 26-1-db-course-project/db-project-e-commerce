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