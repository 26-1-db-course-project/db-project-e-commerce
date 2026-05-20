-- [주문상태] 데이터
INSERT INTO order_status (status_name)
VALUES ('주문접수'),
       ('결제완료'),
       ('배송중'),
       ('배송완료'),
       ('주문취소');


-- -- [주문] 더미 데이터 (총 8개의 주문)
-- -- 시간은 DATETIME(6) 포맷에 맞추고, member_id는 1~5번 회원이 주문했다고 가정했습니다.
-- INSERT INTO orders (member_id, order_date, shipping_address, total_price)
-- VALUES (1, '2026-05-10 10:30:00.000000', '서울특별시 강남구 테헤란로 123', 50000),    -- 주문 1
--        (2, '2026-05-12 14:15:22.000000', '서울특별시 송파구 올림픽로 456', 120000),   -- 주문 2
--        (1, '2026-05-14 09:00:11.000000', '서울특별시 강남구 테헤란로 123', 30000),    -- 주문 3
--        (3, '2026-05-15 18:45:50.000000', '경기도 성남시 분당구 판교역로 789', 200000), -- 주문 4
--        (4, '2026-05-16 22:10:05.000000', '부산광역시 해운대구 센텀중앙로 12', 15000),   -- 주문 5
--        (5, '2026-05-18 11:20:30.000000', '대구광역시 중구 동성로 34', 80000),       -- 주문 6
--        (2, '2026-05-19 16:05:45.000000', '서울특별시 송파구 올림픽로 456', 45000),    -- 주문 7
--        (1, '2026-05-20 08:30:00.000000', '서울특별시 서대문구 이화여대길 52', 350000);
-- -- 주문 8
--
-- -- 3. [주문목록] 더미 데이터 (총 10개의 주문 상품)
-- -- 총결제금액(quantity * order_price)이 위 orders 테이블의 total_price와 정확히 일치하도록 논리적으로 짰습니다.
-- INSERT INTO order_item (order_id, product_id, status_id, quantity, order_price)
-- VALUES
-- -- 1번 주문 (총 50,000원 = 25,000 * 2개) - 배송완료(5)
-- (1, 1, 5, 2, 25000),
--
-- -- 2번 주문 (총 120,000원 = 100,000*1 + 20,000*1) - 배송완료(5)
-- (2, 2, 5, 1, 100000),
-- (2, 3, 5, 1, 20000),
--
-- -- 3번 주문 (총 30,000원 = 30,000 * 1개) - 취소/환불(6)
-- (3, 4, 6, 1, 30000),
--
-- -- 4번 주문 (총 200,000원 = 50,000 * 4개) - 배송중(4)
-- (4, 5, 4, 4, 50000),
--
-- -- 5번 주문 (총 15,000원 = 15,000 * 1개) - 배송준비중(3)
-- (5, 6, 3, 1, 15000),
--
-- -- 6번 주문 (총 80,000원 = 40,000 * 2개) - 배송준비중(3)
-- (6, 7, 3, 2, 40000),
--
-- -- 7번 주문 (총 45,000원 = 15,000 * 3개) - 결제완료(2)
-- (7, 8, 2, 3, 15000),
--
-- -- 8번 주문 (총 350,000원 = 300,000*1 + 50,000*1) - 결제대기(1)
-- (8, 9, 1, 1, 300000),
-- (8, 10, 1, 1, 50000);

-- [제조업체]
INSERT INTO manufacturer (manufacturer_id, company_name, owner)
VALUES (1, 'ZARA', '자라 소유주');
INSERT INTO manufacturer (manufacturer_id, company_name, owner)
VALUES (2, 'H&M', 'H&M 소유주');
INSERT INTO manufacturer (manufacturer_id, company_name, owner)
VALUES (3, 'UNIQLO', 'UNIQLO 소유주');
INSERT INTO manufacturer (manufacturer_id, company_name, owner)
VALUES (4, 'Nike', 'Nike 소유주');
INSERT INTO manufacturer (manufacturer_id, company_name, owner)
VALUES (5, 'Adidas', 'Adidas 소유주');

-- [카테고리]
INSERT INTO category (category_id, category_name)
VALUES (1, '자켓');
INSERT INTO category (category_id, category_name)
VALUES (2, '티셔츠');
INSERT INTO category (category_id, category_name)
VALUES (3, '바지');
INSERT INTO category (category_id, category_name)
VALUES (4, '스커트');
INSERT INTO category (category_id, category_name)
VALUES (5, '아우터');
INSERT INTO category (category_id, category_name)
VALUES (6, '니트');

