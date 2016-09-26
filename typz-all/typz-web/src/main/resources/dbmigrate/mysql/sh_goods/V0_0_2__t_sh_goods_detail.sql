CREATE TABLE `t_sh_goods_detail` (
  `id` varchar(50) NOT NULL,
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  `goods_id` varchar(50) DEFAULT NULL COMMENT '商品基本信息外键',
  `content` text COMMENT '商品详情',
  `sale_status` int(11) DEFAULT NULL COMMENT '销售状态',
  `stock_status` int(11) DEFAULT NULL COMMENT '库存状态',
  `to_pro_time` datetime DEFAULT NULL COMMENT '提审时间',
  `on_sale_time` datetime DEFAULT NULL COMMENT '上架时间',
  `un_sale_time` datetime DEFAULT NULL COMMENT '下架时间',
  `del_status` int(11) DEFAULT NULL COMMENT '删除状态',
  `del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `pro_status` int(11) DEFAULT NULL COMMENT '审核状态',
  `unpro_reason` varchar(100) DEFAULT NULL COMMENT '审核原因',
  `pro_time` datetime DEFAULT NULL COMMENT '审核时间',
  `default_price` double DEFAULT NULL COMMENT '默认价格',
  `sale_count` int(11) DEFAULT NULL COMMENT '销售数量',
  `view_count` int(11) DEFAULT NULL COMMENT '浏览数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

