/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.101.65
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : 192.168.101.65:3306
 Source Schema         : xc1010_content

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 15/10/2022 21:48:45
*/

SET NAMES utf8mb4;
SET
    FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mq_message_history
-- ----------------------------
DROP TABLE IF EXISTS `mq_message_history`;
CREATE TABLE `mq_message_history`
(
    `id`                 bigint                                                  NOT NULL COMMENT '消息id',
    `message_type`       varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '消息类型代码',
    `business_key1`      varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '关联业务信息',
    `business_key2`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
    `business_key3`      varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联业务信息',
    `execute_num`        int UNSIGNED                                            NULL DEFAULT NULL COMMENT '通知次数',
    `state`              char(10) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '处理状态，0:初始，1:成功，2:失败',
    `returnfailure_date` datetime                                                NULL DEFAULT NULL COMMENT '回复失败时间',
    `returnsuccess_date` datetime                                                NULL DEFAULT NULL COMMENT '回复成功时间',
    `returnfailure_msg`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复失败内容',
    `execute_date`       datetime                                                NULL DEFAULT NULL COMMENT '最近通知时间',
    `stage_state1`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL,
    `stage_state2`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL,
    `stage_state3`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL,
    `stage_state4`       char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = DYNAMIC;

SET
    FOREIGN_KEY_CHECKS = 1;
