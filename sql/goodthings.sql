/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : goodthings

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2019-04-24 19:09:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) NOT NULL,
  `out_link` varchar(1000) NOT NULL,
  `pic_link` varchar(1000) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `press` varchar(255) DEFAULT NULL,
  `desc` int(11) DEFAULT NULL,
  `caution` tinyint(1) NOT NULL DEFAULT '0',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL DEFAULT '1',
  `enable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '书单', '1');
INSERT INTO `category` VALUES ('2', '音频', '1');
INSERT INTO `category` VALUES ('3', '视频', '1');
INSERT INTO `category` VALUES ('4', '学生用品', '1');
INSERT INTO `category` VALUES ('5', '玩具', '1');

-- ----------------------------
-- Table structure for goods_tag
-- ----------------------------
DROP TABLE IF EXISTS `goods_tag`;
CREATE TABLE `goods_tag` (
  `goods_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `tag_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of goods_tag
-- ----------------------------

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `pic_id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_link` varchar(1000) DEFAULT NULL,
  `image` blob,
  PRIMARY KEY (`pic_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of picture
-- ----------------------------

-- ----------------------------
-- Table structure for popular
-- ----------------------------
DROP TABLE IF EXISTS `popular`;
CREATE TABLE `popular` (
  `goods_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `owner_num` int(11) NOT NULL DEFAULT '0',
  `approval_num` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`goods_id`,`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of popular
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) NOT NULL,
  `category_id` int(11) NOT NULL,
  `p_tag_id` int(11) NOT NULL DEFAULT '0',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('2', '3-6岁', '1', '0', '2019-04-17 09:20:35');
INSERT INTO `tag` VALUES ('3', '7-10岁', '1', '0', '2019-04-17 09:20:56');
INSERT INTO `tag` VALUES ('4', '11-14岁', '1', '0', '2019-04-24 08:17:36');
INSERT INTO `tag` VALUES ('6', '情绪', '1', '2', '2019-04-24 08:20:31');
INSERT INTO `tag` VALUES ('7', '动物', '1', '2', '2019-04-24 08:21:04');
INSERT INTO `tag` VALUES ('8', '语文', '1', '3', '2019-04-24 08:23:20');
INSERT INTO `tag` VALUES ('9', '数学', '1', '3', '2019-04-24 08:23:50');
INSERT INTO `tag` VALUES ('10', '语文', '1', '4', '2019-04-24 08:23:59');
INSERT INTO `tag` VALUES ('11', '数学', '1', '4', '2019-04-24 08:24:08');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(20) DEFAULT NULL,
  `wechat_id` int(11) DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `nick_name` varchar(30) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_goods
-- ----------------------------
DROP TABLE IF EXISTS `user_goods`;
CREATE TABLE `user_goods` (
  `user_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `want_had` tinyint(1) DEFAULT NULL COMMENT '想要0 已有1',
  PRIMARY KEY (`user_id`,`goods_id`,`category_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user_goods
-- ----------------------------
