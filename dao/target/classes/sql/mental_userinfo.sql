/* 疫情心理用户信息*/
CREATE TABLE IF NOT EXISTS `mental_userinfo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `age_section` varchar(32) NOT NULL DEFAULT '' COMMENT '年龄区间',
  `district` varchar(32) NOT NULL DEFAULT '' COMMENT '行政区',
  `health_status` varchar(128) NOT NULL DEFAULT '' COMMENT 'url',
  `gender` tinyint NOT NULL DEFAULT '0' COMMENT '1男，2女，0未知',
  `add_timestamp_mils` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '添加时间戳',
  `mod_timestamp_mils` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user` (`user_id`)
) CHARSET=utf8mb4;


