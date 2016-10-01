CREATE TABLE `t_sh_purchase` (
  `id` varchar(50) NOT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `yh_id` varchar(50) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `store_id` varchar(50) DEFAULT NULL,
  `is_discut` int(10) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;