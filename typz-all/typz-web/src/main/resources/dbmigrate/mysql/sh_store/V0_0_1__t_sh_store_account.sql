CREATE TABLE `t_sh_store_account` (
  `id` varchar(50) NOT NULL,
  `account_type_id` varchar(50) DEFAULT NULL,
  `account_no` varchar(50) DEFAULT NULL,
  `account_name` varchar(50) DEFAULT NULL,
  `bank_id` varchar(50) DEFAULT NULL,
  `is_selected` tinyint(4) DEFAULT NULL,
  `other` varchar(255) DEFAULT NULL,
  `change_time` datetime DEFAULT NULL,
  `member_id` varchar(50) DEFAULT NULL,
  `sc_bj` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
