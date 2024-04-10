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


/* 疫情新闻*/
CREATE TABLE IF NOT EXISTS `ncov_news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(96) NOT NULL DEFAULT '' COMMENT '标题',
  `url` varchar(256) NOT NULL DEFAULT '' COMMENT 'url',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '内容',
  `timestamp` bigint NOT NULL DEFAULT '0' COMMENT '新闻事件发生戳，单位：毫秒',
  `latitude` double(12,8) NOT NULL DEFAULT '0.0' COMMENT '纬度',
  `longitude` double(12,8) NOT NULL DEFAULT '0.0' COMMENT '经度',
  `committed` tinyint NOT NULL DEFAULT '0' COMMENT '审核，0未审核，1已审核',
  `contributory` varchar(32) NOT NULL DEFAULT '' COMMENT '贡献人',
  `add_timestamp_mils` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '添加时间戳',
  `mod_timestamp_mils` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '修改时间戳',
  PRIMARY KEY (`id`),
  KEY `news_time` (`timestamp`)
) CHARSET=utf8mb4;


/* 心理id生成表*/
CREATE TABLE IF NOT EXISTS `mental_id` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ph` char(1) NOT NULL DEFAULT '' COMMENT '占位',
  PRIMARY KEY (`id`)
) CHARSET=utf8mb4 AUTO_INCREMENT=10000;

/* 疫情心理答案*/
CREATE TABLE IF NOT EXISTS `mental_answer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `coarse_answer` varchar(4096) NOT NULL DEFAULT '' COMMENT '粗糙问卷答案',
  `precise_answer` varchar(4096) NOT NULL DEFAULT '' COMMENT '精确问卷答案',
  `timestamp` BIGINT(15) NOT NULL DEFAULT '0' COMMENT '添加时间戳',
  PRIMARY KEY (`id`),
  KEY `user` (`user_id`)
) CHARSET=utf8mb4;

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




