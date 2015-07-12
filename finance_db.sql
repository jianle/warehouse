/*!财务系统表详情 */;
CREATE DATABASE `finance_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

use `finance_db`;

drop table if exists `producer`;
CREATE TABLE `producer` (
`pro_id` bigint(20) NOT NULL AUTO_INCREMENT comment '自动增长id',
`pro_name` VARCHAR(644) NOT NULL default '' comment '公司名称',
`abbreviate` varchar(20) NOT NULL default 'NULL',
`remark` VARCHAR(640) NOT NULL default '' comment '备注',
`update_time` timestamp null on update current_timestamp comment '最近一次更新',
PRIMARY KEY (`pro_id`) 
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 comment '生产商，客户'
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
index `pro_id_index` (`pro_id`)
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

