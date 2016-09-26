CREATE TABLE `t_sh_order_address` (
  `id` varchar(50) NOT NULL COMMENT '唯一标示',
  `shr_name` varchar(50) DEFAULT NULL COMMENT '收货人姓名',
  `shr_phone` varchar(50) DEFAULT NULL COMMENT '收货人电话',
  `sh_address` varchar(200) DEFAULT NULL COMMENT '收货人地址',
  `address_self` varchar(200) DEFAULT NULL COMMENT '自提地址',
  `tsyq` varchar(200) DEFAULT NULL COMMENT '特殊要求',
  `is_self` int(10) DEFAULT NULL COMMENT '是否自提',
  `is_delivery` int(10) DEFAULT NULL COMMENT '是否快递',
  `is_send` int(10) DEFAULT NULL COMMENT '是否送货上门',
  `yd_no` varchar(50) DEFAULT NULL COMMENT '快递号',
  `yd_type_id` varchar(50) DEFAULT NULL COMMENT '快递公司外键',
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单外键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

