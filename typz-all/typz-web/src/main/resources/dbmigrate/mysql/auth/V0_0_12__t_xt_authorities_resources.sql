CREATE TABLE `t_xt_authorities_resources` (
  ID           VARCHAR(13) not null,  
  AUTHORITY_ID VARCHAR(32),  
  RESOURCE_ID  VARCHAR(32),  
  ENABLED      int(1), 
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;