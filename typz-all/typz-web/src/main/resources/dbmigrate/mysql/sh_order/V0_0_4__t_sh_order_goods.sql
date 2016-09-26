CREATE TABLE `t_sh_order_goods` (
  `id` varchar(50) NOT NULL COMMENT '唯一标石',
  `goods_id` varchar(50) DEFAULT NULL COMMENT '产品外键',
  `shop_id` varchar(50) DEFAULT NULL COMMENT '店铺外键',
  `num` int(10) DEFAULT NULL COMMENT '数量',
  `price` double DEFAULT NULL COMMENT '价格',
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单外键', 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

