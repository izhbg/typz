CREATE TABLE `t_xt_gnjs` (
  `GNJS_DM` varchar(32) NOT NULL,
  `GNJS_MC` varchar(100) NOT NULL,
  `JG_ID` varchar(32) DEFAULT NULL,
  `JS_LX` int(11) DEFAULT NULL,
  `YX_BJ` int(11) DEFAULT NULL,
  `BZ` varchar(100) DEFAULT NULL,
  `APP_ID` varchar(32) DEFAULT NULL,
  `CODE` varchar(50) DEFAULT NULL,
  `viewDirIds` text,
  `resourceIds` text,
  `viewSelfDept` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`GNJS_DM`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

