CREATE TABLE `t_database_maintablebutton` (
  `button_id` varchar(20) NOT NULL DEFAULT '',
  `button_name` varchar(20) DEFAULT NULL,
  `button_sql` varchar(500) DEFAULT NULL,
  `button_order` int(10) DEFAULT NULL,
  `button_table` varchar(20) DEFAULT NULL,
  `button_action` varchar(500) DEFAULT NULL,
  `button_other` varchar(20) DEFAULT '0',
  `button_target` varchar(20) DEFAULT NULL,
  `button_executetype` varchar(20) DEFAULT NULL,
  `APP_ID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`button_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