-- [상품]
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (1, 1, '트위드 자켓', 150000, 1, 'http://img/zara-tweed-jacket');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (2, 1, '플리츠 미디 스커트', 89000, 4, 'http://img/zara-pleats-skirt');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (3, 1, '리넨 블라우스', 65000, 2, 'http://img/zara-linen-blouse');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (4, 2, '오버사이즈 티셔츠', 29900, 2, 'http://img/hm-oversized-tee');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (5, 2, '슬림핏 청바지', 49900, 3, 'http://img/hm-slim-jeans');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (6, 3, '히트텍 롱슬리브', 19900, 2, 'http://img/uniqlo-heattech');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (7, 3, '후리스 집업', 59900, 5, 'http://img/uniqlo-fleece-zip');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (8, 4, '드라이핏 반팔', 45000, 2, 'http://img/nike-dri-fit');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (9, 5, '트랙 재킷', 89000, 1, 'http://img/adidas-track-jacket');
INSERT INTO product (product_id, manufacturer_id, product_name, price, category_id, image_url)
VALUES (10, 5, '조거 팬츠', 69000, 3, 'http://img/adidas-jogger-pants');

-- [옵션 종류]
INSERT INTO option_type (option_type_id, option_type_name)
VALUES (1, '사이즈');
INSERT INTO option_type (option_type_id, option_type_name)
VALUES (2, '색상');

-- [옵션 상세]
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (1, 1, 'XS');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (2, 1, 'S');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (3, 1, 'M');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (4, 1, 'L');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (5, 1, 'XL');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (6, 2, '화이트');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (7, 2, '블랙');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (8, 2, '네이비');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (9, 2, '베이지');
INSERT INTO option_detail (option_detail_id, option_type_id, option_value)
VALUES (10, 2, '그레이');

