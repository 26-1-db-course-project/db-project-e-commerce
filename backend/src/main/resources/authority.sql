-- DB 사용자 권한 분리 (테이블별)
-- 데이터베이스: ecommerce

-- 일반 사용자 (Spring Boot 앱 서버 전용)
-- DML만 허용, DDL/DCL 차단
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'app_password';
-- 참조 테이블 (읽기만)
GRANT SELECT ON `ecommerce`.activity_status TO 'app_user'@'%';
GRANT SELECT ON `ecommerce`.member_grade TO 'app_user'@'%';
GRANT SELECT ON `ecommerce`.order_status TO 'app_user'@'%';
GRANT SELECT ON `ecommerce`.category TO 'app_user'@'%';
GRANT SELECT ON `ecommerce`.manufacturer TO 'app_user'@'%';
GRANT SELECT ON `ecommerce`.option_type TO 'app_user'@'%';
GRANT SELECT ON `ecommerce`.option_detail TO 'app_user'@'%';

-- 회원 (DELETE 제외 - soft delete로 관리)
GRANT SELECT, INSERT, UPDATE ON `ecommerce`.member TO 'app_user'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON `ecommerce`.delivery_address TO 'app_user'@'%';
GRANT SELECT, INSERT ON `ecommerce`.customer TO 'app_user'@'%';
GRANT SELECT, INSERT ON `ecommerce`.business TO 'app_user'@'%';

-- 상품
GRANT SELECT, INSERT, UPDATE, DELETE ON `ecommerce`.product TO 'app_user'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON `ecommerce`.product_detail TO 'app_user'@'%';
GRANT SELECT, INSERT, DELETE ON `ecommerce`.product_option TO 'app_user'@'%';

-- 장바구니
GRANT SELECT, INSERT, DELETE ON `ecommerce`.cart TO 'app_user'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON `ecommerce`.cart_item TO 'app_user'@'%';

-- 주문
GRANT SELECT, INSERT ON `ecommerce`.orders TO 'app_user'@'%';
GRANT SELECT, INSERT ON `ecommerce`.order_item TO 'app_user'@'%';

-- 리뷰
GRANT SELECT, INSERT, UPDATE, DELETE ON `ecommerce`.review TO 'app_user'@'%';
GRANT SELECT, INSERT ON `ecommerce`.review_report TO 'app_user'@'%';

-- 데이터 분석가 / 마케터 전용 (읽기 전용)
CREATE USER IF NOT EXISTS 'analyst'@'%' IDENTIFIED BY 'analyst_password';
-- 개인정보는 읽기에서 제외 (member, delivery_address 접근 불가)
GRANT SELECT ON `ecommerce`.member_grade TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.activity_status TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.product TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.product_detail TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.category TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.manufacturer TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.orders TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.order_item TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.order_status TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.review TO 'analyst'@'%';
GRANT SELECT ON `ecommerce`.review_report TO 'analyst'@'%';

-- 최고 관리자 (전체 권한)
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin_password';
GRANT ALL PRIVILEGES ON `ecommerce`.* TO 'admin'@'%';

FLUSH PRIVILEGES;
