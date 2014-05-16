/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.5.29-log : Database - w951_db_zsbus_permission
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`w951_db_zsbus_permission` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `w951_db_zsbus_permission`;

/*Table structure for table `zsbus_permission_domain` */

DROP TABLE IF EXISTS `zsbus_permission_domain`;

CREATE TABLE `zsbus_permission_domain` (
  `domain_id` varchar(32) NOT NULL COMMENT '域ID',
  `domain_name` varchar(20) DEFAULT NULL COMMENT '域名称',
  `domain_url` varchar(200) DEFAULT NULL COMMENT '域地址',
  `domain_createname` varchar(20) DEFAULT NULL COMMENT '创建人',
  `domain_createdate` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置域';

/*Data for the table `zsbus_permission_domain` */

insert  into `zsbus_permission_domain`(`domain_id`,`domain_name`,`domain_url`,`domain_createname`,`domain_createdate`) values ('402881d045f4b71d0145f4bc58b50001','权限管理','http://localhost:8080/w951-web-zsbus-permission/','admin','2014-05-13 16:39:15'),('402881d045f4b71d0145f4be6d3b0002','新闻管理','http://localhost:8080/w951-web-zsbus-news/','admin','2014-05-13 16:41:31');

/*Table structure for table `zsbus_permission_group` */

DROP TABLE IF EXISTS `zsbus_permission_group`;

CREATE TABLE `zsbus_permission_group` (
  `group_id` varchar(32) NOT NULL COMMENT '组编号',
  `group_name` varchar(20) DEFAULT NULL COMMENT '组名称',
  `group_createname` varchar(20) DEFAULT NULL COMMENT '组创建者',
  `group_createdate` datetime DEFAULT NULL COMMENT '组创建时间',
  `group_verify` int(11) DEFAULT NULL COMMENT '组审核',
  `group_back` varchar(300) DEFAULT NULL COMMENT '组备注',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分组表';

/*Data for the table `zsbus_permission_group` */

insert  into `zsbus_permission_group`(`group_id`,`group_name`,`group_createname`,`group_createdate`,`group_verify`,`group_back`) values ('c4e547cc0b18414aaa585b123ced7ccb','超级管理员','admin','2014-05-09 07:55:32',0,NULL);

/*Table structure for table `zsbus_permission_group_menu` */

DROP TABLE IF EXISTS `zsbus_permission_group_menu`;

CREATE TABLE `zsbus_permission_group_menu` (
  `group_menu_id` varchar(32) NOT NULL COMMENT '关联编号',
  `group_id` varchar(32) DEFAULT NULL COMMENT '组编号',
  `menu_id` varchar(32) DEFAULT NULL COMMENT '资源编号',
  `group_menu_name` varchar(20) DEFAULT NULL,
  `group_menu_date` datetime DEFAULT NULL,
  `group_menu_back` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`group_menu_id`),
  KEY `FK_GroupMenu_Group_On_GroupId` (`group_id`),
  KEY `FK_GroupMenu_Menu_On_MenuId` (`menu_id`),
  CONSTRAINT `FK_GroupMenu_Group_On_GroupId` FOREIGN KEY (`group_id`) REFERENCES `zsbus_permission_group` (`group_id`),
  CONSTRAINT `FK_GroupMenu_Menu_On_MenuId` FOREIGN KEY (`menu_id`) REFERENCES `zsbus_permission_menu` (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分组 分栏 表';

/*Data for the table `zsbus_permission_group_menu` */

insert  into `zsbus_permission_group_menu`(`group_menu_id`,`group_id`,`menu_id`,`group_menu_name`,`group_menu_date`,`group_menu_back`) values ('402881d045f52fc90145f5366d270003','c4e547cc0b18414aaa585b123ced7ccb','ed948a44b4c54854b0640eea2a1f3e16','admin','2014-05-13 18:52:35',NULL),('402881d045f52fc90145f5366d270004','c4e547cc0b18414aaa585b123ced7ccb','402881d045f52fc90145f5337e960000','admin','2014-05-13 18:52:35',NULL);

/*Table structure for table `zsbus_permission_menu` */

DROP TABLE IF EXISTS `zsbus_permission_menu`;

CREATE TABLE `zsbus_permission_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '分栏编号',
  `menu_name` varchar(20) DEFAULT NULL COMMENT '分栏名称',
  `menu_date` datetime DEFAULT NULL COMMENT '分栏日期',
  `menu_createname` varchar(20) DEFAULT NULL COMMENT '分栏者',
  `menu_back` varchar(300) DEFAULT NULL COMMENT '分栏备注',
  `menu_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单栏';

/*Data for the table `zsbus_permission_menu` */

insert  into `zsbus_permission_menu`(`menu_id`,`menu_name`,`menu_date`,`menu_createname`,`menu_back`,`menu_sort`) values ('402881d045f52fc90145f5337e960000','新闻管理','2014-05-13 18:49:23','admin','',2),('ed948a44b4c54854b0640eea2a1f3e16','权限管理','2014-05-09 07:58:39','admin','',1);

/*Table structure for table `zsbus_permission_menu_resource` */

DROP TABLE IF EXISTS `zsbus_permission_menu_resource`;

CREATE TABLE `zsbus_permission_menu_resource` (
  `menu_resouce_id` varchar(32) NOT NULL,
  `menu_id` varchar(32) DEFAULT NULL COMMENT '分栏编号',
  `resource_id` varchar(32) DEFAULT NULL COMMENT '资源编号',
  `menu_resouce_createname` varchar(20) DEFAULT NULL COMMENT '创建者',
  `menu_resouce_date` datetime DEFAULT NULL COMMENT '创建时间',
  `menu_resouce_back` varchar(300) DEFAULT NULL COMMENT '备注',
  `menu_resouce_save` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许保存',
  `menu_resouce_update` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许更新',
  `menu_resouce_delete` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许删除',
  `menu_resouce_select` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许查询',
  `menu_resouce_import` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许导入',
  `menu_resouce_export` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许导出',
  `menu_resouce_like` varchar(100) DEFAULT NULL COMMENT '关联资源是否允许跳转',
  PRIMARY KEY (`menu_resouce_id`),
  KEY `FK_MenuResource_Menu_On_MenuId` (`menu_id`),
  KEY `FK_MenuResource_Resource_On_ResourceId` (`resource_id`),
  CONSTRAINT `FK_MenuResource_Menu_On_MenuId` FOREIGN KEY (`menu_id`) REFERENCES `zsbus_permission_menu` (`menu_id`),
  CONSTRAINT `FK_MenuResource_Resource_On_ResourceId` FOREIGN KEY (`resource_id`) REFERENCES `zsbus_permission_resource` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分栏资源';

/*Data for the table `zsbus_permission_menu_resource` */

insert  into `zsbus_permission_menu_resource`(`menu_resouce_id`,`menu_id`,`resource_id`,`menu_resouce_createname`,`menu_resouce_date`,`menu_resouce_back`,`menu_resouce_save`,`menu_resouce_update`,`menu_resouce_delete`,`menu_resouce_select`,`menu_resouce_import`,`menu_resouce_export`,`menu_resouce_like`) values ('402881d045f8d2af0145f8d518d0000a','ed948a44b4c54854b0640eea2a1f3e16','6d2f60bff7014843b2d8a92116b5914d','admin','2014-05-14 11:44:46',NULL,'permission/User/action/insert','permission/User/action/update','permission/User/action/delete','permission/User/action/query','permission/User/action/import','permission/User/action/export','permission/User/action/like'),('402881d045f8d2af0145f8d518d0000b','ed948a44b4c54854b0640eea2a1f3e16','a341206d21e941dc8fb7a4fb9cc60d1a','admin','2014-05-14 11:44:46',NULL,'permission/Group/action/insert','permission/Group/action/update','permission/Group/action/delete','permission/Group/action/query','permission/Group/action/import','permission/Group/action/export','permission/Group/action/like'),('402881d045f8d2af0145f8d518d0000c','ed948a44b4c54854b0640eea2a1f3e16','e1bec41eb7f249419ce72cef4dcd8cdf','admin','2014-05-14 11:44:46',NULL,'permission/Menu/action/insert','permission/Menu/action/update','permission/Menu/action/delete','permission/Menu/action/query','permission/Menu/action/import','permission/Menu/action/export','permission/Menu/action/like'),('402881d045f8d2af0145f8d518d0000d','ed948a44b4c54854b0640eea2a1f3e16','9a99efb5f098431b85a51925c5e4e94b','admin','2014-05-14 11:44:46',NULL,'permission/Resource/action/insert','permission/Resource/action/update','permission/Resource/action/delete','permission/Resource/action/query','permission/Resource/action/import','permission/Resource/action/export','permission/Resource/action/like'),('402881d045f8d2af0145f8d518d0000e','ed948a44b4c54854b0640eea2a1f3e16','402881d045f4ae740145f4b09fe10000','admin','2014-05-14 11:44:46',NULL,'permission/Domain/action/insert','permission/Domain/action/update','permission/Domain/action/delete','permission/Domain/action/query','permission/Domain/action/import','permission/Domain/action/export','permission/Domain/action/like'),('402881d045f8d2af0145f8d518d1000f','ed948a44b4c54854b0640eea2a1f3e16','402881d045dee47e0145deecb3ee0000','admin','2014-05-14 11:44:46',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('402881d045faa0630145faa897710005','402881d045f52fc90145f5337e960000','402881d045f52fc90145f5350e510001','admin','2014-05-14 20:15:24',NULL,'news/Category/action/insert','news/Category/action/update','news/Category/action/delete','news/Category/action/query','news/Category/action/import','news/Category/action/export','news/Category/action/like'),('402881d045faa0630145faa897710006','402881d045f52fc90145f5337e960000','402881d045f52fc90145f538f0bb0005','admin','2014-05-14 20:15:24',NULL,'news/Article/action/insert','news/Article/action/update','news/Article/action/delete','news/Article/action/query','news/Article/action/import','news/Article/action/export','news/Article/action/like');

/*Table structure for table `zsbus_permission_resource` */

DROP TABLE IF EXISTS `zsbus_permission_resource`;

CREATE TABLE `zsbus_permission_resource` (
  `resource_id` varchar(32) NOT NULL,
  `domain_id` varchar(32) DEFAULT NULL,
  `resource_identif` varchar(20) DEFAULT NULL COMMENT '资源标识',
  `resource_name` varchar(20) DEFAULT NULL COMMENT '资源名称',
  `resource_url` varchar(100) DEFAULT NULL COMMENT '资源地址',
  `resource_createname` varchar(20) DEFAULT NULL COMMENT '资源创建者',
  `resource_date` datetime DEFAULT NULL COMMENT '资源创建时间',
  `resource_verify` int(11) DEFAULT NULL COMMENT '资源审核',
  `resource_back` varchar(300) DEFAULT NULL COMMENT '资源备注',
  `resource_save_url` varchar(100) DEFAULT NULL,
  `resource_update_url` varchar(100) DEFAULT NULL,
  `resource_delete_url` varchar(100) DEFAULT NULL,
  `resource_select_url` varchar(100) DEFAULT NULL,
  `resource_import_url` varchar(100) DEFAULT NULL,
  `resource_export_url` varchar(100) DEFAULT NULL,
  `resource_like_url` varchar(100) DEFAULT NULL,
  `resource_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`resource_id`),
  KEY `FK_Resource_Domain_On_DomainId` (`domain_id`),
  CONSTRAINT `FK_Resource_Domain_On_DomainId` FOREIGN KEY (`domain_id`) REFERENCES `zsbus_permission_domain` (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

/*Data for the table `zsbus_permission_resource` */

insert  into `zsbus_permission_resource`(`resource_id`,`domain_id`,`resource_identif`,`resource_name`,`resource_url`,`resource_createname`,`resource_date`,`resource_verify`,`resource_back`,`resource_save_url`,`resource_update_url`,`resource_delete_url`,`resource_select_url`,`resource_import_url`,`resource_export_url`,`resource_like_url`,`resource_sort`) values ('402881d045dee47e0145deecb3ee0000','402881d045f4b71d0145f4bc58b50001','PermissionLoginOut','退出系统','permission/Main/loginOut','admin','2014-05-09 11:00:25',0,'','','','','','','','',6),('402881d045f4ae740145f4b09fe10000','402881d045f4b71d0145f4bc58b50001','PermissionDomain','域管理','permission/Main/domain','admin','2014-05-13 16:26:27',0,'','permission/Domain/action/insert','permission/Domain/action/update','permission/Domain/action/delete','permission/Domain/action/query','permission/Domain/action/import','permission/Domain/action/export','permission/Domain/action/like',5),('402881d045f52fc90145f5350e510001','402881d045f4b71d0145f4be6d3b0002','NewsCategory','新闻分类','news/Main/category','admin','2014-05-13 18:51:06',0,'','news/Category/action/insert','news/Category/action/update','news/Category/action/delete','news/Category/action/query','news/Category/action/import','news/Category/action/export','news/Category/action/like',7),('402881d045f52fc90145f538f0bb0005','402881d045f4b71d0145f4be6d3b0002','NewsArticle','新闻文章','news/Main/article','admin','2014-05-13 18:55:20',0,'','news/Article/action/insert','news/Article/action/update','news/Article/action/delete','news/Article/action/query','news/Article/action/import','news/Article/action/export','news/Article/action/like',8),('6d2f60bff7014843b2d8a92116b5914d','402881d045f4b71d0145f4bc58b50001','PermissionUser','用户管理','permission/Main/user','admin','2014-05-09 07:59:53',0,'','permission/User/action/insert','permission/User/action/update','permission/User/action/delete','permission/User/action/query','permission/User/action/import','permission/User/action/export','permission/User/action/like',1),('9a99efb5f098431b85a51925c5e4e94b','402881d045f4b71d0145f4bc58b50001','PermissionResource','资源管理','permission/Main/resource','admin','2014-05-09 10:45:13',0,NULL,'permission/Resource/action/insert','permission/Resource/action/update','permission/Resource/action/delete','permission/Resource/action/query','permission/Resource/action/import','permission/Resource/action/export','permission/Resource/action/like',4),('a341206d21e941dc8fb7a4fb9cc60d1a','402881d045f4b71d0145f4bc58b50001','PermissionGroup','分组管理','permission/Main/group','admin','2014-05-09 09:10:24',0,NULL,'permission/Group/action/insert','permission/Group/action/update','permission/Group/action/delete','permission/Group/action/query','permission/Group/action/import','permission/Group/action/export','permission/Group/action/like',2),('e1bec41eb7f249419ce72cef4dcd8cdf','402881d045f4b71d0145f4bc58b50001','PermissionMenu','分栏管理','permission/Main/menu','admin','2014-05-09 09:50:08',0,NULL,'permission/Menu/action/insert','permission/Menu/action/update','permission/Menu/action/delete','permission/Menu/action/query','permission/Menu/action/import','permission/Menu/action/export','permission/Menu/action/like',3);

/*Table structure for table `zsbus_permission_user` */

DROP TABLE IF EXISTS `zsbus_permission_user`;

CREATE TABLE `zsbus_permission_user` (
  `user_id` varchar(32) NOT NULL COMMENT '账户ID',
  `user_nm` varchar(20) DEFAULT NULL COMMENT '账户名称',
  `user_name` varchar(8) DEFAULT NULL COMMENT '真实名称',
  `user_pass` varchar(32) DEFAULT NULL COMMENT '用户密码',
  `user_sex` int(11) DEFAULT NULL COMMENT '用户性别',
  `user_age` int(11) DEFAULT NULL COMMENT '用户年龄',
  `user_dept` varchar(20) DEFAULT NULL COMMENT '用户部门',
  `user_zw` varchar(20) DEFAULT NULL COMMENT '用户职位',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '用户手机',
  `user_qq` varchar(20) DEFAULT NULL COMMENT '用户QQ',
  `user_email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `user_date` datetime DEFAULT NULL COMMENT '用户创建时间',
  `user_company` varchar(50) DEFAULT NULL COMMENT '用户所在公司',
  `user_createname` varchar(20) DEFAULT NULL COMMENT '创建用户者',
  `user_verify` int(11) DEFAULT NULL COMMENT '审核用户',
  `user_back` varchar(300) DEFAULT NULL COMMENT '用户备注',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `zsbus_permission_user` */

insert  into `zsbus_permission_user`(`user_id`,`user_nm`,`user_name`,`user_pass`,`user_sex`,`user_age`,`user_dept`,`user_zw`,`user_phone`,`user_qq`,`user_email`,`user_date`,`user_company`,`user_createname`,`user_verify`,`user_back`) values ('10a59a452e4c4cc4bb6770047861b523','admin','admin','7a57a5a743894a0e',0,28,NULL,NULL,NULL,NULL,NULL,'2014-05-09 06:58:12',NULL,'SYSTEM',0,NULL);

/*Table structure for table `zsbus_permission_user_group` */

DROP TABLE IF EXISTS `zsbus_permission_user_group`;

CREATE TABLE `zsbus_permission_user_group` (
  `user_group_id` varchar(32) NOT NULL COMMENT '关联编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '关联用户编号',
  `group_id` varchar(32) DEFAULT NULL COMMENT '关联组编号',
  `user_group_name` varchar(20) DEFAULT NULL COMMENT '关联用户者',
  `user_group_date` datetime DEFAULT NULL COMMENT '关联日期',
  `user_group_back` varchar(300) DEFAULT NULL COMMENT '关联备注',
  PRIMARY KEY (`user_group_id`),
  KEY `FK_UserGroup_Group_On_GroupId` (`group_id`),
  KEY `FK_UserGroup_User_On_UserId` (`user_id`),
  CONSTRAINT `FK_UserGroup_Group_On_GroupId` FOREIGN KEY (`group_id`) REFERENCES `zsbus_permission_group` (`group_id`),
  CONSTRAINT `FK_UserGroup_User_On_UserId` FOREIGN KEY (`user_id`) REFERENCES `zsbus_permission_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户 分组 关联表';

/*Data for the table `zsbus_permission_user_group` */

insert  into `zsbus_permission_user_group`(`user_group_id`,`user_id`,`group_id`,`user_group_name`,`user_group_date`,`user_group_back`) values ('402881d045deb6b30145ded0145b0011','10a59a452e4c4cc4bb6770047861b523','c4e547cc0b18414aaa585b123ced7ccb','admin','2014-05-09 10:29:06',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
