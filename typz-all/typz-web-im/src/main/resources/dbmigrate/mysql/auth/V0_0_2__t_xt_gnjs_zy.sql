CREATE TABLE `t_xt_gnjs_zy` (
  `JS_DM` varchar(32) DEFAULT NULL,
  `GNZY_DM` varchar(32) DEFAULT NULL,
  `uuid` varchar(50) NOT NULL DEFAULT '',
  `is_read` int(11) DEFAULT '0',
  `is_create` int(11) DEFAULT '0',
  `is_update` int(11) DEFAULT '0',
  `is_delete` int(11) DEFAULT '0',
  `is_all` int(11) DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;