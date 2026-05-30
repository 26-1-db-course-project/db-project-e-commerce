-- DB_NAME에는 DATABASE 이름 넣으면 됨

-- 일반 사용자 (Spring Boot 앱 서버 전용)
--    DML만 허용, DDL/DCL 차단
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'app_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON `{DB_NAME}`.* TO 'app_user'@'%';

-- 데이터 분석가 / 마케터 전용 (읽기 전용)
CREATE USER IF NOT EXISTS 'analyst'@'%' IDENTIFIED BY 'analyst_password';
GRANT SELECT ON `{DB_NAME}`.* TO 'analyst'@'%';

-- 최고 관리자 (전체 권한)
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin_password';
GRANT ALL PRIVILEGES ON `{DB_NAME}`.* TO 'admin'@'%';

FLUSH PRIVILEGES;