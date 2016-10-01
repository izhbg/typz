INSERT INTO `t_xt_gnzy` VALUES ('c78657w5m0vg781mw5m1', '系统功能', null, '-1', null, '8', '1', '1', null, null, null, null, '1');

INSERT INTO `t_xt_gnzy` VALUES ('firstpage', '首页', null, 'c78657w5m0vg781mw5m1', null, '1', '1', '1', null, '', 'fa-home', 'main/common.izhbg', '1');

INSERT INTO `t_xt_gnzy` VALUES ('houtanguanli', '系统配置', '1', 'c78657w5m0vg781mw5m1', null, '15', '1', '1', null, '', 'fa-cogs', '', '1');
INSERT INTO `t_xt_gnzy` VALUES ('usermanager', '用户管理', '1', 'houtanguanli', null, '1', '1', '2', null, 'blue', 'fa-group', 'user/user-list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('rolemanager', '角色管理', '1', 'houtanguanli', null, '2', '1', '2', null, 'yellow', 'fa-foursquare', 'role/role-list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('departmanager', '机构管理', '1', 'houtanguanli', null, '4', '1', '2', null, 'purple', 'fa-sitemap', 'org/org-list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('funmanager', '功能管理', '1', 'houtanguanli', null, '5', '1', '2', null, 'blue', 'fa-dropbox', 'fun/fun_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('appmanager', '应用管理', '1', 'houtanguanli', null, '6', '1', '2', null, 'green', 'fa-html5', 'sys/sys_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('ziyuanguanli', '资源管理', '1', 'houtanguanli', null, '7', '1', '2', null, 'red', 'fa-stack-overflow', 'resources/resources_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('quanxianguanli', '权限管理', '1', 'houtanguanli', null, '8', '1', '2', null, 'yellow', ' fa-unlock-o', 'authorities/authorities_list.izhbg', '1');
INSERT INTO `t_xt_gnzy` VALUES ('jiekouguize', '接口规则', '1', 'houtanguanli', null, '9', '1', '2', null, 'yellow', ' fa-unlock-o', 'client/client_list.izhbg', '1');

INSERT INTO `t_xt_gnzy` VALUES ('shenjirizhi', '审计日志', '1', 'c78657w5m0vg781mw5m1', null, '2', '1', '1', null, '', 'fa-book', '', '1');
INSERT INTO `t_xt_gnzy` VALUES ('shenjirizhi2', '审计日志', '1', 'shenjirizhi', null, '2', '1', '2', null, 'red', 'fa-book', 'audit/audit_list.izhbg', '1');

INSERT INTO `t_xt_gnzy` VALUES ('biaodanguanli', '表单管理', '1', 'c78657w5m0vg781mw5m1', null, '2', '1', '1', null, '', ' fa-file-text-o', '', '1');
INSERT INTO `t_xt_gnzy` VALUES ('biaodanpeizhi', '表单配置', '1', 'biaodanguanli', null, '2', '1', '2', null, 'red', ' fa-file-text-o', 'maintable/maintable_list.izhbg', '1');

