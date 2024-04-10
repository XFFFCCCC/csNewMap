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


