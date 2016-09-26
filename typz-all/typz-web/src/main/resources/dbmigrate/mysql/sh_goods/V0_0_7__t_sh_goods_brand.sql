CREATE TABLE `t_sh_goods_brand` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `pid` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `del_status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



