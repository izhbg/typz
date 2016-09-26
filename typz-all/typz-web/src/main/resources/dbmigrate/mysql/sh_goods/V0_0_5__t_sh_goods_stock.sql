CREATE TABLE `t_sh_goods_stock` (
  `id` varchar(50) NOT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `stock_count` int(11) DEFAULT NULL,
  `freeze_count` int(11) DEFAULT NULL,
  `marketable_count` int(11) DEFAULT NULL,
  `unit_cost` double DEFAULT NULL,
  `other` varchar(100) DEFAULT NULL,
  `del_status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



