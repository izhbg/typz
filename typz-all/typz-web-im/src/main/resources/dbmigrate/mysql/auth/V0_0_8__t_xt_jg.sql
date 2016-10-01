CREATE TABLE `t_xt_jg` (
  `JG_ID` varchar(32) NOT NULL,
  `JG_DM` varchar(100) DEFAULT NULL,
  `JG_MC` varchar(100) DEFAULT NULL,
  `SC_BJ` int(11) DEFAULT NULL,
  `YX_BJ` int(11) DEFAULT NULL,
  `LR_RQ` timestamp NULL DEFAULT NULL,
  `SJJG_ID` varchar(32) DEFAULT NULL,
  `XH` int(20) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `XG_RQ` timestamp NULL DEFAULT NULL,
  `CZRY_ID` varchar(200) DEFAULT NULL,
  `CCM` varchar(200) DEFAULT NULL,
  `JG_LX` int(11) DEFAULT NULL,
  `JG_BM` int(11) DEFAULT NULL,
  `LEL` varchar(50) DEFAULT NULL,
  `NEWURL` varchar(255) DEFAULT NULL,
  `APP_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`JG_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

