-- DROP TABLE IF EXISTS member_conn_test;
--
-- CREATE TABLE member_conn_test (
--     member_id INT AUTO_INCREMENT PRIMARY KEY,
--     nickname VARCHAR(50) NOT NULL
-- );

-- 회원
DROP TABLE IF EXISTS member;
CREATE TABLE member (
                        member_id     BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        login_id      VARCHAR(50)   NOT NULL UNIQUE, -- 기존의 문자열 ID 역할을 할 로그인용 아이디
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
