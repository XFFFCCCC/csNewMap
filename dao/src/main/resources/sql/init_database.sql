CREATE DATABASE IF NOT EXISTS `health_virus`;

CREATE USER IF NOT EXISTS 'health_virus'@'%' identified BY 'yangxuechao321';

GRANT ALL ON health_virus.* TO 'health_virus'@'%';