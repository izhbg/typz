
INSERT INTO `t_database_maintabletype` VALUES ('1466734969362', '-1', '商品相关', null, null, '1');

INSERT INTO `t_database_maintable` VALUES ('185398328737792', null, null, 't_sh_goods_type', 't_sh_goods_type', '商品类目', 'select * from t_sh_goods_type', '0', '0', '', 'id', '', '1', '1466734969362');
INSERT INTO `t_database_maintable` VALUES ('331495250018304', null, null, 't_sh_goods_tags', 't_sh_goods_tags', '商品标签', 'select * from t_sh_goods_tags', '0', '0', '', 'id', '', '1', '1466734969362');

INSERT INTO `t_database_maintablecolumn` VALUES ('185398901915648', '185398328737792', 'id', null, 'id', '0', '999', '0', null, '1', '999', '1', '999', '0', '999', '6', '', '1', '0', '', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('185398901997568', '185398328737792', 'name', null, '名称', '1', '999', '0', null, '1', '1', '1', '999', '1', '999', '1', '', '1', '0', '', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('185398902079488', '185398328737792', 'pid', null, '父级', '0', '999', '0', null, '1', '2', '1', '999', '1', '999', '8', 'select id ,name,pid from t_sh_goods_type', '1', '0', '', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('185398902145024', '185398328737792', 'sort', null, '排序', '0', '999', '0', null, '1', '3', '1', '999', '1', '999', '1', '', '2', '0', '', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('185398902292480', '185398328737792', 'create_time', null, '创建时间', '0', '999', '0', null, '1', '4', '1', '999', '1', '999', '6', '', '3', '0', 'DateTime()', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('185398902374400', '185398328737792', 'create_user', null, '创建人', '0', '999', '0', null, '1', '5', '1', '999', '1', '999', '6', '', '1', '0', 'CurrentUserName', '0', '', '0', '0', '', '', '', '', '0', '', null);

INSERT INTO `t_database_maintablecolumn` VALUES ('331496563015680', '331495250018304', 'id', null, 'id', '0', '1', '0', null, '1', '1', '1', '1', '0', '1', '6', '', '1', '0', '', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('331496563097600', '331495250018304', 'name', null, '名称', '1', '2', '0', null, '1', '2', '1', '2', '1', '2', '1', '', '1', '0', '', '0', '', '1', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('331496563163136', '331495250018304', 'xh', null, '序号', '0', '4', '0', null, '1', '4', '1', '4', '1', '4', '1', '', '1', '1', '', '0', '', '0', '0', '', '', '', '', '0', '', null);
INSERT INTO `t_database_maintablecolumn` VALUES ('331496563310592', '331495250018304', 'color', null, '标记颜色', '0', '3', '0', null, '1', '3', '1', '3', '1', '3', '1', '', '1', '0', '', '0', '', '0', '0', '', '', '', '', '0', '', null);

INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'lmgl', '1466992727356', '1', '1', '1', '1', '0');
INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'splb', '1466992727364', '1', '1', '1', '1', '0');
INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'dpsplb', '1475648473888', '1', '1', '1', '1', '0');
INSERT INTO `t_xt_gnjs_zy` VALUES ('1', 'spbq', '1475651810986', '1', '1', '1', '1', '0');

INSERT INTO `t_xt_gnzy` VALUES ('spgl', '商品管理', '1', 'c78657w5m0vg781mw5m1', null, '5', '1', '1', null, 'red', 'fa-retweet', '', '1');
INSERT INTO `t_xt_gnzy` VALUES ('lmgl', '类目管理', '1', 'spgl', null, '1', '1', '1', null, 'red', 'fa-retweet', 'maintabledata/maintabledata_pagelist.izhbg?tableName=t_sh_goods_type', '1');
INSERT INTO `t_xt_gnzy` VALUES ('splb', '商品列表', '1', 'spgl', null, '2', '1', '1', null, 'red', 'fa-retweet', 'goods/goods_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('dpsplb', '我的商品', '1', 'spgl', null, '2', '1', '1', null, 'red', 'fa-retweet', 'goods/goods_store_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('spbq', '商品标签', '1', 'spgl', null, '4', '1', '1', null, 'red', 'fa-retweet', 'maintabledata/maintabledata_pagelist.izhbg?tableName=t_sh_goods_tags', '1');