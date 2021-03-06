CREATE TABLE `t_sales_report` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SHOP_ID` bigint(20) NOT NULL,
  `PRODUCT_ID` bigint(20) NOT NULL,
  `REPORT_TYPE` varchar(20) DEFAULT NULL,
  `DATE_TO` date DEFAULT NULL,
  `RANK` varchar(11) DEFAULT NULL,
  `TOTAL_SALE_AMOUNT` bigint(20) DEFAULT NULL,
  `TOTAL_REVENUE` decimal(10,0) DEFAULT NULL,
  `CREATED_BY` varchar(128) DEFAULT NULL,
  `UPDATED_BY` varchar(128) DEFAULT NULL,
  `UPDATED_AT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_AT` timestamp,
  PRIMARY KEY (`ID`),
  KEY `FK_SALES_REPORT_SHOP_ID_CONSTRAINT` (`SHOP_ID`),
  KEY `FK_SALES_REPORT_PRODUCT_ID_CONSTRAINT` (`PRODUCT_ID`),
  CONSTRAINT `FK_SALES_REPORT_SHOP_ID_CONSTRAINT` FOREIGN KEY (`SHOP_ID`) REFERENCES `t_sales_shop` (`ID`),
  CONSTRAINT `FK_SALES_REPORT_PRODUCT_ID_CONSTRAINT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `t_sales_product` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

