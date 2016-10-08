CREATE TABLE `t_sh_store` (
  `id` varchar(50) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `content` text,
  `other` varchar(100) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `state` tinyint(4) DEFAULT '0',
  `shyj` varchar(200) DEFAULT NULL,
  `sc_bj` tinyint(4) DEFAULT NULL,
  `sq_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `sh_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `sh_user_id` varchar(50) DEFAULT NULL,
  `yh_id` varchar(50) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `la` double DEFAULT NULL,
  `lo` double DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

