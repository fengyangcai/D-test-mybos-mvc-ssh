/*
Navicat MySQL Data Transfer

Source Server         : 我的mysql
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : newbos

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-09-02 09:08:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `areacode` varchar(100) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `distrcit` varchar(255) DEFAULT NULL,
  `postcode` varchar(100) DEFAULT NULL,
  `citycode` varchar(255) DEFAULT NULL,
  `shortcode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of t_area
-- ----------------------------
INSERT INTO `t_area` VALUES ('3', '020', '广东省', '广州市', '天河', '510000', '020', 'th');
INSERT INTO `t_area` VALUES ('5', '020', '广东省', '广州市', '黄村', '510000', 'guangzhou', 'GDGZH');
INSERT INTO `t_area` VALUES ('6', '020', '广东省', '广州市', '棠下', '510000', 'guangzhou', 'GDGZtx');
INSERT INTO `t_area` VALUES ('7', '020', '广东省', '广州市', '五山', '510000', 'guangzhou', 'GDGZW');
INSERT INTO `t_area` VALUES ('11', '021', '广东省', '深圳市', '宝安区', '512121', '021', 'GDSZ');

-- ----------------------------
-- Table structure for t_courier
-- ----------------------------
DROP TABLE IF EXISTS `t_courier`;
CREATE TABLE `t_courier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courier_num` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `telephone` varchar(100) DEFAULT NULL,
  `pda` varchar(100) DEFAULT NULL,
  `check_pwd` varchar(100) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `standard_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_courier_standard` (`standard_id`),
  CONSTRAINT `FK_courier_standard` FOREIGN KEY (`standard_id`) REFERENCES `t_standard` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='快递员表';

-- ----------------------------
-- Records of t_courier
-- ----------------------------
INSERT INTO `t_courier` VALUES ('1', '111', '快递员1', '15219444445', '123', '123', 'cn', '3');
INSERT INTO `t_courier` VALUES ('15', '001', '快递员2号', '13750000000', '3232', '001', 'jd', '4');

-- ----------------------------
-- Table structure for t_fixed_area
-- ----------------------------
DROP TABLE IF EXISTS `t_fixed_area`;
CREATE TABLE `t_fixed_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fixed_area_name` varchar(255) DEFAULT NULL,
  `fixed_area_leader` int(10) DEFAULT NULL,
  `telephone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fixed_area_courier` (`fixed_area_leader`),
  CONSTRAINT `FK_fixed_area_courier` FOREIGN KEY (`fixed_area_leader`) REFERENCES `t_courier` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='定区表';

-- ----------------------------
-- Records of t_fixed_area
-- ----------------------------
INSERT INTO `t_fixed_area` VALUES ('3', '定区1', '1', '13750000001');
INSERT INTO `t_fixed_area` VALUES ('4', '定区2', '15', '13750000002');
INSERT INTO `t_fixed_area` VALUES ('5', '定区3', '15', '13750000003');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_num` varchar(255) DEFAULT NULL,
  `customer_id` int(10) DEFAULT NULL,
  `send_name` varchar(100) DEFAULT NULL,
  `send_mobile` varchar(100) DEFAULT NULL,
  `send_company` varchar(255) DEFAULT NULL,
  `send_area_id` int(10) DEFAULT NULL,
  `send_address` varchar(255) DEFAULT NULL,
  `rec_name` varchar(100) DEFAULT NULL,
  `rec_mobile` varchar(100) DEFAULT NULL,
  `rec_company` varchar(255) DEFAULT NULL,
  `rec_area_id` int(10) DEFAULT NULL,
  `rec_address` varchar(255) DEFAULT NULL,
  `send_pro_num` varchar(100) DEFAULT NULL,
  `good_type` varchar(100) DEFAULT NULL,
  `pay_type_num` varchar(100) DEFAULT NULL,
  `weight` varchar(100) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `send_mobile_msg` varchar(255) DEFAULT NULL,
  `order_type` varchar(1) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `courier_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_courier` (`courier_id`),
  KEY `FK_rec_order_area` (`rec_area_id`),
  KEY `FK_send_order_area` (`send_area_id`),
  CONSTRAINT `FK_order_courier` FOREIGN KEY (`courier_id`) REFERENCES `t_courier` (`id`),
  CONSTRAINT `FK_rec_order_area` FOREIGN KEY (`rec_area_id`) REFERENCES `t_area` (`id`),
  CONSTRAINT `FK_send_order_area` FOREIGN KEY (`send_area_id`) REFERENCES `t_area` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('4', 'e98e67d2-2fe3-4c80-9897-f9b20ea46436', '5', '13750015150', '13750015150', 'dd', null, '广州市天河区岑村-公交车站', 'lyx', '15197443152', 'aaa', '11', '深圳市龙华区龙华汽车站', '速运当日', null, '寄付日结', '2', 'ssdada', '', '2', '1', '2020-07-26 09:35:29', null);

-- ----------------------------
-- Table structure for t_promotion
-- ----------------------------
DROP TABLE IF EXISTS `t_promotion`;
CREATE TABLE `t_promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `title_img` varchar(255) DEFAULT NULL,
  `active_scope` varchar(255) DEFAULT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` varchar(1) DEFAULT NULL,
  `descrption` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='促销活动表';

-- ----------------------------
-- Records of t_promotion
-- ----------------------------
INSERT INTO `t_promotion` VALUES ('4', '宣传1', 'http://localhost:9080/mybos-web/upload/dc42a03f-4778-4027-9a8d-d975484421d7.jpeg', '111', '2020-07-28 22:00:00', '2020-07-28 00:00:00', '0', '');
INSERT INTO `t_promotion` VALUES ('5', '标题2', 'http://localhost:9080/mybos-web/upload/69feaf68-444b-4f74-a883-2c2bfb6139a7.jpeg', '2', '2020-07-23 22:00:00', '2020-07-21 00:00:00', '0', '方法的身上发生');

-- ----------------------------
-- Table structure for t_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_resource`;
CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `grant_key` varchar(100) DEFAULT NULL,
  `page` varchar(255) DEFAULT NULL,
  `seq` int(10) DEFAULT NULL,
  `resource_type` varchar(10) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `pid` int(10) DEFAULT NULL,
  `open` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Records of t_resource
-- ----------------------------
INSERT INTO `t_resource` VALUES ('9', '业务功能', null, '', '1', '0', 'js/ztree/css/zTreeStyle/img/diy/1_close.png', null, '1');
INSERT INTO `t_resource` VALUES ('10', '受理', null, '', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/1_open.png', '9', '1');
INSERT INTO `t_resource` VALUES ('11', '基础数据', null, '', '1', '0', 'js/ztree/css/zTreeStyle/img/diy/1_close.png', '9', '1');
INSERT INTO `t_resource` VALUES ('12', '收派标准管理', 'standard:*', 'pages/base/standard.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '11', '1');
INSERT INTO `t_resource` VALUES ('13', '快递员管理', 'courier:*', 'pages/base/courier.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '11', '1');
INSERT INTO `t_resource` VALUES ('14', '区域管理', 'area:*', 'pages/base/area.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '11', '0');
INSERT INTO `t_resource` VALUES ('15', '分区管理', 'subArea:*', 'pages/base/sub_area.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '11', '0');
INSERT INTO `t_resource` VALUES ('16', '定区管理', 'fixedArea:*', 'pages/base/fixed_area.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '11', '0');
INSERT INTO `t_resource` VALUES ('17', '运单快速录入', 'waybillquick:*', 'pages/take_delivery/waybill_quick.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '10', '1');
INSERT INTO `t_resource` VALUES ('18', '运单录入', 'waybill:*', 'pages/take_delivery/waybill.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '10', '1');
INSERT INTO `t_resource` VALUES ('19', '运单管理', 'waybill_manage:*', 'pages/take_delivery/waybill_manage.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '10', '1');
INSERT INTO `t_resource` VALUES ('20', '调度', null, '', '1', '0', 'js/ztree/css/zTreeStyle/img/diy/1_open.png', '9', '1');
INSERT INTO `t_resource` VALUES ('21', '宣传任务', 'promotion:*', 'pages/take_delivery/promotion.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '20', '0');
INSERT INTO `t_resource` VALUES ('22', '系统管理', null, '', '1', '0', 'js/ztree/css/zTreeStyle/img/diy/1_open.png', '9', '1');
INSERT INTO `t_resource` VALUES ('23', '用户管理', 'user:*', 'pages/system/user.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '22', '0');
INSERT INTO `t_resource` VALUES ('24', '角色管理', 'role:*', 'pages/system/role.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '22', '0');
INSERT INTO `t_resource` VALUES ('25', '资源管理', 'resource:*', 'pages/system/resource.jsp', '2', '0', 'js/ztree/css/zTreeStyle/img/diy/2.png', '22', '0');
INSERT INTO `t_resource` VALUES ('26', '按钮图标', null, '', '1', '1', '', null, '0');
INSERT INTO `t_resource` VALUES ('27', '添加按钮', 'user_add_btn', '', '2', '1', '', '26', '0');
INSERT INTO `t_resource` VALUES ('28', '修改按钮', 'update_btn', '', '2', '', '', '26', '0');
INSERT INTO `t_resource` VALUES ('29', '删除图标', 'del_btn', '', '2', '', '', '26', '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `keyword` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('3', '系统管理员', '管理员', '系统管理');
INSERT INTO `t_role` VALUES ('4', '后台管理员', '后台管理员', '后台管理员');
INSERT INTO `t_role` VALUES ('5', '小职员', '普通职员', '小职员');

-- ----------------------------
-- Table structure for t_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_role_resource`;
CREATE TABLE `t_role_resource` (
  `role_id` int(10) DEFAULT NULL,
  `resource_id` int(10) DEFAULT NULL,
  KEY `FK_role_resource_resource` (`resource_id`),
  KEY `FK_role_resource_role` (`role_id`),
  CONSTRAINT `FK_role_resource_resource` FOREIGN KEY (`resource_id`) REFERENCES `t_resource` (`id`),
  CONSTRAINT `FK_role_resource_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色资源表';

-- ----------------------------
-- Records of t_role_resource
-- ----------------------------
INSERT INTO `t_role_resource` VALUES ('5', '13');
INSERT INTO `t_role_resource` VALUES ('5', '10');
INSERT INTO `t_role_resource` VALUES ('5', '17');
INSERT INTO `t_role_resource` VALUES ('5', '12');
INSERT INTO `t_role_resource` VALUES ('5', '19');
INSERT INTO `t_role_resource` VALUES ('5', '14');
INSERT INTO `t_role_resource` VALUES ('5', '16');
INSERT INTO `t_role_resource` VALUES ('5', '9');
INSERT INTO `t_role_resource` VALUES ('5', '18');
INSERT INTO `t_role_resource` VALUES ('5', '15');
INSERT INTO `t_role_resource` VALUES ('3', '27');
INSERT INTO `t_role_resource` VALUES ('3', '26');
INSERT INTO `t_role_resource` VALUES ('3', '17');
INSERT INTO `t_role_resource` VALUES ('3', '16');
INSERT INTO `t_role_resource` VALUES ('3', '24');
INSERT INTO `t_role_resource` VALUES ('3', '10');
INSERT INTO `t_role_resource` VALUES ('3', '13');
INSERT INTO `t_role_resource` VALUES ('3', '14');
INSERT INTO `t_role_resource` VALUES ('3', '23');
INSERT INTO `t_role_resource` VALUES ('3', '9');
INSERT INTO `t_role_resource` VALUES ('3', '18');
INSERT INTO `t_role_resource` VALUES ('3', '15');
INSERT INTO `t_role_resource` VALUES ('3', '28');
INSERT INTO `t_role_resource` VALUES ('3', '20');
INSERT INTO `t_role_resource` VALUES ('3', '21');
INSERT INTO `t_role_resource` VALUES ('3', '19');
INSERT INTO `t_role_resource` VALUES ('3', '11');
INSERT INTO `t_role_resource` VALUES ('3', '29');
INSERT INTO `t_role_resource` VALUES ('3', '12');
INSERT INTO `t_role_resource` VALUES ('3', '22');
INSERT INTO `t_role_resource` VALUES ('3', '25');

-- ----------------------------
-- Table structure for t_standard
-- ----------------------------
DROP TABLE IF EXISTS `t_standard`;
CREATE TABLE `t_standard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `min_weight` varchar(10) DEFAULT NULL,
  `max_weight` varchar(10) DEFAULT NULL,
  `min_length` varchar(10) DEFAULT NULL,
  `max_length` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='取派标准表';

-- ----------------------------
-- Records of t_standard
-- ----------------------------
INSERT INTO `t_standard` VALUES ('3', '10-20斤', '20', '30', '250', '400');
INSERT INTO `t_standard` VALUES ('4', '20-30斤', '10', '50', '10', '100');
INSERT INTO `t_standard` VALUES ('5', '30-40斤', '30', '40', '300', '400');
INSERT INTO `t_standard` VALUES ('6', '40-50斤', '40', '50', '400', '500');

-- ----------------------------
-- Table structure for t_sub_area
-- ----------------------------
DROP TABLE IF EXISTS `t_sub_area`;
CREATE TABLE `t_sub_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `start_num` varchar(100) DEFAULT NULL,
  `end_num` varchar(100) DEFAULT NULL,
  `key_words` varchar(255) DEFAULT NULL,
  `assit_key_words` varchar(255) DEFAULT NULL,
  `area_id` int(10) DEFAULT NULL,
  `fixedarea_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sub_area_area` (`area_id`),
  KEY `FK_sub_area_fixed_area` (`fixedarea_id`),
  CONSTRAINT `FK_sub_area_area` FOREIGN KEY (`area_id`) REFERENCES `t_area` (`id`),
  CONSTRAINT `FK_sub_area_fixed_area` FOREIGN KEY (`fixedarea_id`) REFERENCES `t_fixed_area` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='分区表';

-- ----------------------------
-- Records of t_sub_area
-- ----------------------------
INSERT INTO `t_sub_area` VALUES ('3', '01', '00001', '天河', 'th2', '3', '3');
INSERT INTO `t_sub_area` VALUES ('4', '01', '00002', '黄村', 'cc', '5', '4');
INSERT INTO `t_sub_area` VALUES ('5', '3', '004', '黄', 'dd', '6', '5');
INSERT INTO `t_sub_area` VALUES ('6', '01', '00002', '龙华', '宝安区', '11', '5');
INSERT INTO `t_sub_area` VALUES ('7', '0222', '0333', '沙井', '海上田园', '11', '4');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `station` varchar(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'root', 'c07ee22c45409b9ff53c49bd13c7840b', 'root', '男', '13750015150', '1', 'root');
INSERT INTO `t_user` VALUES ('3', 'lisi', 'ad46a6f82c81e3cb3b2d24440e57e28f', '李四', '男', '13750000003', '1', '小职员');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_id` int(10) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  KEY `FK_user_role_role` (`role_id`),
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '3');
INSERT INTO `t_user_role` VALUES ('1', '4');
INSERT INTO `t_user_role` VALUES ('3', '5');

-- ----------------------------
-- Table structure for t_way_bill
-- ----------------------------
DROP TABLE IF EXISTS `t_way_bill`;
CREATE TABLE `t_way_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `way_bill_num` varchar(255) DEFAULT NULL,
  `order_id` int(10) DEFAULT NULL,
  `send_name` varchar(100) DEFAULT NULL,
  `send_mobile` varchar(100) DEFAULT NULL,
  `send_company` varchar(255) DEFAULT NULL,
  `send_area_id` int(10) DEFAULT NULL,
  `send_address` varchar(255) DEFAULT NULL,
  `rec_name` varchar(100) DEFAULT NULL,
  `rec_mobile` varchar(100) DEFAULT NULL,
  `rec_company` varchar(255) DEFAULT NULL,
  `rec_area_id` int(10) DEFAULT NULL,
  `rec_address` varchar(255) DEFAULT NULL,
  `send_pro_num` varchar(100) DEFAULT NULL,
  `good_type` varchar(100) DEFAULT NULL,
  `pay_type_num` varchar(100) DEFAULT NULL,
  `weight` varchar(100) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `arrive_city` varchar(255) DEFAULT NULL,
  `actlweit` varchar(10) DEFAULT NULL,
  `feeitemnum` varchar(10) DEFAULT NULL,
  `vol` varchar(10) DEFAULT NULL,
  `floadreqr` varchar(10) DEFAULT NULL,
  `sign_status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_rec_way_bill_area` (`rec_area_id`),
  KEY `FK_send_way_bill_area` (`send_area_id`),
  KEY `FK_way_bill_order` (`order_id`),
  CONSTRAINT `FK_rec_way_bill_area` FOREIGN KEY (`rec_area_id`) REFERENCES `t_area` (`id`),
  CONSTRAINT `FK_send_way_bill_area` FOREIGN KEY (`send_area_id`) REFERENCES `t_area` (`id`),
  CONSTRAINT `FK_way_bill_order` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='运单表';

-- ----------------------------
-- Records of t_way_bill
-- ----------------------------
INSERT INTO `t_way_bill` VALUES ('3', '200102030', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2', null, '广东广州岑村1哈皮', null, null, null, '快速送', null);
INSERT INTO `t_way_bill` VALUES ('4', '0122122', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '5', null, '广东广州', null, null, null, '顶顶顶', null);
INSERT INTO `t_way_bill` VALUES ('5', 'dd8fb0df-60db-42fd-8b33-cebe0246565d', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '33', null, '33', null, null, null, '3adadas', null);
INSERT INTO `t_way_bill` VALUES ('6', '39e97344-2fef-4f2c-acd5-a6c14da47afd', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '44', null, '44', null, null, null, '444', null);

-- ----------------------------
-- Table structure for t_work_bill
-- ----------------------------
DROP TABLE IF EXISTS `t_work_bill`;
CREATE TABLE `t_work_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pickstate` varchar(10) DEFAULT NULL,
  `buildtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT NULL,
  `courier_id` int(10) DEFAULT NULL,
  `order_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_work_bill_courier` (`courier_id`),
  KEY `FK_work_bill_order` (`order_id`),
  CONSTRAINT `FK_work_bill_courier` FOREIGN KEY (`courier_id`) REFERENCES `t_courier` (`id`),
  CONSTRAINT `FK_work_bill_order` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='工单表';

-- ----------------------------
-- Records of t_work_bill
-- ----------------------------
