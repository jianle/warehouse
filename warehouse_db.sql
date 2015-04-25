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

insert into `user`(username,password,truename,email,role,created) values('admin','admin','管理员','admin@xxx.com',1,current_time());

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
  `boxes`  int(8) not null default 1 comment '一箱多少盒',
  `amount` int(8) not null default 1 comment '一盒多少个',
  `is_disabled` char(1) not null DEFAULT 'F' comment 'T-不可用 F-有效',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 comment '商品信息表'
;
alter table goods add index `g_id_index` (`g_id`);

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
  `s_id` bigint(20) NOT NULL default 0 comment '供应商id',
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
alter table enter add `s_id` bigint(20) NOT NULL default 0 comment '供应商id' after g_id;

CREATE TABLE `storage` (
  `g_id` bigint(20) NOT NULL default 0 comment '商品id',
  `s_id` bigint(20) NOT NULL default 0 comment '供应商id',
  `g_name` varchar(256) not null default '' comment '商品名称',
  `chests` int(8) not null default 0 comment '共多少箱',
  `boxes` int(8) not null default 0 comment '共多少盒数',
  `amount` bigint(20) not null default 0 comment '共多少个',
  `remarks` varchar(1000) not null default '' comment '备注',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`g_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '库存表'
;

alter table storage add `s_id` bigint(20) NOT NULL default 0 comment '供应商id' after g_id;
alter table storage add `chests` int(8) not null default 0 comment '共多少箱' after g_name;
alter table storage add `amount` int(8) not null default 0 comment '共多少个' after boxes;

CREATE TABLE `orderinfo` (
  `o_id` bigint(20) NOT NULL AUTO_INCREMENT comment '订单id',
  `document_code` varchar(20) not null default '' comment '单据编码',
  `order_code` varchar(20) not null default '' comment '订单编码',
  `order_type` varchar(128) not null default '' comment '订单类型',
  `customer_type` varchar(24) not null default '' comment '客户类型',
  `customer_code` varchar(24) not null default '' comment '客户编码',
  `customer_name` varchar(64) not null default '' comment '客户姓名',
  `wh_add` varchar(256) not null default '' comment '出库仓库',
  `transp_cost_type` varchar(24) not null default '' comment '运费支付类型',
  `receive_tel` varchar(24) not null default '' comment '收货电话',
  `receive_add` varchar(256) not null default '' comment '收货地址(货送地址)',
  `user_id` bigint(20) not null default 0 comment '制单人ID',
  `user_name` varchar(64) not null default '' comment '制单人',
  `document_date`date not null default '1990-01-01'comment '制单日期',
  `sale_date` date not null default '1990-01-01' comment '销售日期',
  `company` varchar(256) not null default '' comment '公司名称',
  `amount_total` double(10,2) not null default 0.0 comment '总金额',
  `mt_mch` varchar(256) not null default '' comment '销售机构',
  `account_balance` double(10,2) not null default 0.0 comment '账户余额',
  `retail_price` double(10,2) not null default 0.0 comment '零售价总额',
  `amount_discount` double(10,2) not null default 0.0 comment '折扣金额',
  `amount_payable` double(10,2) not null default 0.0 comment '应付金额',
  `amount_net` double(10,2) not null default 0.0 comment '净额',
  `status` smallint(3) not null default 0 comment '订单状态 0-新增、1-已配货、2-已验货、3-已出库、4-已完成、',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '订单表'
;


CREATE TABLE `order_detail` (
  `od_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
  `o_id` bigint(20) NOT NULL default 0 comment '订单id',
  `g_id` bigint(20) NOT NULL default 0 comment '商品id',
  `g_name` varchar(256) not null default '' comment '商品名称',
  `retail_price` double(10,2) not null default 0.0 comment '零售价',
  `unit_price` double(10,2) not null default 0.0 comment '单价',
  `amount_net` double(10,2) not null default 0.0 comment '净额',
  `amount`  int(8) not null default 0 comment '数量',
  `amount_amt` double(10,2) not null default 0.0 comment '金额',
  `code` varchar(64) not null default '' comment '条码',
  `remarks` varchar(1000) not null default '' comment '备注',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`od_id`),
  index `o_id_index` (`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '订单表明细'
;

CREATE TABLE `delivery` (
  `d_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
  `o_id` bigint(20) NOT NULL default 0 comment '订单id',
  `express_id` varchar(64) not null default '' comment '快递单号',
  `express_name` varchar(256) not null default '' comment '快递名称(公司)',
  `weight` int(10) not null default 0 comment '重(g)',
  `length` int(10) not null default 0 comment '长(mm)',
  `wide`   int(10) not null default 0 comment '宽(mm)',
  `high`   int(10) not null default 0 comment '高(mm)',
  `remarks` varchar(1000) not null default '' comment '备注',
  `status` smallint(3) not null default 0 comment '订单状态 0-等待出库、1-已经出库、',
  `insert_dt` datetime not null DEFAULT '1900-01-01 00:00:00' comment '插入日期',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`d_id`),
  index `o_id_index` (`o_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '订单出库'
;

alter table delivery add `status` smallint(3) not null default 0 comment '订单状态 0-等待出库、1-已经出库、' after `remarks`;

CREATE TABLE `delivery_detail` (
  `dd_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
  `content` varchar(64) not null default '' comment '快递单号',
  `accept_address` varchar(640) not null default '' comment '接收地址',
  `accept_time` datetime not null DEFAULT '1900-01-01 00:00:00' comment '接收日期',
  `remark` varchar(1000) not null default '' comment '备注',
  PRIMARY KEY (`dd_id`),
  index `content_index` (`content`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '快递详情'
;