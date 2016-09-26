CREATE TABLE `t_sh_person_address` (
  `id` varchar(50) NOT NULL,
  `shr_name` varchar(50) DEFAULT NULL,
  `shr_phone` varchar(50) DEFAULT NULL,
  `district_id` varchar(50) DEFAULT NULL,
  `shr_xxdz` varchar(200) DEFAULT NULL,
  `yh_id` varchar(50) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `is_enable` int(11) DEFAULT '1',
  `other` varchar(100) DEFAULT NULL,
  `shr_yb` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;