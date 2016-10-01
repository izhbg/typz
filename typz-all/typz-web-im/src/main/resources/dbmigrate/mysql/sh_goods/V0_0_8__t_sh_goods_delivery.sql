CREATE TABLE `t_sh_goods_delivery` (
  `id` varchar(50) NOT NULL,
  `address_self` varchar(150) DEFAULT NULL,
  `psfw` varchar(150) DEFAULT NULL,
  `pssm` varchar(150) DEFAULT NULL,
  `tcyf` double DEFAULT NULL,
  `tsyf` double DEFAULT NULL,
  `qgyf` double DEFAULT NULL,
  `bytj` varchar(150) DEFAULT NULL,
  `shop_id` varchar(50) DEFAULT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `is_self` tinyint(4) DEFAULT NULL,
  `is_send` tinyint(4) DEFAULT NULL,
  `is_delivery` tinyint(4) DEFAULT NULL,
  `is_tc_delivery` tinyint(4) DEFAULT NULL,
  `is_qg_delivery` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



