/*
Navicat MySQL Data Transfer

Source Server         : jarvis
Source Server Version : 50625
Source Host           : 47.94.92.140:3306
Source Database       : jws_user

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2018-04-04 16:14:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_access
-- ----------------------------
DROP TABLE IF EXISTS `data_access`;
CREATE TABLE `data_access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `access_expr` varchar(50) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限表达式',
  `function_id` bigint(20) NOT NULL COMMENT '功能id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='权限表达式';

-- ----------------------------
-- Table structure for function_access
-- ----------------------------
DROP TABLE IF EXISTS `function_access`;
CREATE TABLE `function_access` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `module_id` bigint(20) NOT NULL COMMENT '模块id',
  `function_id` bigint(20) NOT NULL COMMENT '功能点id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 COMMENT='功能点权限';

-- ----------------------------
-- Table structure for function_point
-- ----------------------------
DROP TABLE IF EXISTS `function_point`;
CREATE TABLE `function_point` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `enabled` char(1) NOT NULL COMMENT '是否启用',
  `function_code` varchar(40) NOT NULL DEFAULT '' COMMENT '功能点code',
  `function_name` varchar(40) NOT NULL COMMENT '功能名称',
  `module_id` bigint(20) NOT NULL COMMENT '模块id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='功能点';

-- ----------------------------
-- Table structure for org_user
-- ----------------------------
DROP TABLE IF EXISTS `org_user`;
CREATE TABLE `org_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `enabled` char(1) NOT NULL COMMENT '是否可用',
  `org_ref_id` bigint(20) NOT NULL COMMENT '关联机构id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `default_org` char(1) NOT NULL COMMENT '是否默认机构',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueUserOrg` (`org_ref_id`,`user_id`) USING BTREE COMMENT '用户机构联合唯一索引',
  KEY `USERID` (`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE=InnoDB AUTO_INCREMENT=4979 DEFAULT CHARSET=utf8 COMMENT='用户机构关联表';

-- ----------------------------
-- Table structure for organization_reference
-- ----------------------------
DROP TABLE IF EXISTS `organization_reference`;
CREATE TABLE `organization_reference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `group_id` bigint(20) NOT NULL COMMENT '集团id',
  `group_name` varchar(60) NOT NULL COMMENT '集团名称',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  `org_name` varchar(60) NOT NULL COMMENT '机构名称',
  `org_type` varchar(20) NOT NULL COMMENT '机构类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3257 DEFAULT CHARSET=utf8 COMMENT='机构组织表';

-- ----------------------------
-- Table structure for system_module
-- ----------------------------
DROP TABLE IF EXISTS `system_module`;
CREATE TABLE `system_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` int(11) NOT NULL COMMENT '版本号',
  `callback_url` varchar(255) NOT NULL COMMENT '回掉地址',
  `module_code` varchar(40) NOT NULL COMMENT '模块号',
  `module_name` varchar(40) NOT NULL COMMENT '模块名',
  `enabled` char(1) NOT NULL COMMENT '是否可用',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统模块表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(40) NOT NULL COMMENT '真实姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `enabled` char(1) NOT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_username` (`username`) USING BTREE COMMENT 'username唯一'
) ENGINE=InnoDB AUTO_INCREMENT=4979 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `enabled` char(1) NOT NULL COMMENT '是否可用',
  `global_role` char(1) NOT NULL COMMENT '是否全局角色',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `name` varchar(20) NOT NULL COMMENT '角色名',
  `org_ref_id` bigint(20) DEFAULT NULL COMMENT '关联机构id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Table structure for user_role_assignment
-- ----------------------------
DROP TABLE IF EXISTS `user_role_assignment`;
CREATE TABLE `user_role_assignment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  `enabled` char(1) NOT NULL COMMENT '是否可用',
  `end_date` datetime DEFAULT NULL COMMENT '有效期结束时间',
  `from_date` datetime DEFAULT NULL COMMENT '有效期开始时间',
  `org_ref_id` bigint(20) NOT NULL COMMENT '组织机构关联表Id',
  `org_user_id` bigint(20) NOT NULL COMMENT '用户机构关联表id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `unbound` char(1) NOT NULL COMMENT '是否长期有效',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5895 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Table structure for user_task_time
-- ----------------------------
DROP TABLE IF EXISTS `user_task_time`;
CREATE TABLE `user_task_time` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `task_type` varchar(20) NOT NULL COMMENT '定时任务类型',
  `last_update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='定时任务时间表';