-- [상품상세]
-- 트위드 자켓 (product_id=1)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (1, 1, 30, 0, 10, 'http://img/zara-tweed-jacket-white-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (2, 1, 25, 0, 20, 'http://img/zara-tweed-jacket-white-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (3, 1, 20, 0, 15, 'http://img/zara-tweed-jacket-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (4, 1, 15, 0, 5, 'http://img/zara-tweed-jacket-black-l');
-- 플리츠 미디 스커트 (product_id=2)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (5, 2, 40, 0, 30, 'http://img/zara-pleats-skirt-beige-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (6, 2, 35, 0, 25, 'http://img/zara-pleats-skirt-beige-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (7, 2, 30, 0, 20, 'http://img/zara-pleats-skirt-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (8, 2, 20, 0, 10, 'http://img/zara-pleats-skirt-navy-l');
-- 리넨 블라우스 (product_id=3)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (9, 3, 50, 0, 40, 'http://img/zara-linen-blouse-white-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (10, 3, 45, 0, 35, 'http://img/zara-linen-blouse-white-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (11, 3, 40, 0, 30, 'http://img/zara-linen-blouse-navy-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (12, 3, 30, 0, 15, 'http://img/zara-linen-blouse-beige-l');
-- 오버사이즈 티셔츠 (product_id=4)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (13, 4, 100, 0, 80, 'http://img/hm-oversized-tee-white-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (14, 4, 90, 0, 70, 'http://img/hm-oversized-tee-gray-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (15, 4, 80, 0, 60, 'http://img/hm-oversized-tee-black-l');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (16, 4, 60, 0, 40, 'http://img/hm-oversized-tee-gray-xl');
-- 슬림핏 청바지 (product_id=5)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (17, 5, 60, 0, 50, 'http://img/hm-slim-jeans-black-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (18, 5, 55, 0, 45, 'http://img/hm-slim-jeans-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (19, 5, 50, 0, 40, 'http://img/hm-slim-jeans-navy-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (20, 5, 40, 0, 30, 'http://img/hm-slim-jeans-navy-l');
-- 히트텍 롱슬리브 (product_id=6)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (21, 6, 200, 0, 150, 'http://img/uniqlo-heattech-white-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (22, 6, 180, 0, 140, 'http://img/uniqlo-heattech-white-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (23, 6, 160, 0, 120, 'http://img/uniqlo-heattech-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (24, 6, 140, 0, 100, 'http://img/uniqlo-heattech-gray-l');
-- 후리스 집업 (product_id=7)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (25, 7, 70, 0, 55, 'http://img/uniqlo-fleece-gray-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (26, 7, 65, 0, 50, 'http://img/uniqlo-fleece-gray-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (27, 7, 60, 0, 45, 'http://img/uniqlo-fleece-navy-l');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (28, 7, 50, 0, 35, 'http://img/uniqlo-fleece-navy-xl');
-- 드라이핏 반팔 (product_id=8)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (29, 8, 80, 0, 65, 'http://img/nike-dri-fit-black-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (30, 8, 75, 0, 60, 'http://img/nike-dri-fit-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (31, 8, 70, 0, 55, 'http://img/nike-dri-fit-gray-l');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (32, 8, 55, 0, 40, 'http://img/nike-dri-fit-gray-xl');
-- 트랙 재킷 (product_id=9)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (33, 9, 45, 0, 35, 'http://img/adidas-track-jacket-black-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (34, 9, 40, 0, 30, 'http://img/adidas-track-jacket-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (35, 9, 35, 0, 25, 'http://img/adidas-track-jacket-navy-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (36, 9, 30, 0, 20, 'http://img/adidas-track-jacket-navy-l');
-- 조거 팬츠 (product_id=10)
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (37, 10, 55, 0, 45, 'http://img/adidas-jogger-black-s');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (38, 10, 50, 0, 40, 'http://img/adidas-jogger-black-m');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (39, 10, 45, 0, 35, 'http://img/adidas-jogger-black-l');
INSERT INTO product_detail (product_detail_id, product_id, stock_quantity, surcharge, sales, image_url)
VALUES (40, 10, 35, 0, 25, 'http://img/adidas-jogger-gray-xl');

-- [상품옵션 조합]
-- 트위드 자켓: pd1(S/화이트), pd2(M/화이트), pd3(M/블랙), pd4(L/블랙)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (1, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (1, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (2, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (2, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (3, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (3, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (4, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (4, 7);
-- 플리츠 미디 스커트: pd5(S/베이지), pd6(M/베이지), pd7(M/블랙), pd8(L/네이비)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (5, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (5, 9);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (6, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (6, 9);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (7, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (7, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (8, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (8, 8);
-- 리넨 블라우스: pd9(S/화이트), pd10(M/화이트), pd11(M/네이비), pd12(L/베이지)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (9, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (9, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (10, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (10, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (11, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (11, 8);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (12, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (12, 9);
-- 오버사이즈 티셔츠: pd13(S/화이트), pd14(M/그레이), pd15(L/블랙), pd16(XL/그레이)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (13, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (13, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (14, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (14, 10);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (15, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (15, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (16, 5);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (16, 10);
-- 슬림핏 청바지: pd17(S/블랙), pd18(M/블랙), pd19(M/네이비), pd20(L/네이비)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (17, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (17, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (18, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (18, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (19, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (19, 8);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (20, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (20, 8);
-- 히트텍 롱슬리브: pd21(S/화이트), pd22(M/화이트), pd23(M/블랙), pd24(L/그레이)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (21, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (21, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (22, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (22, 6);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (23, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (23, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (24, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (24, 10);
-- 후리스 집업: pd25(S/그레이), pd26(M/그레이), pd27(L/네이비), pd28(XL/네이비)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (25, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (25, 10);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (26, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (26, 10);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (27, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (27, 8);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (28, 5);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (28, 8);
-- 드라이핏 반팔: pd29(S/블랙), pd30(M/블랙), pd31(L/그레이), pd32(XL/그레이)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (29, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (29, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (30, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (30, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (31, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (31, 10);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (32, 5);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (32, 10);
-- 트랙 재킷: pd33(S/블랙), pd34(M/블랙), pd35(M/네이비), pd36(L/네이비)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (33, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (33, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (34, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (34, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (35, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (35, 8);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (36, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (36, 8);
-- 조거 팬츠: pd37(S/블랙), pd38(M/블랙), pd39(L/블랙), pd40(XL/그레이)
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (37, 2);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (37, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (38, 3);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (38, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (39, 4);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (39, 7);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (40, 5);
INSERT INTO product_option (product_detail_id, option_detail_id) VALUES (40, 10);

