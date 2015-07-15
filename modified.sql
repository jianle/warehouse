alter table `goods` ADD COLUMN `operator_id` int(11) NOT NULL DEFAULT '0' COMMENT '操作者' AFTER `user_id`;
