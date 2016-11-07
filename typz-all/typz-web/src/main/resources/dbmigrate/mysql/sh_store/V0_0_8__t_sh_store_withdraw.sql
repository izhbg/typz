CREATE TABLE `t_sh_store_withdraw` (
  `id` varchar(50) NOT NULL,
  `money` double DEFAULT NULL,
  `ask_time` datetime DEFAULT NULL,
  `member_id` varchar(50) DEFAULT NULL,
  `state` tinyint(4) DEFAULT '1',
  `sh_time` datetime DEFAULT NULL,
  `other` varchar(100) DEFAULT NULL,
  `account_type_id` varchar(50) DEFAULT NULL,
  `bank_id` varchar(50) DEFAULT NULL,
  `account_no` varchar(50) DEFAULT NULL,
  `account_name` varchar(50) DEFAULT NULL,
  `total_money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

