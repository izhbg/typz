CREATE TABLE `t_sh_store_goods` (
  `id` varchar(50) NOT NULL,
  `store_id` varchar(50) DEFAULT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
