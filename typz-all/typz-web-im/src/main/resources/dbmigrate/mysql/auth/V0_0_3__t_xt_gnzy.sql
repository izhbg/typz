CREATE TABLE `t_xt_gnzy` (
  `GN_DM` varchar(32) NOT NULL,
  `GN_MC` varchar(200) NOT NULL,
  `GN_LX` int(11) DEFAULT NULL,
  `SJGN_DM` varchar(32) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `GN_XH` int(11) DEFAULT NULL,
  `YX_BJ` int(11) DEFAULT NULL,
  `SQ_BJ` int(11) DEFAULT NULL,
  `GN_LS` varchar(10) DEFAULT NULL,
  `BZ` varchar(100) DEFAULT NULL,
  `GN_ICON` varchar(50) DEFAULT NULL,
  `NEWURL` varchar(250) DEFAULT NULL,
  `APP_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`GN_DM`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
