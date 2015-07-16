alter table `goods` ADD COLUMN `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作者' AFTER `user_id`;
update goods set operator_id=user_id;

alter table `enter` ADD COLUMN `operator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作者' AFTER `user_id`;
update enter set operator_id=user_id;


alter table `orderinfo` ADD COLUMN `operator_id` bigint(20) DEFAULT '0' COMMENT '操作人' AFTER `os_id`;
update orderinfo set operator_id=user_id;

----------


