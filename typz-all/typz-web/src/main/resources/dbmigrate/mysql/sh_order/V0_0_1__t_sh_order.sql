CREATE TABLE `t_sh_order` (
  `id` varchar(50) NOT NULL COMMENT '唯一标示',
  `total_price` double(10,2) DEFAULT NULL COMMENT '总价格',
  `total_count` int(11) DEFAULT NULL COMMENT '总数量',
  `yf` double DEFAULT NULL COMMENT '邮费',
  `sc_bj` tinyint(4) DEFAULT NULL COMMENT '删除标记',
  `time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `store_id` varchar(50) DEFAULT NULL COMMENT '店铺标石',
  `yh_id` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `other` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

