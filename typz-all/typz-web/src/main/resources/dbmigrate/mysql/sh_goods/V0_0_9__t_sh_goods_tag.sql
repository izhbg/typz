CREATE TABLE `t_sh_goods_tag` (
  `id` varchar(50) NOT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `tag_id` varchar(50) DEFAULT NULL,
  `xh` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;