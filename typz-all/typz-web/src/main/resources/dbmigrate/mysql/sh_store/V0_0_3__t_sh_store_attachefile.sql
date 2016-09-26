CREATE TABLE `t_sh_store_attachefile` (
  `id` varchar(50) NOT NULL,
  `path` varchar(100) DEFAULT NULL,
  `size` double DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `yh_id` varchar(50) DEFAULT NULL,
  `content` varchar(100) DEFAULT NULL,
  `store_id` varchar(50) DEFAULT NULL,
  `xh` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

