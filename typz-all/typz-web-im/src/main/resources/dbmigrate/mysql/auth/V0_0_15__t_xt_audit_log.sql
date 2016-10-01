CREATE TABLE `t_xt_audit_log` (
  `id` varchar(32) NOT NULL,
  `description` varchar(200) NOT NULL,
  `method` varchar(100) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `request_ip` varchar(32) DEFAULT NULL,
  `exception_code` varchar(50) DEFAULT NULL,
  `exception_detail` text,
  `params` text,
  `create_by` varchar(50) DEFAULT NULL,
  `app_id` varchar(50) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;