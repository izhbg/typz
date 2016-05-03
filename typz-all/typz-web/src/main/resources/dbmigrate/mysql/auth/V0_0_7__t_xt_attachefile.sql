CREATE TABLE `t_xt_attachfile` (
  `ID` varchar(32) NOT NULL DEFAULT '',
  `ATTACH_FILE_NAME` varchar(200) DEFAULT NULL,
  `CONF_ID` varchar(32) DEFAULT NULL,
  `PATH` varchar(200) DEFAULT NULL,
  `ATTACH_SHOW_NAME` varchar(200) DEFAULT NULL,
  `ATTACH_TYPE` varchar(11) DEFAULT NULL,
  `ATTACH_TIME` datetime DEFAULT NULL,
  `ATTACH_SIZE` varchar(20) DEFAULT NULL,
  `OTHER` varchar(200) DEFAULT NULL,
  `YH_ID` varchar(32) DEFAULT NULL,
  `YH_MC` varchar(32) DEFAULT NULL,
  `isread` int(2) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;