DROP DATABASE IF EXISTS `internet_facing_plus`;

CREATE DATABASE internet_facing_plus DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE internet_facing_plus;

-- external_regulation表

DROP TABLE IF EXISTS `external_regulation`;

CREATE TABLE external_regulation
(
    id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT '主键',
    title VARCHAR(30) NOT NULL COMMENT '法规标题',
    number VARCHAR(20) COMMENT '法规文号',
    type VARCHAR(20) NOT NULL COMMENT '外规类别',
    publishing_department VARCHAR(20) NOT NULL COMMENT '发文部门',
    effectiveness_level INT NOT NULL COMMENT '效力等级',
    release_date DATETIME NOT NULL COMMENT '发布日期',
    implementation_date DATETIME NOT NULL COMMENT '实施日期',
    interpretation_department VARCHAR(20) COMMENT '解读部门',
    input_person VARCHAR(20) NOT NULL COMMENT '录入人',
    input_date DATETIME NOT NULL COMMENT '录入时间',
    text_path VARCHAR(260) NOT NULL COMMENT '正文文件路径',
    state INT NOT NULL COMMENT '法规是否发布,0为未发布,1为发布',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- user表

DROP TABLE IF EXISTS `user`;

CREATE TABLE user
(
    id INT UNIQUE NOT NULL AUTO_INCREMENT COMMENT '主键',
    username VARCHAR(30) UNICODE NOT NULL COMMENT '用户名',
    password VARCHAR(30) NOT NULL COMMENT '密码',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建默认数据

INSERT INTO `external_regulation`
VALUES (1,'测试法规1','','测试类别1','部门1',1,'2021-04-29 18:37:10','2021-04-29 18:37:10','部门2','张三','2021-04-28 18:37:10','test1',0),
       (2,'测试法规2','','测试类别2','部门2',1,'2021-04-29 18:37:10','2021-04-29 18:37:10','部门1','张三','2021-04-28 18:37:10','test2',1);

INSERT INTO `user`
VALUES (1,'root','111111');