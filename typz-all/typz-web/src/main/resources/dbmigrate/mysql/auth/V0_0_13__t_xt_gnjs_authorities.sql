CREATE TABLE `t_xt_gnjs_authorities` (
   ID           VARCHAR(50) not null,  
   ROLE_ID      VARCHAR(32),  
   AUTHORITY_ID VARCHAR(32),  
   ENABLED      int(1),
   PRIMARY KEY (`ID`)  
) ENGINE=MyISAM DEFAULT CHARSET=utf8;