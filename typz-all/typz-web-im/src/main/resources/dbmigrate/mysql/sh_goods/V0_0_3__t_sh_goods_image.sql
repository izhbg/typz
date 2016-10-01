CREATE TABLE `t_sh_goods_image` (
  `id` varchar(50) NOT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `resolution` int(20) DEFAULT NULL,
  `path` varchar(200) DEFAULT NULL,
  `is_face` int(11) DEFAULT NULL,
  `del_status` int(11) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



