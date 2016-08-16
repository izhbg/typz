CREATE TABLE `t_sh_goods_basic` (
  `id` varchar(50) NOT NULL COMMENT '唯一标示',
  `shop_basic_id` varchar(50) DEFAULT NULL COMMENT '所属店铺唯一性标示',
  `name` varchar(200) DEFAULT NULL COMMENT '名称',
  `version` int(50) DEFAULT NULL COMMENT '版本号',
  `alias_name` varchar(200) DEFAULT NULL COMMENT '别名',
  `brand_id` varchar(50) DEFAULT NULL COMMENT '品牌外键',
  `specifications_id` varchar(50) DEFAULT NULL COMMENT '规格',
  `unit` varchar(20) DEFAULT NULL COMMENT '单位',
  `model_id` varchar(50) DEFAULT NULL COMMENT '型号',
  `type_id` varchar(50) DEFAULT NULL COMMENT '类型',
  `vender` varchar(50) DEFAULT NULL COMMENT '规格',
  `del_status` int(11) DEFAULT NULL COMMENT '删除状态  正常：1 删除：-1',
  `status` int(11) DEFAULT NULL COMMENT '状态  上架：1 下架：-1',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  `other` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



