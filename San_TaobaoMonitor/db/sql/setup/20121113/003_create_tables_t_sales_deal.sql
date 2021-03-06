CREATE TABLE `t_sales_deal` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `UNIT_PRICE` decimal(10,0) unsigned zerofill DEFAULT NULL,
  `SALES_AMOUNT` int(10) unsigned zerofill DEFAULT NULL,
  `TOTAL_PRICE` decimal(10,0) unsigned zerofill DEFAULT NULL,
  `DEAL_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PRODUCT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PRODUCT_ID_CONSTRAINT` (`PRODUCT_ID`),
  KEY `DEAL_DATE` (`DEAL_DATE`),
  CONSTRAINT `FK_PRODUCT_ID_CONSTRAINT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `t_sales_product` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

