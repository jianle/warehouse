alter table `invoice_income` add column con_name varchar(256) not null default '' comment '开票公司名称' after `con_id`;

