CREATE DATABASE `warehouse_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(256) NOT NULL,
  `password` varchar(20) NOT NULL,
  `truename` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `role` int(11) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
;

CREATE TABLE `goods` (
  `g_id` bigint(20) NOT NULL AUTO_INCREMENT comment '商品自增id',
  `s_id` bigint(20) not null default 0 comment '供应商id',
  `name` varchar(256) not null default '' comment '商品名称',
  `length` int(11) not null default 0 comment '长度(单位:mm)',
  `width`  int(11) not null default 0 comment '宽度(单位:mm)',
  `height` int(11) not null default 0 comment '高度(单位:mm)',
  `weight` int(11) not null default 0 comment '重量(单位:g)',
  `g_id_supplier` varchar(64) default '' comment '供应商编号',
  `scode` varchar(128) not null default '' comment '商品条码',
  `boxes`  int(8) not null default 0 comment '一箱多少盒',
  `amount` int(8) not null default 0 comment '一盒多少个',
  `is_disabled` char(1) not null DEFAULT 'F' comment 'T-不可用 F-有效',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 comment '商品信息表'
;

CREATE TABLE supplier(
  `s_id` bigint(20) not null AUTO_INCREMENT comment '供应商自增id',
  `name` varchar(256) not null default '' comment '供应商名称',
  `shortname` varchar(30) not null default '' comment '供应商名称缩写',  
  `address` varchar(256) not null default '' comment '供应商地址',
  `contact_name` varchar(64) not null default '' comment '联系人',
  `contact_tel`  varchar(64) not null default '' comment '联系电话',
  `is_disabled` char(1) not null DEFAULT 'F' comment 'T-不可用 F-有效',
  `mbcode` varchar(128) not null default '' comment '月结编号',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`s_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '供应商信息表';


CREATE TABLE `enter` (
  `e_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自增id',
  `g_id` bigint(20) NOT NULL default 0 comment '商品id',
  `g_name` varchar(256) not null default '' comment '商品名称',
  `chests` int(8) not null default 0 comment '共多少箱',
  `boxes` int(8) not null default 0 comment '共多少盒数',
  `amount` int(8) not null default 0 comment '共多少个',
  `user_id` bigint(20) NOT NULL default 0 comment '操作用户ID',
  `user_name` varchar(256)  NOT NULL default '' comment '操作人姓名',
  `remarks` varchar(1000) not null default '' comment '备注',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`e_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '商品入库表'
;

CREATE TABLE `storage` (
  `g_id` bigint(20) NOT NULL default 0 comment '商品id',
  `g_name` varchar(256) not null default '' comment '商品名称',
  `boxes` int(8) not null default 0 comment '共多少盒数',
  `remarks` varchar(1000) not null default '' comment '备注',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '库存表'
;


CREATE TABLE `purchase` (
  `p_id` bigint(20) NOT NULL AUTO_INCREMENT comment '采购自增id',
  `g_id` bigint(20) NOT NULL default 0 comment '商品id',
  `g_name` varchar(256) not null default '' comment '商品名称',
  `retail_prices` double(8,2) not null default 0.0 comment '零售价格',
  `unit_price` double(8,2) not null default 0.0 comment '单价',
  `net_amount` double(8,2) not null default 0.0 comment '净额',
  `amount` int(8) not null default 0 comment '商品的数量',
  `atm_amount` double(8,2) not null default 0.0 comment '金额',
  ``  '条码',
  `remarks` varchar(1000) not null default '' comment '备注',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '采购单'
;



