CREATE TABLE `t_xt_yy` (
  `YY_ID` varchar(50) NOT NULL,
  `APP_NAME` varchar(50) NOT NULL,
  `chinese_name` varchar(50) DEFAULT NULL,
  `deploy_url` varchar(200) NOT NULL,
  `home_page` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `RESPECTIVE_DIVISIONS` varchar(50) DEFAULT NULL,
  `CHARGER` varchar(20) DEFAULT NULL,
  `CONTACT` varchar(20) DEFAULT NULL,
  `LOGO_MARK` varchar(20) DEFAULT NULL,
  `CODE` varchar(20) DEFAULT NULL,
  `CLASSIFICATION` int(11) DEFAULT NULL,
  `SHOW_FLAG` varchar(2) DEFAULT NULL,
  `LOGIN_FLAG` varchar(2) DEFAULT NULL,
  `SORT_NO` int(11) DEFAULT NULL,
  `OPERATE_TIME` varchar(30) DEFAULT NULL,
  `LOGIN_DISPLAY` varchar(50) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `YX_BJ` int(11) DEFAULT NULL,
  PRIMARY KEY (`YY_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

