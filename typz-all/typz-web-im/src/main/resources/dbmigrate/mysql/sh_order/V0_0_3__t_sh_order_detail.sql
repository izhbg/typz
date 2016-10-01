CREATE TABLE `t_sh_order_detail` (
  `id` varchar(50) NOT NULL COMMENT '唯一标示',
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单外键',
  `sh_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '收货时间',
  `fh_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '发货时间',
  `sqth_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '申请退货时间',
  `thcl_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '退货处理时间',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '支付类型',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '交易号',
  `trade_state` tinyint(4) DEFAULT NULL COMMENT '交易状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

