/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 01/04/2020 13:30:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `notifier` bigint(0) NOT NULL COMMENT '通知发送方',
  `receiver` bigint(0) NOT NULL COMMENT '通知接收方',
  `outerId` bigint(0) NOT NULL COMMENT '问题或者评论的id',
  `type` int(0) NULL DEFAULT NULL COMMENT '通知的类型：评论、点赞、收藏',
  `gmt_create` bigint(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
