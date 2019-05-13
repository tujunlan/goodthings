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

 Date: 06/05/2019 08:28:42
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
) ENGINE = InnoDB AUTO_INCREMENT = 1101 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '德国精选科学图画书: 肚子里有个火车站', 'http://www.xiaohuasheng.cn/mbookn/bf7fc3a591d151ba', 'http://img.xiaohuasheng.cn/1/Book/20180426113146513.jpg?imageView2/1/w/160/h/240', '（德） 鲁斯曼·安娜 著/绘；（德）舒尔茨·史蒂芬 绘；张振 译', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (2, '德国精选科学图画书: 牙齿大街的新鲜事', 'http://www.xiaohuasheng.cn/mbookn/6c504ad90552ef60', 'http://img.xiaohuasheng.cn/1/Book/20180426113117060.jpg?imageView2/1/w/160/h/240', '（德） 鲁斯曼·安娜 著/绘；王丛兵译', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (3, '你看起来好像很好吃', 'http://www.xiaohuasheng.cn/mbookn/9af836e3c8acb6ed', 'http://img.xiaohuasheng.cn/1/Book/20180426113124732.jpg?imageView2/1/w/160/h/240', '（日）宫西达也 文/图 杨文 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (4, '猜猜我有多爱你', 'http://www.xiaohuasheng.cn/mbookn/8790b3c11a409ffa', 'http://img.xiaohuasheng.cn/1/Book/20180426112718779.jpg?imageView2/1/w/160/h/240', '（爱尔兰） 山姆·麦克布雷尼 著；【英】安妮塔·婕朗 绘；梅子涵 译', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (5, '大卫,不可以', 'http://www.xiaohuasheng.cn/mbookn/70baf53c5fbd0d2c', 'http://img.xiaohuasheng.cn/1/Book/20180426113145763.jpg?imageView2/1/w/160/h/240', '（美） 大卫·香农 著；余治莹 译', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (6, '爷爷一定有办法', 'http://www.xiaohuasheng.cn/mbookn/c9cf8b80b8c82588', 'http://img.xiaohuasheng.cn/1/Book/20180426112735310.jpg?imageView2/1/w/160/h/240', 'fei bi ji dong man', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (7, '不一样的卡梅拉1: 我想去看海', 'http://www.xiaohuasheng.cn/mbookn/fd9ac5b473227490', 'http://img.xiaohuasheng.cn/1/Book/20180426113117326.jpg?imageView2/1/w/160/h/240', '（法） 克利斯提昂·约里波瓦 著；（法） 克利斯提昂·艾利施 绘；郑迪蔚 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (8, '100层的房子系列', 'http://www.xiaohuasheng.cn/mbookn/578a5c9fe822e74d', 'http://img.xiaohuasheng.cn/1/Book/20161227162444131.jpg?imageView2/1/w/160/h/240', '（日） 岩井俊雄 著', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (9, '好饿的毛毛虫', 'http://www.xiaohuasheng.cn/mbookn/4a8ec2703528df0c', 'http://img.xiaohuasheng.cn/Douban/Book/25234508-1_w_3.jpg?imageView2/1/w/160/h/240', '（美） 艾瑞·卡尔 著；郑明进 译', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (10, '斯凯瑞金色童书·第一辑', 'http://www.xiaohuasheng.cn/mbookn/a38a0a868c58e0a8', 'http://img.xiaohuasheng.cn/1/Book/20180427092124022.jpg?imageView2/1/w/160/h/240', '（美）斯凯瑞，李晓评（译）', '贵州人民出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (11, '母鸡萝丝去散步', 'http://www.xiaohuasheng.cn/mbookn/63505a1df3e94f18', 'http://img.xiaohuasheng.cn/1/Book/20180426113118795.jpg?imageView2/1/w/160/h/240', '（美） 佩特·哈群斯 著', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (12, '彩虹色的花', 'http://www.xiaohuasheng.cn/mbookn/7066b70b34d12ed7', 'http://img.xiaohuasheng.cn/124244/Book/20190107172444885.jpg?imageView2/1/w/160/h/240', '麦克·格雷涅茨', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (13, '你真好', 'http://www.xiaohuasheng.cn/mbookn/a4941a4424778556', 'http://img.xiaohuasheng.cn/1/Book/20180426112732842.jpg?imageView2/1/w/160/h/240', '宫西达也 著；蒲蒲兰 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (14, '我是霸王龙', 'http://www.xiaohuasheng.cn/mbookn/df09bd7132ef31d2', 'http://img.xiaohuasheng.cn/1/Book/20180426112720873.jpg?imageView2/1/w/160/h/240', '（日）宫西达也 文/图；杨文 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (15, '蚯蚓的日记', 'http://www.xiaohuasheng.cn/mbookn/e121ddec33ee2aac', 'http://img.xiaohuasheng.cn/1/Book/20180426112656529.jpg?imageView2/1/w/160/h/240', '（美） 朵琳·克罗宁 著；（美） 哈利·布里斯 绘；陈宏淑 译', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (16, '地下100层的房子', 'http://www.xiaohuasheng.cn/mbookn/87a823f31c6a688f', 'http://img.xiaohuasheng.cn/1/Book/20180426113127248.jpg?imageView2/1/w/160/h/240', '（日） 岩井俊雄 著；刘洋 译', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (17, '鸭子骑车记', 'http://www.xiaohuasheng.cn/mbookn/e04b4ca0827eb414', 'http://img.xiaohuasheng.cn/1/Book/20180426113121888.jpg?imageView2/1/w/160/h/240', '（美） 大卫·夏农 著；彭懿 译', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (18, '蚂蚁和西瓜', 'http://www.xiaohuasheng.cn/mbookn/2c19cca0758009aa', 'http://img.xiaohuasheng.cn/1/Book/20180426113122092.jpg?imageView2/1/w/160/h/240', '田村茂, 蒲蒲兰 (Translator)', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (19, '小黑鱼', 'http://www.xiaohuasheng.cn/mbookn/72a6f43d0d05ee2c', 'http://img.xiaohuasheng.cn/1/Book/20180426112655092.jpg?imageView2/1/w/160/h/240', '（美） 李欧·李奥尼 著；彭懿 译', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (20, '遇到你, 真好', 'http://www.xiaohuasheng.cn/mbookn/c38c28b358a501d9', 'http://img.xiaohuasheng.cn/1/Book/20180426112735467.jpg?imageView2/1/w/160/h/240', '宫西达也 著；蒲蒲兰 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (21, '妈妈, 买绿豆!', 'http://www.xiaohuasheng.cn/mbookn/87900708f269d64b', 'http://img.xiaohuasheng.cn/1/Book/20180426113119560.jpg?imageView2/1/w/160/h/240', '曾阳晴 著；万华国 绘', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (22, '活了100万次的猫', 'http://www.xiaohuasheng.cn/mbookn/fa3dc517d7728788', 'http://img.xiaohuasheng.cn/Douban/Book/20416433-1_w_2.jpg?imageView2/1/w/160/h/240', '（日） 佐野洋子 著；唐亚明 译', '接力出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (23, '抱抱', 'http://www.xiaohuasheng.cn/mbookn/93049201262cb52f', 'http://img.xiaohuasheng.cn/1/Book/20180426113117967.jpg?imageView2/1/w/160/h/240', '[英]Jez Alborough ，上谊编辑部 译', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (24, '100层的房子', 'http://www.xiaohuasheng.cn/mbookn/885383c5fb40ce74', 'http://img.xiaohuasheng.cn/1/Book/20180426113120326.jpg?imageView2/1/w/160/h/240', '[日]岩井俊雄著，于海洋译', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (25, '鳄鱼怕怕 牙医怕怕', 'http://www.xiaohuasheng.cn/mbookn/59ee2253f2d352d8', 'http://img.xiaohuasheng.cn/1/Book/20180426112740857.jpg?imageView2/1/w/160/h/240', 'Taro Gomi', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (26, '可爱的鼠小弟1', 'http://www.xiaohuasheng.cn/mbookn/9f79f0502ace6bcb', 'http://img.xiaohuasheng.cn/124244/Book/20181008212250426.jpg?imageView2/1/w/160/h/240', '（日）中江嘉男/文 （日）上野纪子/图 赵静 文纪子 /译', '南海出版公司', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (27, '是谁嗯嗯在我的头上', 'http://www.xiaohuasheng.cn/mbookn/5f9bb635541d7bb7', 'http://img.xiaohuasheng.cn/1/Book/20180426113119029.jpg?imageView2/1/w/160/h/240', '（德） 维尔纳·霍尔茨瓦特 著；（德） 沃尔夫·埃布鲁赫 （Wolf Erlbruch 绘；方素珍 译', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (28, '神奇校车动画版', 'http://www.xiaohuasheng.cn/mbookn/6db47627d1c13095', 'http://img.xiaohuasheng.cn/124244/Book/20190223193114650.jpg?imageView2/1/w/160/h/240', '乔安娜柯尔 著,布鲁斯迪根 绘 , 漆仰平 译,漆仰平（译）', '贵州人民出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (29, '月亮的味道', 'http://www.xiaohuasheng.cn/mbookn/a8f452eee6f76860', 'http://img.xiaohuasheng.cn/1/Book/20180426113119763.jpg?imageView2/1/w/160/h/240', '（波兰） 麦克·格雷涅茨 著；漪然，彭懿 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (30, '爷爷一定有办法', 'http://www.xiaohuasheng.cn/mbookn/c473bd186b108f6e', 'http://img.xiaohuasheng.cn/1/Book/20180426113120576.jpg?imageView2/1/w/160/h/240', '（加） 菲比·吉尔曼 著；宋珮 译', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (31, '我妈妈', 'http://www.xiaohuasheng.cn/mbookn/7b698e5ea4fa166e', 'http://img.xiaohuasheng.cn/1/Book/20180426113145951.jpg?imageView2/1/w/160/h/240', '〔英〕安东尼·布朗 文／图；余治莹 译', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (32, '呀! 屁股', 'http://www.xiaohuasheng.cn/mbookn/d371c4581ac911de', 'http://img.xiaohuasheng.cn/1/Book/20180426113126342.jpg?imageView2/1/w/160/h/240', 'Andersen', '希望出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (33, '不一样的卡梅拉3: 我想有个弟弟', 'http://www.xiaohuasheng.cn/mbookn/3194dca71e695567', 'http://img.xiaohuasheng.cn/1/Book/20180426113120748.jpg?imageView2/1/w/160/h/240', '（法） 克利斯提昂·约里波瓦 著；（法） 克利斯提昂·艾利施 绘；郑迪蔚 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (34, '第一次上街买东西', 'http://www.xiaohuasheng.cn/mbookn/d406be16f0773004', 'http://img.xiaohuasheng.cn/1/Book/20161227160636804.jpg?imageView2/1/w/160/h/240', '（日）筒井赖子 著，（日）林明子 绘，彭懿 季颖 译', '新星出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (35, '海底100层的房子', 'http://www.xiaohuasheng.cn/mbookn/629b1663c65fa414', 'http://img.xiaohuasheng.cn/1/Book/20180426112719248.jpg?imageView2/1/w/160/h/240', '（日） 岩井俊雄 著；肖潇 译', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (36, '逃家小兔', 'http://www.xiaohuasheng.cn/mbookn/ae1850f7aa06375', 'http://img.xiaohuasheng.cn/124244/Book/20180719144400010.jpg?imageView2/1/w/160/h/240', 'Margaret Wise Brown, Clement Hurd (Illustrator)', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (37, '不睡觉世界冠军', 'http://www.xiaohuasheng.cn/mbookn/6f822d8f29856c0f', 'http://img.xiaohuasheng.cn/1/Book/20180426112714967.jpg?imageView2/1/w/160/h/240', 'Sean Taylor , 几米', '新星出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (38, '大脚丫跳芭蕾', 'http://www.xiaohuasheng.cn/mbookn/4d16804e95b1a4a4', 'http://img.xiaohuasheng.cn/1/Book/20180426112715826.jpg?imageView2/1/w/160/h/240', '（美） 埃米·扬 著；柯倩华 译', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (39, '安的种子', 'http://www.xiaohuasheng.cn/mbookn/15cca2a8d65806da', 'http://img.xiaohuasheng.cn/1/Book/20180426112741623.jpg?imageView2/1/w/160/h/240', '王早早 著；黄丽 绘', '海燕出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (40, '100层的巴士', 'http://www.xiaohuasheng.cn/mbookn/e2f70957f0146284', 'http://img.xiaohuasheng.cn/1/Book/20180426112655607.jpg?imageView2/1/w/160/h/240', 'Mike Smith', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (41, '谁藏起来了', 'http://www.xiaohuasheng.cn/mbookn/e785d074be732d1a', 'http://img.xiaohuasheng.cn/124244/Book/20190317112853020.jpg?imageView2/1/w/160/h/240', '大西悟 文/图；蒲蒲兰 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (42, '不一样的卡梅拉2: 我想有颗星星', 'http://www.xiaohuasheng.cn/mbookn/106f7c1ed7f9015c', 'http://img.xiaohuasheng.cn/1/Book/20180426113121467.jpg?imageView2/1/w/160/h/240', '（法） 克利斯提昂·约里波瓦 著；（法） 克利斯提昂·艾利施 绘；郑迪蔚 译', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (43, '乌鸦面包店系列', 'http://www.xiaohuasheng.cn/mbookn/e492d8cbc3306a15', 'http://img.xiaohuasheng.cn/1/Book/20180426112907951.jpg?imageView2/1/w/160/h/240', '（日）加古里子 著,（日）猿渡静子 译', '新星出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (44, '石头汤', 'http://www.xiaohuasheng.cn/mbookn/d328264c8c717ca4', 'http://img.xiaohuasheng.cn/1/Book/20161227160722491.jpg?imageView2/1/w/160/h/240', 'Jon J. Muth', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (45, '斯凯瑞金色童书(第1辑): 忙忙碌碌镇', 'http://www.xiaohuasheng.cn/mbookn/ef38cbc402e58ee9', 'http://img.xiaohuasheng.cn/1/Book/20180426112657763.jpg?imageView2/1/w/160/h/240', 'Richard Scarry', '贵州人民出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (46, '14只老鼠系列', 'http://www.xiaohuasheng.cn/mbookn/d511b69648df0d2c', 'http://img.xiaohuasheng.cn/1/Book/20161227161336538.jpg?imageView2/1/w/160/h/240', '岩村和朗 著', '接力出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (47, '巴巴爸爸经典系列: 巴巴爸爸和圣诞礼物', 'http://www.xiaohuasheng.cn/mbookn/b738414bda3f702e', 'http://img.xiaohuasheng.cn/1/Book/20180426112742310.jpg?imageView2/1/w/160/h/240', '【法】安娜特·缇森，德鲁斯·泰勒/著；谢逢蓓 /译', '接力出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (48, '大卫上学去', 'http://www.xiaohuasheng.cn/mbookn/84a47e7a3700d80c', 'http://img.xiaohuasheng.cn/1/Book/20180426113118560.jpg?imageView2/1/w/160/h/240', '文/图：（美）大卫·香农：翻译：余治莹', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (49, '田鼠阿佛', 'http://www.xiaohuasheng.cn/mbookn/10aa8fcaf7368d4', 'http://img.xiaohuasheng.cn/1/Book/20180426112738607.jpg?imageView2/1/w/160/h/240', '（美）李欧·李奥尼 文/图；阿甲 译', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (50, '神奇校车图画版: 气候大挑战', 'http://www.xiaohuasheng.cn/mbookn/d0ea832f4ae3d398', 'http://img.xiaohuasheng.cn/403519/Book/20190228135118.jpg?imageView2/1/w/160/h/240', 'Joanna Cole', '贵州人民出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (51, '图书馆狮子', 'http://www.xiaohuasheng.cn/mbookn/4068d3c1e171a903', 'http://img.xiaohuasheng.cn/1/Book/20180426112759123.jpg?imageView2/1/w/160/h/240', '米歇尔·努森（Knudsen.M.） 编；凯文·霍克斯（Hawkes.K.） 绘；周逸芬 译', '中国少年儿童出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (52, '一园青菜成了精', 'http://www.xiaohuasheng.cn/mbookn/ab16668882224160', 'http://img.xiaohuasheng.cn/1/Book/20180426112716451.jpg?imageView2/1/w/160/h/240', 'Anonymous 北方童谣', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (53, '爱心树', 'http://www.xiaohuasheng.cn/mbookn/3c95f6ef261a2f4', 'http://img.xiaohuasheng.cn/1/Book/20180426112902529.jpg?imageView2/1/w/160/h/240', 'Shel Silverstein', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (54, '云朵面包', 'http://www.xiaohuasheng.cn/mbookn/b5a8e78e29fb4160', 'http://img.xiaohuasheng.cn/1/Book/20180426112758029.jpg?imageView2/1/w/160/h/240', 'Hui-na Paek', '接力出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (55, '不一样的卡梅拉4: 我去找回太阳', 'http://www.xiaohuasheng.cn/mbookn/d19dbd4756809ffa', 'http://img.xiaohuasheng.cn/1/Book/20180426113122263.jpg?imageView2/1/w/160/h/240', 'Christian Jolibois', '二十一世纪出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (56, '小黑鱼', 'http://www.xiaohuasheng.cn/mbookn/be952f2dbc7c4028', 'http://img.xiaohuasheng.cn/Douban/Book/s2797764.jpg?imageView2/1/w/160/h/240', 'Leo Lionni', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (57, '大卫惹麻烦', 'http://www.xiaohuasheng.cn/mbookn/f8f81cf8d0ed696e', 'http://img.xiaohuasheng.cn/1/Book/20180426113118201.jpg?imageView2/1/w/160/h/240', '文/图：（美）大卫·香农；余治莹 /译', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (58, '小威向前冲', 'http://www.xiaohuasheng.cn/mbookn/ff05572a08717a70', 'http://img.xiaohuasheng.cn/1/Book/20180426112727967.jpg?imageView2/1/w/160/h/240', '（英） 艾伦 著；李小强 译', '贵州人民出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (59, '菲菲生气了', 'http://www.xiaohuasheng.cn/mbookn/d50013d67eb1a5c8', 'http://img.xiaohuasheng.cn/1/Book/20180426113123342.jpg?imageView2/1/w/160/h/240', '（美） 莫莉·卞 著；李坤珊 译', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (60, '看里面低幼版第1辑: 揭秘机场', 'http://www.xiaohuasheng.cn/mbookn/2067738f2ca207e8', 'http://img.xiaohuasheng.cn/1/Book/20180426112731279.jpg?imageView2/1/w/160/h/240', '（英） 罗布·利奥伊德·琼斯 著；（英） 斯特凡诺·托涅蒂 绘；荣信文化 译', '未来出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (61, '晚安, 大猩猩', 'http://www.xiaohuasheng.cn/mbookn/a81202f131d9ff8e', 'http://img.xiaohuasheng.cn/1/Book/20180426112715638.jpg?imageView2/1/w/160/h/240', '（美） 佩吉·拉特曼 著；爱心树 译', '南海出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (62, '花婆婆', 'http://www.xiaohuasheng.cn/mbookn/83167e06c5e7bf3f', 'http://img.xiaohuasheng.cn/1/Book/20180426113125420.jpg?imageView2/1/w/160/h/240', 'Barbara Cooney, 方索珍 (Translator)', '河北教育出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (63, '长颈鹿不会跳舞', 'http://www.xiaohuasheng.cn/mbookn/bd7267fc1f21b7cc', 'http://img.xiaohuasheng.cn/1/Book/20180426112746998.jpg?imageView2/1/w/160/h/240', 'Giles Andreae', '北京科学技术出版社', NULL, 0, '2019-05-05 07:36:45', NULL);
INSERT INTO `book` VALUES (64, '驴小弟变石头', 'http://www.xiaohuasheng.cn/mbookn/776ff7f6f1688bd2', 'http://img.xiaohuasheng.cn/1/Book/20180426112955732.jpg?imageView2/1/w/160/h/240', '（美） 威廉·史塔克 著；张剑鸣 译', '明天出版社', NULL, 0, '2019-05-05 07:36:45', NULL);

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
-- Records of goods_tag
-- ----------------------------
INSERT INTO `goods_tag` VALUES (1, 1, 2);
INSERT INTO `goods_tag` VALUES (2, 1, 2);
INSERT INTO `goods_tag` VALUES (3, 1, 2);
INSERT INTO `goods_tag` VALUES (4, 1, 2);
INSERT INTO `goods_tag` VALUES (5, 1, 2);
INSERT INTO `goods_tag` VALUES (6, 1, 2);
INSERT INTO `goods_tag` VALUES (7, 1, 2);
INSERT INTO `goods_tag` VALUES (8, 1, 2);
INSERT INTO `goods_tag` VALUES (9, 1, 2);
INSERT INTO `goods_tag` VALUES (10, 1, 2);
INSERT INTO `goods_tag` VALUES (11, 1, 2);
INSERT INTO `goods_tag` VALUES (12, 1, 2);
INSERT INTO `goods_tag` VALUES (13, 1, 2);
INSERT INTO `goods_tag` VALUES (14, 1, 2);
INSERT INTO `goods_tag` VALUES (15, 1, 2);
INSERT INTO `goods_tag` VALUES (16, 1, 2);
INSERT INTO `goods_tag` VALUES (17, 1, 2);
INSERT INTO `goods_tag` VALUES (18, 1, 2);
INSERT INTO `goods_tag` VALUES (19, 1, 2);
INSERT INTO `goods_tag` VALUES (20, 1, 2);
INSERT INTO `goods_tag` VALUES (21, 1, 2);
INSERT INTO `goods_tag` VALUES (22, 1, 2);
INSERT INTO `goods_tag` VALUES (23, 1, 2);
INSERT INTO `goods_tag` VALUES (24, 1, 2);
INSERT INTO `goods_tag` VALUES (25, 1, 2);
INSERT INTO `goods_tag` VALUES (26, 1, 2);
INSERT INTO `goods_tag` VALUES (27, 1, 2);
INSERT INTO `goods_tag` VALUES (28, 1, 2);
INSERT INTO `goods_tag` VALUES (29, 1, 2);
INSERT INTO `goods_tag` VALUES (30, 1, 2);
INSERT INTO `goods_tag` VALUES (31, 1, 2);
INSERT INTO `goods_tag` VALUES (32, 1, 2);
INSERT INTO `goods_tag` VALUES (33, 1, 2);
INSERT INTO `goods_tag` VALUES (34, 1, 2);
INSERT INTO `goods_tag` VALUES (35, 1, 2);
INSERT INTO `goods_tag` VALUES (36, 1, 2);
INSERT INTO `goods_tag` VALUES (37, 1, 2);
INSERT INTO `goods_tag` VALUES (38, 1, 2);
INSERT INTO `goods_tag` VALUES (39, 1, 2);
INSERT INTO `goods_tag` VALUES (40, 1, 2);
INSERT INTO `goods_tag` VALUES (41, 1, 2);
INSERT INTO `goods_tag` VALUES (42, 1, 2);
INSERT INTO `goods_tag` VALUES (43, 1, 2);
INSERT INTO `goods_tag` VALUES (44, 1, 2);
INSERT INTO `goods_tag` VALUES (45, 1, 2);
INSERT INTO `goods_tag` VALUES (46, 1, 2);
INSERT INTO `goods_tag` VALUES (47, 1, 2);
INSERT INTO `goods_tag` VALUES (48, 1, 2);
INSERT INTO `goods_tag` VALUES (49, 1, 2);
INSERT INTO `goods_tag` VALUES (50, 1, 2);
INSERT INTO `goods_tag` VALUES (51, 1, 2);
INSERT INTO `goods_tag` VALUES (52, 1, 2);
INSERT INTO `goods_tag` VALUES (53, 1, 2);
INSERT INTO `goods_tag` VALUES (54, 1, 2);
INSERT INTO `goods_tag` VALUES (55, 1, 2);
INSERT INTO `goods_tag` VALUES (56, 1, 2);
INSERT INTO `goods_tag` VALUES (57, 1, 2);
INSERT INTO `goods_tag` VALUES (58, 1, 2);
INSERT INTO `goods_tag` VALUES (59, 1, 2);
INSERT INTO `goods_tag` VALUES (60, 1, 2);
INSERT INTO `goods_tag` VALUES (61, 1, 2);
INSERT INTO `goods_tag` VALUES (62, 1, 2);
INSERT INTO `goods_tag` VALUES (63, 1, 2);
INSERT INTO `goods_tag` VALUES (64, 1, 2);

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
-- Records of popular
-- ----------------------------
INSERT INTO `popular` VALUES (1, 1, 16965, 2100);
INSERT INTO `popular` VALUES (2, 1, 16168, 1671);
INSERT INTO `popular` VALUES (3, 1, 9531, 1295);
INSERT INTO `popular` VALUES (4, 1, 8512, 1228);
INSERT INTO `popular` VALUES (5, 1, 28193, 1080);
INSERT INTO `popular` VALUES (6, 1, 5946, 1025);
INSERT INTO `popular` VALUES (7, 1, 19287, 931);
INSERT INTO `popular` VALUES (8, 1, 697, 874);
INSERT INTO `popular` VALUES (9, 1, 22298, 826);
INSERT INTO `popular` VALUES (10, 1, 1074, 739);
INSERT INTO `popular` VALUES (11, 1, 14691, 699);
INSERT INTO `popular` VALUES (12, 1, 11117, 674);
INSERT INTO `popular` VALUES (13, 1, 5965, 667);
INSERT INTO `popular` VALUES (14, 1, 6747, 617);
INSERT INTO `popular` VALUES (15, 1, 9401, 613);
INSERT INTO `popular` VALUES (16, 1, 11366, 585);
INSERT INTO `popular` VALUES (17, 1, 10896, 570);
INSERT INTO `popular` VALUES (18, 1, 12919, 546);
INSERT INTO `popular` VALUES (19, 1, 9351, 546);
INSERT INTO `popular` VALUES (20, 1, 5457, 475);
INSERT INTO `popular` VALUES (21, 1, 12873, 470);
INSERT INTO `popular` VALUES (22, 1, 9774, 452);
INSERT INTO `popular` VALUES (23, 1, 13684, 445);
INSERT INTO `popular` VALUES (24, 1, 13099, 435);
INSERT INTO `popular` VALUES (25, 1, 4980, 434);
INSERT INTO `popular` VALUES (26, 1, 6477, 431);
INSERT INTO `popular` VALUES (27, 1, 15960, 430);
INSERT INTO `popular` VALUES (28, 1, 5098, 426);
INSERT INTO `popular` VALUES (29, 1, 12074, 424);
INSERT INTO `popular` VALUES (30, 1, 14003, 403);
INSERT INTO `popular` VALUES (31, 1, 28833, 394);
INSERT INTO `popular` VALUES (32, 1, 9760, 358);
INSERT INTO `popular` VALUES (33, 1, 17696, 344);
INSERT INTO `popular` VALUES (34, 1, 1609, 344);
INSERT INTO `popular` VALUES (35, 1, 8480, 343);
INSERT INTO `popular` VALUES (36, 1, 4639, 324);
INSERT INTO `popular` VALUES (37, 1, 8939, 316);
INSERT INTO `popular` VALUES (38, 1, 8916, 299);
INSERT INTO `popular` VALUES (39, 1, 5787, 298);
INSERT INTO `popular` VALUES (40, 1, 8827, 296);
INSERT INTO `popular` VALUES (41, 1, 12353, 291);
INSERT INTO `popular` VALUES (42, 1, 17643, 290);
INSERT INTO `popular` VALUES (43, 1, 2502, 290);
INSERT INTO `popular` VALUES (44, 1, 1570, 286);
INSERT INTO `popular` VALUES (45, 1, 10327, 285);
INSERT INTO `popular` VALUES (46, 1, 518, 254);
INSERT INTO `popular` VALUES (47, 1, 5984, 244);
INSERT INTO `popular` VALUES (48, 1, 18318, 239);
INSERT INTO `popular` VALUES (49, 1, 5887, 237);
INSERT INTO `popular` VALUES (50, 1, 10016, 236);
INSERT INTO `popular` VALUES (51, 1, 3744, 234);
INSERT INTO `popular` VALUES (52, 1, 8468, 229);
INSERT INTO `popular` VALUES (53, 1, 2651, 220);
INSERT INTO `popular` VALUES (54, 1, 2882, 220);
INSERT INTO `popular` VALUES (55, 1, 17332, 218);
INSERT INTO `popular` VALUES (56, 1, 238, 217);
INSERT INTO `popular` VALUES (57, 1, 18676, 210);
INSERT INTO `popular` VALUES (58, 1, 7346, 209);
INSERT INTO `popular` VALUES (59, 1, 12824, 205);
INSERT INTO `popular` VALUES (60, 1, 7316, 199);
INSERT INTO `popular` VALUES (61, 1, 8067, 197);
INSERT INTO `popular` VALUES (62, 1, 13091, 195);
INSERT INTO `popular` VALUES (63, 1, 4482, 195);
INSERT INTO `popular` VALUES (64, 1, 1989, 194);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category_id` int(11) NOT NULL,
  `p_tag_id` int(11) NOT NULL DEFAULT 0,
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (2, '3-6岁', 1, 0, '2019-04-17 09:20:35');
INSERT INTO `tag` VALUES (3, '7-10岁', 1, 0, '2019-04-17 09:20:56');
INSERT INTO `tag` VALUES (4, '11-14岁', 1, 0, '2019-04-24 08:17:36');
INSERT INTO `tag` VALUES (6, '情绪', 1, 2, '2019-04-24 08:20:31');
INSERT INTO `tag` VALUES (7, '动物', 1, 2, '2019-04-24 08:21:04');
INSERT INTO `tag` VALUES (8, '语文', 1, 3, '2019-04-24 08:23:20');
INSERT INTO `tag` VALUES (9, '数学', 1, 3, '2019-04-24 08:23:50');
INSERT INTO `tag` VALUES (10, '语文', 1, 4, '2019-04-24 08:23:59');
INSERT INTO `tag` VALUES (11, '数学', 1, 4, '2019-04-24 08:24:08');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `wechat_id` int(11) NULL DEFAULT NULL,
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pwd` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, NULL, NULL, 'admin', NULL, '111111');

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
