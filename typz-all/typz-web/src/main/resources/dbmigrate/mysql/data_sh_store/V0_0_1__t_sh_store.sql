INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'dplb', '1475462045089', '1', '1', '1', '1', '0');
INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'dtx', '1477983747917', '0', '1', '0', '0', '0');
INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'ytx', '1477983747918', '0', '1', '0', '0', '0');
INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'ddgl', '1478069040751', '0', '0', '0', '0', '0');

INSERT INTO `t_xt_gnzy` VALUES ('dpgl', '店铺管理', '1', 'c78657w5m0vg781mw5m1', null, '2', '1', '1', null, 'red', 'fa-retweet', '', '1');
INSERT INTO `t_xt_gnzy` VALUES ('dplb', '店铺列表', '1', 'dpgl', null, null, '1', '1', null, '', '', 'store/store_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('txgl', '提现管理', '1', 'c78657w5m0vg781mw5m1', null, '3', '1', '1', null, 'red', 'fa-cogs', '', '1');
INSERT INTO `t_xt_gnzy` VALUES ('dtx', '待提现', '1', 'txgl', null, '1', '1', '1', null, '', '', 'storeWithdraw/store-withdraw-waite-list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('ytx', '已提现', '1', 'txgl', null, '2', '1', '1', null, '', '', 'storeWithdraw/store-withdraw-history-list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('ddgl', '订单列表', '1', 'dpgl', null, '2', '1', '1', null, 'red', '', 'store/store_order_list.izhbg', '1');