CREATE TABLE `t_sh_member` (
  `id` varchar(50) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `age` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `phone` varchar(50) DEFAULT NULL,
  `time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `la` double DEFAULT NULL,
  `lo` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

