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


