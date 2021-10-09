DROP DATABASE IF EXISTS `internet_facing_plus`;

CREATE DATABASE internet_facing_plus DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE internet_facing_plus;

DROP TABLE IF EXISTS `ExternalRegulation`;

CREATE TABLE ExternalRegulation
(
    id INT NOT NULL AUTO_INCREMENT COMMENT '主键',
    title VARCHAR(30) NOT NULL COMMENT '法规标题',
    number VARCHAR(20) COMMENT '法规文号',
    type VARCHAR(20) NOT NULL COMMENT '外规类别',
    publishing_department VARCHAR(20) NOT NULL COMMENT '发文部门',
    effectiveness_level VARCHAR(20) NOT NULL COMMENT '效力等级',
    release_date DATETIME NOT NULL COMMENT '发布日期',
    implementation_date DATETIME NOT NULL COMMENT '实施日期',
    interpretation_department VARCHAR(20) COMMENT '解读部门',
    input_person VARCHAR(20) NOT NULL COMMENT '录入人',
    input_date DATETIME NOT NULL COMMENT '录入时间',
    text_path VARCHAR(260) NOT NULL COMMENT '正文文件路径',
    state ENUM('published','unpublished'),
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;