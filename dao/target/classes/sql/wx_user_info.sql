/* 微信用户角色*/
CREATE TABLE IF NOT EXISTS `wx_user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `open_id` varchar(256) NOT NULL DEFAULT '' COMMENT '用户公众号开放ID',
  `user_mobile` char(16) NOT NULL DEFAULT '' COMMENT '用户手机号',
  `nick_name` varchar(64) NOT NULL DEFAULT '' COMMENT '微信昵称',
  `gender` varchar(12) NOT NULL DEFAULT '' COMMENT '性别',
  `language` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '语言',
  `city` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '城市',
  `province` VARCHAR (32) NOT NULL DEFAULT '' COMMENT '省',
  `country` VARCHAR (32) NOT NULL DEFAULT '' COMMENT '国家',
  `avatar_url` VARCHAR (256) NOT NULL DEFAULT '' COMMENT '头像图片',
  `union_id` VARCHAR (256) NOT NULL DEFAULT '' COMMENT '联合唯一ID,标识用户身份',
  `add_timestamp_mils` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '添加时间戳',
  `mod_timestamp_mils` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`),
  KEY `phone` (`user_mobile`)
) CHARSET=utf8mb4;


