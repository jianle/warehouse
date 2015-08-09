use finance_db;
alter table `invoice_income` add column con_name varchar(256) not null default '' comment '开票公司名称' after `con_id`;


use warehouse_db;
alter table `goods` add column `model` varchar(128) default '' comment '型号' after `name`;
alter table `goods` add column `lrack` varchar(128) not null default '{"lrack1":"01","lrack2":"A","lrack3":"01"}' comment '大货架' after `model`;
alter table `goods` add column `mrack` varchar(128) not null  default '{"mrack1":"01","mrack2":"A","mrack3":"01"}' comment '小货架' after `lrack`;

model, lrack, mrack
