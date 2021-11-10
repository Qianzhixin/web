# DROP DATABASE IF EXISTS `internet_facing_plus`;

# CREATE DATABASE internet_facing_plus DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE internet_facing_plus;

SET FOREIGN_KEY_CHECKS = 0;

-- user表

DROP TABLE IF EXISTS `user`;

CREATE TABLE user
(
    id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(30) NOT NULL COMMENT '用户名',
    password VARCHAR(30) NOT NULL COMMENT '密码',
    name VARCHAR(30) NOT NULL COMMENT '录入人姓名',
    CONSTRAINT user_pk
        PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- external_regulation表

DROP TABLE IF EXISTS `external_regulation`;

CREATE TABLE external_regulation
(
    id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT '主键',
    title VARCHAR(255) NOT NULL COMMENT '法规标题',
    number VARCHAR(30) COMMENT '法规文号',
    type VARCHAR(30) NOT NULL COMMENT '外规类别',
    publishing_department VARCHAR(50) NOT NULL COMMENT '发文部门',
    effectiveness_level VARCHAR(30) NOT NULL COMMENT '效力等级',
    release_date DATETIME NOT NULL COMMENT '发布日期',
    implementation_date DATETIME COMMENT '实施日期',
    interpretation_department VARCHAR(50) COMMENT '解读部门',
    input_person_id INT NOT NULL COMMENT '录入人id',
    input_date DATETIME NOT NULL COMMENT '录入时间',
    text_path VARCHAR(255) NOT NULL COMMENT '正文文件路径',
    state INT NOT NULL COMMENT '法规是否发布,0为未发布,1为发布',
    CONSTRAINT external_regulation_pk
        PRIMARY KEY (id),
        FOREIGN KEY (input_person_id) REFERENCES user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE `external_regulation` ADD INDEX idx_title (`title`);
ALTER TABLE `external_regulation` ADD INDEX idx_publishing_department (`publishing_department`);
ALTER TABLE `external_regulation` ADD INDEX idx_effectiveness_level (`effectiveness_level`);
ALTER TABLE `external_regulation` ADD INDEX idx_number (`number`);
ALTER TABLE `external_regulation` ADD INDEX idx_release_date (`release_date`);
ALTER TABLE `external_regulation` ADD INDEX idx_implementation_date (`implementation_date`);

-- 创建默认数据

INSERT INTO `user`
VALUES (1,'root','111111','张三');

INSERT INTO `external_regulation`
VALUES (1,'测试法规1','','测试类别1','部门1','1','2021-04-29 18:37:10','2021-04-29 18:37:10','部门2',1,'2021-04-28 18:37:10','测试法规1.doc',0),
       (2,'测试法规2','','测试类别2','部门2','1','2021-04-29 18:37:10','2021-04-29 18:37:10','部门1',1,'2021-04-28 18:37:10','测试法规2.doc',1);