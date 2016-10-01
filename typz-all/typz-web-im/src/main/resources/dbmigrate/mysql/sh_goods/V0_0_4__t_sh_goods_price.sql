CREATE TABLE `t_sh_goods_price` (
  `id` varchar(50) NOT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `price_type` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `del_status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



