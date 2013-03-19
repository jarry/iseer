-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.20 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2013-03-18 22:08:08
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping structure for table iseer.color_features
DROP TABLE IF EXISTS `color_features`;
CREATE TABLE IF NOT EXISTS `color_features` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `IMG_ID` int(11) DEFAULT NULL,
  `F1` int(11) DEFAULT NULL,
  `F2` int(11) DEFAULT NULL,
  `F3` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_color_features_media` (`IMG_ID`),
  CONSTRAINT `FK_color_features_media` FOREIGN KEY (`IMG_ID`) REFERENCES `images` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图像颜色功能库';

-- Data exporting was unselected.


-- Dumping structure for table iseer.images
DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(10) DEFAULT NULL,
  `NAME` varchar(255) NOT NULL,
  `WIDTH` int(11) NOT NULL,
  `HEIGHT` int(11) NOT NULL,
  `HOST` varchar(50) DEFAULT NULL,
  `VIA_TYPE` varchar(50) DEFAULT NULL,
  `FROM_URL` varchar(255) DEFAULT NULL,
  `MWIDTH` int(11) DEFAULT '0',
  `MHEIGHT` int(11) DEFAULT '0',
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ix_media_type` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table iseer.texture_features
DROP TABLE IF EXISTS `texture_features`;
CREATE TABLE IF NOT EXISTS `texture_features` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `IMG_ID` int(11) DEFAULT NULL,
  `E_L` int(11) DEFAULT NULL,
  `E_H` int(11) DEFAULT NULL,
  `E_V` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FK_texture_features_media` (`IMG_ID`),
  CONSTRAINT `FK_texture_features_media` FOREIGN KEY (`IMG_ID`) REFERENCES `images` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图像功能库';

-- Data exporting was unselected.


-- Dumping structure for table iseer.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(128) NOT NULL,
  `NICKNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ACTIVE` tinyint(4) NOT NULL COMMENT '是否有效的用户，1有效，0禁用',
  `CREATE_TIME` datetime DEFAULT NULL,
  `MODIFY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=234 COMMENT='用户实体表';

-- Data exporting was unselected.
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
