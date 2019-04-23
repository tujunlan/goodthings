/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : goodthings

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 23/04/2019 08:31:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `out_link` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic_link` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `press` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` int(11) NULL DEFAULT NULL,
  `caution` tinyint(1) NOT NULL DEFAULT 0,
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1',
  `enable` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '书单', 1);
INSERT INTO `category` VALUES (2, '音频', 1);
INSERT INTO `category` VALUES (3, '视频', 1);
INSERT INTO `category` VALUES (4, '学生用品', 1);
INSERT INTO `category` VALUES (5, '玩具', 1);

-- ----------------------------
-- Table structure for goods_tag
-- ----------------------------
DROP TABLE IF EXISTS `goods_tag`;
CREATE TABLE `goods_tag`  (
  `goods_id` int(11) NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `tag_id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
  `pic_id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_link` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` blob NULL,
  PRIMARY KEY (`pic_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for popular
-- ----------------------------
DROP TABLE IF EXISTS `popular`;
CREATE TABLE `popular`  (
  `goods_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `owner_num` int(11) NOT NULL DEFAULT 0,
  `approval_num` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`goods_id`, `category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category_id` int(11) NOT NULL,
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '3-6岁', 1, '2019-04-17 09:20:11');
INSERT INTO `tag` VALUES (2, '7-10岁', 1, '2019-04-17 09:20:35');
INSERT INTO `tag` VALUES (3, '11-14岁', 1, '2019-04-17 09:20:56');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat_id` int(11) NULL DEFAULT NULL,
  `qq` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_goods
-- ----------------------------
DROP TABLE IF EXISTS `user_goods`;
CREATE TABLE `user_goods`  (
  `user_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `want_had` tinyint(1) NULL DEFAULT NULL COMMENT '想要0 已有1',
  PRIMARY KEY (`user_id`, `goods_id`, `category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
