CREATE DATABASE `warehouse_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL default 0 comment '对应上级ID',
  `username` varchar(256) NOT NULL default '' comment '账户',
  `password` varchar(20) NOT NULL default 'pwd' comment '密码',
  `truename` varchar(256) NOT NULL default '' comment '用户中文名',
  `email` varchar(256) NOT NULL default '' comment '邮箱',
  `role` int(11) NOT NULL default 0 comment '角色',
  `created` datetime NOT NULL comment '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8
;

ALTER TABLE `warehouse_db`.`user` CHANGE COLUMN `password` `password` varchar(100) NOT NULL;
ALTER TABLE `warehouse_db`.`user` ADD COLUMN `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级ID' AFTER `id`;

insert into `user`(username,password,truename,email,role,created) values('admin','admin123','管理员','admin@xxx.com',1,current_time());

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
  PRIMARY KEY (`g_id`),
  index `g_id_index` (`g_id`)
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


/*!财务系统表详情 */;
CREATE DATABASE `finance_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

drop table if exists `producer`;
CREATE TABLE `producer` (
`pro_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
`pro_name` VARCHAR(644) NOT NULL default '' comment '公司名称',
`remark` VARCHAR(640) NOT NULL default '' comment '备注',
`update_time` timestamp null on update current_timestamp comment '最近一次更新',
PRIMARY KEY (`pro_id`) 
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 comment '生产商，供应商'
;

drop table if exists `consumer`;
CREATE TABLE `consumer` (
`con_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
`con_name` VARCHAR(644) NOT NULL default '' comment '公司名称',
`account_company` varchar(640) not null default '' comment '开户名称',
`account` varchar(100) not null default '' comment '账号',
`taxpayer_id` varchar(100) not null default '' comment '纳税人识别号',
`contact_addr` VARCHAR(640) NOT NULL default '' comment '联系地址',
`contact_name` VARCHAR(30) NOT NULL default '' comment '联系人',
`contact_tel` VARCHAR(20) NOT NULL default '' comment '联系电话',
`remark` VARCHAR(640) NOT NULL default '' comment '备注',
`insert_dt` timestamp not null comment '建立日期',
`update_time` timestamp null on update current_timestamp comment '最近一次更新',
PRIMARY KEY (`con_id`) 
)ENGINE=InnoDB AUTO_INCREMENT=20000 DEFAULT CHARSET=utf8 comment '客服公司'
;

drop table if exists `bill_receivable`;
CREATE TABLE `bill_receivable` (
`br_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
`con_id` bigint(20) NOT NULL default 0 comment '客服公司ID',
`br_date` DATE NOT NULL default '1900-01-01' comment '日期',
`amount` DOUBLE(20,4) NOT NULL default 0.0 comment '金额',
`remark` VARCHAR(640) NOT NULL default '' comment '备注',
PRIMARY KEY (`br_id`),
index `idx` (con_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '应收账单'
;

drop table if exists `invoice`;
CREATE TABLE `invoice` (
`inv_id` bigint(20) NOT NULL default 0 comment '发票号',
`con_id` bigint(20) NOT NULL default 0 comment '发票抬头',
`valorem_tax` DOUBLE(20,4) NOT NULL  default 0.0 comment '价税合计',
`amount` DOUBLE(20,4) NOT NULL default 0.0 comment '金额',
`amount_tax` DOUBLE(20,4) NOT NULL default 0.0 comment '税额',
`rate_tax` DOUBLE(4,2) NOT NULL default 0.0 comment '税率',
`inv_date` DATE NOT NULL default '1900-01-01' comment '开票日期',
`remark` VARCHAR(640) NOT NULL default '' comment '备注',
`inc_date` DATE NOT NULL default '1900-01-01' comment '进账日期',
`pro_id` bigint(20) NOT NULL default 0 comment '开票公司',
`verification` DOUBLE(20,4) NOT NULL default 0.0 comment '已核销金额',
`is_deleted` smallint(2) not null default 0 comment '0-有效、1-被删除的',
`update_time` timestamp null on update current_timestamp comment '最近一次更新',
PRIMARY KEY (`inv_id`),
index `con_id_index` (`con_id`),
index `pro_id_index` (`pro_id`),
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '发票表'
;

drop table if exists `ledger_receivable`;
create table `ledger_receivable`(
  `lr_id` bigint(20) not null AUTO_INCREMENT comment '主键自增ID',
  `con_id` bigint(20) not null default 0 comment '付款公司（客服公司）',
  `pro_id` bigint(20) not null default 0 comment '付款公司（客服公司）',
  `month_id` varchar(10) not null default '' comment '所属月份',
  `amount` double(20,4) not null default 0.0 comment '金额',
  `pay_date` date not null default '1900-01-01' comment '付款日期',
  `verification` DOUBLE(20,4) NOT NULL default 0.0 comment '已核销金额',
  `remark` VARCHAR(640) NOT NULL default '' comment '备注',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`lr_id`),
  index `con_id_index` (`con_id`,`pro_id`),
  index `month_id_index` (month_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '收款表'
;

drop table if exists `invoice_income`;
CREATE TABLE `invoice_income` (
`inv_id` bigint(20) NOT NULL default 0 comment '发票号',
`pro_id` bigint(20) NOT NULL default 0 comment '发票抬头ID',
`valorem_tax` DOUBLE(20,4) NOT NULL  default 0.0 comment '价税合计',
`amount` DOUBLE(20,4) NOT NULL default 0.0 comment '金额',
`amount_tax` DOUBLE(20,4) NOT NULL default 0.0 comment '税额',
`rate_tax` DOUBLE(4,2) NOT NULL default 0.0 comment '税率',
`inv_date` DATE NOT NULL default '1900-01-01' comment '开票日期',
`inv_type` smallint(2) not null default 0 comment '0-公司、3-张峰、4-吕峰、5-陈浩、2-其它',
`con_id` bigint(20) NOT NULL default 0 comment '开票对应公司ID',
`remark` VARCHAR(640) NOT NULL default '' comment '备注',
`rate_rebate` DOUBLE(5,2) NOT NULL default 0.0 comment '返点比例',
`is_deleted` smallint(2) not null default 0 comment '0-有效、1-被删除的',
`update_time` timestamp null on update current_timestamp comment '最近一次更新',
PRIMARY KEY (`inv_id`),
index `inv_date_index`(`inv_date`),
index `con_id_index` (`con_id`),
index `pro_id_index` (`pro_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment '进项增票登记表'
;


drop table if exists `debtor`;
create table `debtor`(
  `de_id` bigint(20) not null AUTO_INCREMENT comment '主键自增ID',
  `mon_id` varchar(64) not null default '1900M01' comment '月份',
  `consumer` varchar(255) not null default '' comment '客户名称',
  `inv_company` VARCHAR(640) NOT NULL default '' comment '开票公司',
  `amount` double(20,4) not null default 0.0 comment '金额',
  `remark` VARCHAR(640) NOT NULL default '' comment '备注',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`de_id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '应收账款'
;


drop table if exists `incomepayment`;
create table `incomepayment`(
  `id` int(10) not null AUTO_INCREMENT comment '主键自增ID',
  `parent_id`  int(10) not null default 0 comment '上级目录ID',
  `type` varchar(255) not null default '' comment '支出类型',
  `update_time` timestamp null on update current_timestamp comment '最近一次更新',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '收入支出维表'
;

