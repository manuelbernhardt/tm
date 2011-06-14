-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Product`
--

DROP TABLE IF EXISTS `Product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Product`
--

LOCK TABLES `Product` WRITE;
/*!40000 ALTER TABLE `Product` DISABLE KEYS */;
/*!40000 ALTER TABLE `Product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `postalCode` varchar(255) DEFAULT NULL,
  `street1` varchar(255) DEFAULT NULL,
  `street2` varchar(255) DEFAULT NULL,
  `contactFirstName` varchar(255) DEFAULT NULL,
  `contactLastName` varchar(255) DEFAULT NULL,
  `credit` double DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `subdomain` varchar(255) NOT NULL,
  `vatCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--
-- WHERE:  id = 1

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES (1,'2011-06-14 21:34:09','2011-06-14 21:34:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'demo@example.com','ACME',NULL,'demo',NULL);
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `AccountProduct`
--

DROP TABLE IF EXISTS `AccountProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountProduct` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `active` bit(1) DEFAULT NULL,
  `pricePerSeatMonth` double DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`account_id`),
  KEY `FKDC9F4A22A63214B0` (`product_id`),
  KEY `FKDC9F4A22D1149035` (`account_id`),
  CONSTRAINT `FKDC9F4A22D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKDC9F4A22A63214B0` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccountProduct`
--
-- WHERE:  account_id = 1

LOCK TABLES `AccountProduct` WRITE;
/*!40000 ALTER TABLE `AccountProduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `AccountProduct` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_AccountProduct
BEFORE INSERT ON AccountProduct FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM AccountProduct
  WHERE account_id=NEW.account_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `AccountProductPeriod`
--

DROP TABLE IF EXISTS `AccountProductPeriod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AccountProductPeriod` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `availableSeats` int(11) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`account_id`),
  KEY `FKCB5C0A83A63214B0` (`product_id`),
  KEY `FKCB5C0A83D1149035` (`account_id`),
  CONSTRAINT `FKCB5C0A83D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKCB5C0A83A63214B0` FOREIGN KEY (`product_id`) REFERENCES `Product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AccountProductPeriod`
--
-- WHERE:  account_id = 1

LOCK TABLES `AccountProductPeriod` WRITE;
/*!40000 ALTER TABLE `AccountProductPeriod` DISABLE KEYS */;
/*!40000 ALTER TABLE `AccountProductPeriod` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_AccountProductPeriod
BEFORE INSERT ON AccountProductPeriod FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM AccountProductPeriod
  WHERE account_id=NEW.account_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `PurchaseOrder`
--

DROP TABLE IF EXISTS `PurchaseOrder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PurchaseOrder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `dateFrom` datetime DEFAULT NULL,
  `dateTo` datetime DEFAULT NULL,
  `orderDate` datetime DEFAULT NULL,
  `pricePerSeatMonth` double DEFAULT NULL,
  `seats` int(11) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`account_id`),
  KEY `FKDFD4BAAD26A06A72` (`product_id`),
  KEY `FKDFD4BAADD1149035` (`account_id`),
  CONSTRAINT `FKDFD4BAADD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKDFD4BAAD26A06A72` FOREIGN KEY (`product_id`) REFERENCES `AccountProduct` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PurchaseOrder`
--
-- WHERE:  account_id = 1

LOCK TABLES `PurchaseOrder` WRITE;
/*!40000 ALTER TABLE `PurchaseOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `PurchaseOrder` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_PurchaseOrder
BEFORE INSERT ON PurchaseOrder FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM PurchaseOrder
  WHERE account_id=NEW.account_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ApproachRelease`
--

DROP TABLE IF EXISTS `ApproachRelease`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ApproachRelease` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FKB554F31F8A330A89` (`project_id`),
  KEY `FKB554F31FD1149035` (`account_id`),
  CONSTRAINT `FKB554F31FD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKB554F31F8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ApproachRelease`
--
-- WHERE:  account_id = 1

LOCK TABLES `ApproachRelease` WRITE;
/*!40000 ALTER TABLE `ApproachRelease` DISABLE KEYS */;
INSERT INTO `ApproachRelease` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:11',1,'Template',1,1),(2,'2011-06-14 21:34:11','2011-06-14 21:34:11',2,'Roll-out UK',1,1),(3,'2011-06-14 21:34:11','2011-06-14 21:34:11',3,'Roll-out AT',1,1);
/*!40000 ALTER TABLE `ApproachRelease` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ApproachRelease
BEFORE INSERT ON ApproachRelease FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ApproachRelease
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Defect`
--

DROP TABLE IF EXISTS `Defect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Defect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `description` varchar(8000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `assignedTo_id` bigint(20) DEFAULT NULL,
  `status_id` bigint(20) NOT NULL,
  `submittedBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FK79C8B5919F19C4C6` (`assignedTo_id`),
  KEY `FK79C8B5918A330A89` (`project_id`),
  KEY `FK79C8B591AD97425D` (`submittedBy_id`),
  KEY `FK79C8B591A80BE33C` (`status_id`),
  KEY `FK79C8B591D1149035` (`account_id`),
  CONSTRAINT `FK79C8B591D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK79C8B5918A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK79C8B5919F19C4C6` FOREIGN KEY (`assignedTo_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FK79C8B591A80BE33C` FOREIGN KEY (`status_id`) REFERENCES `DefectStatus` (`id`),
  CONSTRAINT `FK79C8B591AD97425D` FOREIGN KEY (`submittedBy_id`) REFERENCES `TMUser` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Defect`
--
-- WHERE:  account_id = 1

LOCK TABLES `Defect` WRITE;
/*!40000 ALTER TABLE `Defect` DISABLE KEYS */;
INSERT INTO `Defect` VALUES (1,'2011-06-02 00:00:00','2011-06-14 21:34:12',1,NULL,'The application does not start',1,1,3,1,2);
/*!40000 ALTER TABLE `Defect` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Defect
BEFORE INSERT ON Defect FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Defect
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `DefectStatus`
--

DROP TABLE IF EXISTS `DefectStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DefectStatus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `defaultOnCreation` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FK564740E38A330A89` (`project_id`),
  KEY `FK564740E3D1149035` (`account_id`),
  CONSTRAINT `FK564740E3D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK564740E38A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DefectStatus`
--
-- WHERE:  account_id = 1

LOCK TABLES `DefectStatus` WRITE;
/*!40000 ALTER TABLE `DefectStatus` DISABLE KEYS */;
INSERT INTO `DefectStatus` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:10',1,'','New',0,1,1);
/*!40000 ALTER TABLE `DefectStatus` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_DefectStatus
BEFORE INSERT ON DefectStatus FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM DefectStatus
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Filter`
--

DROP TABLE IF EXISTS `Filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Filter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `entity` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `owner_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FK7D6DB7988A330A89` (`project_id`),
  KEY `FK7D6DB798D1149035` (`account_id`),
  KEY `FK7D6DB7983E92729C` (`owner_id`),
  CONSTRAINT `FK7D6DB7983E92729C` FOREIGN KEY (`owner_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FK7D6DB7988A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK7D6DB798D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Filter`
--
-- WHERE:  account_id = 1

LOCK TABLES `Filter` WRITE;
/*!40000 ALTER TABLE `Filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `Filter` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Filter
BEFORE INSERT ON Filter FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Filter
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `FilterConstraint`
--

DROP TABLE IF EXISTS `FilterConstraint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FilterConstraint` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `property` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FKA8380B958A330A89` (`project_id`),
  KEY `FKA8380B95D1149035` (`account_id`),
  CONSTRAINT `FKA8380B95D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKA8380B958A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FilterConstraint`
--
-- WHERE:  account_id = 1

LOCK TABLES `FilterConstraint` WRITE;
/*!40000 ALTER TABLE `FilterConstraint` DISABLE KEYS */;
/*!40000 ALTER TABLE `FilterConstraint` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_FilterConstraint
BEFORE INSERT ON FilterConstraint FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM FilterConstraint
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Instance`
--

DROP TABLE IF EXISTS `Instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `plannedExecution` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `responsible_id` bigint(20) DEFAULT NULL,
  `script_id` bigint(20) NOT NULL,
  `testCycle_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK24F0F8B58A330A89` (`project_id`),
  KEY `FK24F0F8B5B3E57F45` (`testCycle_id`),
  KEY `FK24F0F8B5232B42E5` (`script_id`),
  KEY `FK24F0F8B5D1149035` (`account_id`),
  KEY `FK24F0F8B5CDF31379` (`responsible_id`),
  CONSTRAINT `FK24F0F8B5CDF31379` FOREIGN KEY (`responsible_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FK24F0F8B5232B42E5` FOREIGN KEY (`script_id`) REFERENCES `Script` (`id`),
  CONSTRAINT `FK24F0F8B58A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK24F0F8B5B3E57F45` FOREIGN KEY (`testCycle_id`) REFERENCES `TestCycle` (`id`),
  CONSTRAINT `FK24F0F8B5D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Instance`
--
-- WHERE:  account_id = 1

LOCK TABLES `Instance` WRITE;
/*!40000 ALTER TABLE `Instance` DISABLE KEYS */;
INSERT INTO `Instance` VALUES (1,'2011-06-14 21:34:12','2011-06-14 21:34:12',1,'Create new moveable asset','2011-06-18 02:00:00',NULL,1,1,1,1,2),(2,'2011-06-14 21:34:12','2011-06-14 21:34:12',2,'Create new fixed asset','2011-06-18 02:00:00',NULL,1,1,1,1,2);
/*!40000 ALTER TABLE `Instance` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Instance
BEFORE INSERT ON Instance FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Instance
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `InstanceParam`
--

DROP TABLE IF EXISTS `InstanceParam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstanceParam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `value` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `instance_id` bigint(20) NOT NULL,
  `scriptParam_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FKDC5C58D8A718CC2F` (`scriptParam_id`),
  KEY `FKDC5C58D88A330A89` (`project_id`),
  KEY `FKDC5C58D8D1149035` (`account_id`),
  KEY `FKDC5C58D8D46E1FE5` (`instance_id`),
  CONSTRAINT `FKDC5C58D8D46E1FE5` FOREIGN KEY (`instance_id`) REFERENCES `Instance` (`id`),
  CONSTRAINT `FKDC5C58D88A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FKDC5C58D8A718CC2F` FOREIGN KEY (`scriptParam_id`) REFERENCES `ScriptParam` (`id`),
  CONSTRAINT `FKDC5C58D8D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `InstanceParam`
--
-- WHERE:  account_id = 1

LOCK TABLES `InstanceParam` WRITE;
/*!40000 ALTER TABLE `InstanceParam` DISABLE KEYS */;
INSERT INTO `InstanceParam` VALUES (1,'2011-06-14 21:34:12','2011-06-14 21:34:12',1,NULL,1,1,1,1),(2,'2011-06-14 21:34:12','2011-06-14 21:34:12',2,NULL,1,1,1,2),(3,'2011-06-14 21:34:12','2011-06-14 21:34:12',3,NULL,1,1,1,3),(4,'2011-06-14 21:34:12','2011-06-14 21:34:12',4,NULL,1,1,1,4),(5,'2011-06-14 21:34:12','2011-06-14 21:34:12',5,'5',1,1,1,5);
/*!40000 ALTER TABLE `InstanceParam` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_InstanceParam
BEFORE INSERT ON InstanceParam FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM InstanceParam
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `description` varchar(8000) DEFAULT NULL,
  `maxAvailableSeats` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `reservedSeats` int(11) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `projectCategory_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`account_id`),
  KEY `FK50C8E2F9B0F526E9` (`projectCategory_id`),
  KEY `FK50C8E2F926A06A72` (`product_id`),
  KEY `FK50C8E2F9D1149035` (`account_id`),
  CONSTRAINT `FK50C8E2F9D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK50C8E2F926A06A72` FOREIGN KEY (`product_id`) REFERENCES `AccountProduct` (`id`),
  CONSTRAINT `FK50C8E2F9B0F526E9` FOREIGN KEY (`projectCategory_id`) REFERENCES `ProjectCategory` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--
-- WHERE:  account_id = 1

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:10',1,NULL,NULL,'SAP Implementation',NULL,1,NULL,1);
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Project
BEFORE INSERT ON Project FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Project
  WHERE account_id=NEW.account_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ProjectCategory`
--

DROP TABLE IF EXISTS `ProjectCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectCategory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`account_id`),
  KEY `FKB033FB17D1149035` (`account_id`),
  CONSTRAINT `FKB033FB17D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectCategory`
--
-- WHERE:  account_id = 1

LOCK TABLES `ProjectCategory` WRITE;
/*!40000 ALTER TABLE `ProjectCategory` DISABLE KEYS */;
INSERT INTO `ProjectCategory` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:10',1,'ERP',1);
/*!40000 ALTER TABLE `ProjectCategory` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ProjectCategory
BEFORE INSERT ON ProjectCategory FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ProjectCategory
  WHERE account_id=NEW.account_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ProjectRole`
--

DROP TABLE IF EXISTS `ProjectRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectRole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FK2B63B30F8A330A89` (`project_id`),
  KEY `FK2B63B30FD1149035` (`account_id`),
  CONSTRAINT `FK2B63B30FD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK2B63B30F8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectRole`
--
-- WHERE:  account_id = 1

LOCK TABLES `ProjectRole` WRITE;
/*!40000 ALTER TABLE `ProjectRole` DISABLE KEYS */;
INSERT INTO `ProjectRole` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:10',1,'Test Lead',1,1);
/*!40000 ALTER TABLE `ProjectRole` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ProjectRole
BEFORE INSERT ON ProjectRole FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ProjectRole
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ProjectTreeNode`
--

DROP TABLE IF EXISTS `ProjectTreeNode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectTreeNode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nodeId` bigint(20) DEFAULT NULL,
  `opened` bit(1) NOT NULL,
  `path` varchar(5000) DEFAULT NULL,
  `treeId` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `threadRoot_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFFCDD4D9D72BF9B6` (`threadRoot_id`),
  KEY `FKFFCDD4D98A330A89` (`project_id`),
  KEY `FKFFCDD4D9D1149035` (`account_id`),
  CONSTRAINT `FKFFCDD4D9D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKFFCDD4D98A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FKFFCDD4D9D72BF9B6` FOREIGN KEY (`threadRoot_id`) REFERENCES `ProjectTreeNode` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectTreeNode`
--
-- WHERE:  account_id = 1

LOCK TABLES `ProjectTreeNode` WRITE;
/*!40000 ALTER TABLE `ProjectTreeNode` DISABLE KEYS */;
INSERT INTO `ProjectTreeNode` VALUES (1,0,'Template',1,'','/Template','approachTree','release',1,1,1),(2,1,'Application Test',1,'\0','/Template/Application Test','approachTree','testCycle',1,1,1),(3,1,'Product Test',2,'\0','/Template/Product Test','approachTree','testCycle',1,1,1),(4,1,'User Acceptance Test',3,'\0','/Template/User Acceptance Test','approachTree','testCycle',1,1,1),(5,0,'Roll-out UK',2,'','/Roll-out UK','approachTree','release',1,1,5),(6,0,'Roll-out AT',3,'','/Roll-out AT','approachTree','release',1,1,6),(7,0,'Finance',1,'','/Finance','repositoryTree','scriptFolder',1,1,7),(8,1,'Assets',3,'','/Finance/Assets','repositoryTree','scriptFolder',1,1,7),(9,1,'Customers',2,'','/Finance/Customers','repositoryTree','scriptFolder',1,1,7),(10,1,'General Ledger',4,'','/Finance/General Ledger','repositoryTree','scriptFolder',1,1,7),(11,0,'Logistics',5,'','/Logistics','repositoryTree','scriptFolder',1,1,11),(12,0,'Purchasing',6,'','/Purchasing','repositoryTree','scriptFolder',1,1,12),(13,2,'Create asset master',1,'','/Finance/Assets/Create asset master','repositoryTree','script',1,1,7),(14,0,'Finance',1,'','/Finance','requirementTree','requirementFolder',1,1,14),(15,1,'Finance',1,'','/Finance/AP User access restriction','requirementTree','requirement',1,1,14);
/*!40000 ALTER TABLE `ProjectTreeNode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ProjectWidget`
--

DROP TABLE IF EXISTS `ProjectWidget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectWidget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `category` varchar(255) DEFAULT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `publicWidget` bit(1) NOT NULL,
  `templateWidget` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `wType` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `owner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FKE9734A9D8A330A89` (`project_id`),
  KEY `FKE9734A9DD1149035` (`account_id`),
  KEY `FKE9734A9D3E92729C` (`owner_id`),
  CONSTRAINT `FKE9734A9D3E92729C` FOREIGN KEY (`owner_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FKE9734A9D8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FKE9734A9DD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectWidget`
--
-- WHERE:  account_id = 1

LOCK TABLES `ProjectWidget` WRITE;
/*!40000 ALTER TABLE `ProjectWidget` DISABLE KEYS */;
INSERT INTO `ProjectWidget` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:10',1,'Defects','Oxiras','Displays the total number of defects created each day','','','Number of defects by day','graph',1,1,NULL),(2,'2011-06-14 21:34:10','2011-06-14 21:34:10',2,'Defects','Oxiras','Displays all defects','','','Defects report','report',1,1,NULL),(3,'2011-06-14 21:34:10','2011-06-14 21:34:10',3,'Requirements','Oxiras','Displays all requirements','','','Requirements report','report',1,1,NULL),(4,'2011-06-14 21:34:10','2011-06-14 21:34:10',4,'Instances','Oxiras','Displays all test instances','','','Test instances','report',1,1,NULL),(5,'2011-06-14 21:34:10','2011-06-14 21:34:10',5,'Scripts','Oxiras','Displays all test scripts','','','Test scripts','report',1,1,NULL);
/*!40000 ALTER TABLE `ProjectWidget` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ProjectWidget
BEFORE INSERT ON ProjectWidget FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ProjectWidget
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Requirement`
--

DROP TABLE IF EXISTS `Requirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Requirement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `description` varchar(8000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `createdBy_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FK791284235302D890` (`createdBy_id`),
  KEY `FK791284238A330A89` (`project_id`),
  KEY `FK79128423D1149035` (`account_id`),
  CONSTRAINT `FK79128423D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK791284235302D890` FOREIGN KEY (`createdBy_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FK791284238A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Requirement`
--
-- WHERE:  account_id = 1

LOCK TABLES `Requirement` WRITE;
/*!40000 ALTER TABLE `Requirement` DISABLE KEYS */;
INSERT INTO `Requirement` VALUES (1,'2011-06-14 21:34:12','2011-06-14 21:34:12',1,NULL,'AP user access restriction',1,1,1);
/*!40000 ALTER TABLE `Requirement` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Requirement
BEFORE INSERT ON Requirement FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Requirement
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `RequirementFolder`
--

DROP TABLE IF EXISTS `RequirementFolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RequirementFolder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FK535BA6718A330A89` (`project_id`),
  KEY `FK535BA671D1149035` (`account_id`),
  CONSTRAINT `FK535BA671D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK535BA6718A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RequirementFolder`
--
-- WHERE:  account_id = 1

LOCK TABLES `RequirementFolder` WRITE;
/*!40000 ALTER TABLE `RequirementFolder` DISABLE KEYS */;
INSERT INTO `RequirementFolder` VALUES (1,'2011-06-14 21:34:12','2011-06-14 21:34:12',1,'Finance',1,1);
/*!40000 ALTER TABLE `RequirementFolder` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_RequirementFolder
BEFORE INSERT ON RequirementFolder FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM RequirementFolder
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Run`
--

DROP TABLE IF EXISTS `Run`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Run` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `executionDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `temporary` bit(1) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `instance_id` bigint(20) NOT NULL,
  `tester_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK1426B8A330A89` (`project_id`),
  KEY `FK1426B956D2510` (`tester_id`),
  KEY `FK1426BD1149035` (`account_id`),
  KEY `FK1426BD46E1FE5` (`instance_id`),
  CONSTRAINT `FK1426BD46E1FE5` FOREIGN KEY (`instance_id`) REFERENCES `Instance` (`id`),
  CONSTRAINT `FK1426B8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK1426B956D2510` FOREIGN KEY (`tester_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FK1426BD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Run`
--
-- WHERE:  account_id = 1

LOCK TABLES `Run` WRITE;
/*!40000 ALTER TABLE `Run` DISABLE KEYS */;
/*!40000 ALTER TABLE `Run` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Run
BEFORE INSERT ON Run FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Run
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `RunParam`
--

DROP TABLE IF EXISTS `RunParam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RunParam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `run_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK33A3B0E28A330A89` (`project_id`),
  KEY `FK33A3B0E2B09B6CAF` (`run_id`),
  KEY `FK33A3B0E2D1149035` (`account_id`),
  CONSTRAINT `FK33A3B0E2D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK33A3B0E28A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK33A3B0E2B09B6CAF` FOREIGN KEY (`run_id`) REFERENCES `Run` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RunParam`
--
-- WHERE:  account_id = 1

LOCK TABLES `RunParam` WRITE;
/*!40000 ALTER TABLE `RunParam` DISABLE KEYS */;
/*!40000 ALTER TABLE `RunParam` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_RunParam
BEFORE INSERT ON RunParam FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM RunParam
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `RunStep`
--

DROP TABLE IF EXISTS `RunStep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RunStep` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `actualResult` varchar(2000) DEFAULT NULL,
  `description` varchar(8000) DEFAULT NULL,
  `expectedResult` varchar(2000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `position` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `run_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FKBF9B8FD78A330A89` (`project_id`),
  KEY `FKBF9B8FD7B09B6CAF` (`run_id`),
  KEY `FKBF9B8FD7D1149035` (`account_id`),
  CONSTRAINT `FKBF9B8FD7D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKBF9B8FD78A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FKBF9B8FD7B09B6CAF` FOREIGN KEY (`run_id`) REFERENCES `Run` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RunStep`
--
-- WHERE:  account_id = 1

LOCK TABLES `RunStep` WRITE;
/*!40000 ALTER TABLE `RunStep` DISABLE KEYS */;
/*!40000 ALTER TABLE `RunStep` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_RunStep
BEFORE INSERT ON RunStep FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM RunStep
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `Script`
--

DROP TABLE IF EXISTS `Script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Script` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `description` varchar(8000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `createdBy_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK934ABCEB5302D890` (`createdBy_id`),
  KEY `FK934ABCEB8A330A89` (`project_id`),
  KEY `FK934ABCEBD1149035` (`account_id`),
  CONSTRAINT `FK934ABCEBD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK934ABCEB5302D890` FOREIGN KEY (`createdBy_id`) REFERENCES `TMUser` (`id`),
  CONSTRAINT `FK934ABCEB8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Script`
--
-- WHERE:  account_id = 1

LOCK TABLES `Script` WRITE;
/*!40000 ALTER TABLE `Script` DISABLE KEYS */;
INSERT INTO `Script` VALUES (1,'2011-06-14 21:34:11','2011-06-14 21:34:11',1,NULL,'Create asset master',1,1,1);
/*!40000 ALTER TABLE `Script` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Script
BEFORE INSERT ON Script FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Script
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ScriptFolder`
--

DROP TABLE IF EXISTS `ScriptFolder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScriptFolder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK946829398A330A89` (`project_id`),
  KEY `FK94682939D1149035` (`account_id`),
  CONSTRAINT `FK94682939D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK946829398A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ScriptFolder`
--
-- WHERE:  account_id = 1

LOCK TABLES `ScriptFolder` WRITE;
/*!40000 ALTER TABLE `ScriptFolder` DISABLE KEYS */;
INSERT INTO `ScriptFolder` VALUES (1,'2011-06-14 21:34:11','2011-06-14 21:34:11',1,'Finance',1,1),(2,'2011-06-14 21:34:11','2011-06-14 21:34:11',2,'Customers',1,1),(3,'2011-06-14 21:34:11','2011-06-14 21:34:11',3,'Assets',1,1),(4,'2011-06-14 21:34:11','2011-06-14 21:34:11',4,'General Ledger',1,1),(5,'2011-06-14 21:34:11','2011-06-14 21:34:11',5,'Logistics',1,1),(6,'2011-06-14 21:34:11','2011-06-14 21:34:11',6,'Purchasing',1,1);
/*!40000 ALTER TABLE `ScriptFolder` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ScriptFolder
BEFORE INSERT ON ScriptFolder FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ScriptFolder
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ScriptParam`
--

DROP TABLE IF EXISTS `ScriptParam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScriptParam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `script_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK4FA2C6628A330A89` (`project_id`),
  KEY `FK4FA2C662232B42E5` (`script_id`),
  KEY `FK4FA2C662D1149035` (`account_id`),
  CONSTRAINT `FK4FA2C662D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK4FA2C662232B42E5` FOREIGN KEY (`script_id`) REFERENCES `Script` (`id`),
  CONSTRAINT `FK4FA2C6628A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ScriptParam`
--
-- WHERE:  account_id = 1

LOCK TABLES `ScriptParam` WRITE;
/*!40000 ALTER TABLE `ScriptParam` DISABLE KEYS */;
INSERT INTO `ScriptParam` VALUES (1,'2011-06-14 21:34:11','2011-06-14 21:34:11',1,'companyCode',1,1,1),(2,'2011-06-14 21:34:11','2011-06-14 21:34:11',2,'assetClass',1,1,1),(3,'2011-06-14 21:34:11','2011-06-14 21:34:11',3,'assetDescription',1,1,1),(4,'2011-06-14 21:34:11','2011-06-14 21:34:11',4,'costCenter',1,1,1),(5,'2011-06-14 21:34:11','2011-06-14 21:34:11',5,'usefulLife',1,1,1);
/*!40000 ALTER TABLE `ScriptParam` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ScriptParam
BEFORE INSERT ON ScriptParam FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ScriptParam
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ScriptStep`
--

DROP TABLE IF EXISTS `ScriptStep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScriptStep` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `description` varchar(8000) DEFAULT NULL,
  `expectedResult` varchar(2000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `position` int(11) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `script_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK44A3CA578A330A89` (`project_id`),
  KEY `FK44A3CA57232B42E5` (`script_id`),
  KEY `FK44A3CA57D1149035` (`account_id`),
  CONSTRAINT `FK44A3CA57D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK44A3CA57232B42E5` FOREIGN KEY (`script_id`) REFERENCES `Script` (`id`),
  CONSTRAINT `FK44A3CA578A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ScriptStep`
--
-- WHERE:  account_id = 1

LOCK TABLES `ScriptStep` WRITE;
/*!40000 ALTER TABLE `ScriptStep` DISABLE KEYS */;
INSERT INTO `ScriptStep` VALUES (1,'2011-06-14 21:34:11','2011-06-14 21:34:11',1,'Go to transaction AS01 \r Enter compay code <<<companyCode>>> \r Enter asset class <<<assetClass>>>','','Initial screen',1,1,1,1),(2,'2011-06-14 21:34:11','2011-06-14 21:34:11',2,'Enter asset description <<<assetDescription>>>','','General tab',2,1,1,1),(3,'2011-06-14 21:34:11','2011-06-14 21:34:11',3,'Enter cost center <<<costCenter>>>','','Time dependent tab',3,1,1,1),(4,'2011-06-14 21:34:11','2011-06-14 21:34:11',4,'Enter useful life <<<usefulLife>>> \rSave','Asset <<<assetDescription>>> created in company code <<<companyCode>>>','Depreciation Areas tab',4,1,1,1);
/*!40000 ALTER TABLE `ScriptStep` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_ScriptStep
BEFORE INSERT ON ScriptStep FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM ScriptStep
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `TMUser`
--

DROP TABLE IF EXISTS `TMUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TMUser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dashboardLayout` varchar(255) DEFAULT NULL,
  `sessionExpirationTime` datetime DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `activeProject_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK93BC85E438ABFA0F` (`activeProject_id`),
  KEY `FK93BC85E4BDF9CE1F` (`user_id`),
  KEY `FK93BC85E4D1149035` (`account_id`),
  CONSTRAINT `FK93BC85E4D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK93BC85E438ABFA0F` FOREIGN KEY (`activeProject_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FK93BC85E4BDF9CE1F` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TMUser`
--
-- WHERE:  account_id = 1

LOCK TABLES `TMUser` WRITE;
/*!40000 ALTER TABLE `TMUser` DISABLE KEYS */;
INSERT INTO `TMUser` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:48',NULL,'2011-06-15 00:34:48',1,1,1),(2,'2011-06-14 21:34:10','2011-06-14 21:34:10',NULL,NULL,1,1,2),(3,'2011-06-14 21:34:10','2011-06-14 21:34:10',NULL,NULL,1,1,3);
/*!40000 ALTER TABLE `TMUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Tag`
--

DROP TABLE IF EXISTS `Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK1477A8A330A89` (`project_id`),
  KEY `FK1477AD1149035` (`account_id`),
  CONSTRAINT `FK1477AD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK1477A8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Tag`
--
-- WHERE:  account_id = 1

LOCK TABLES `Tag` WRITE;
/*!40000 ALTER TABLE `Tag` DISABLE KEYS */;
INSERT INTO `Tag` VALUES (1,'2011-06-14 21:34:11','2011-06-14 21:34:11',1,'Functional','REQUIREMENT',1,1),(2,'2011-06-14 21:34:11','2011-06-14 21:34:12',2,'Security & Control','REQUIREMENT',1,1),(3,'2011-06-14 21:34:12','2011-06-14 21:34:12',3,'Interface & Data','REQUIREMENT',1,1),(4,'2011-06-14 21:34:12','2011-06-14 21:34:12',4,'Finance','TESTINSTANCE',1,1),(5,'2011-06-14 21:34:12','2011-06-14 21:34:12',5,'Logistics','TESTINSTANCE',1,1),(6,'2011-06-14 21:34:12','2011-06-14 21:34:12',6,'Purchasing','TESTINSTANCE',1,1);
/*!40000 ALTER TABLE `Tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_Tag
BEFORE INSERT ON Tag FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM Tag
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `TestCycle`
--

DROP TABLE IF EXISTS `TestCycle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TestCycle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `description` varchar(8000) DEFAULT NULL,
  `fromDate` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `toDate` datetime DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`project_id`),
  KEY `FK2F928F548A330A89` (`project_id`),
  KEY `FK2F928F54D1149035` (`account_id`),
  CONSTRAINT `FK2F928F54D1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FK2F928F548A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TestCycle`
--
-- WHERE:  account_id = 1

LOCK TABLES `TestCycle` WRITE;
/*!40000 ALTER TABLE `TestCycle` DISABLE KEYS */;
INSERT INTO `TestCycle` VALUES (1,'2011-06-14 21:34:11','2011-06-14 21:34:11',1,NULL,'2011-03-01 01:00:00','Application test','2011-03-12 01:00:00',1,1),(2,'2011-06-14 21:34:11','2011-06-14 21:34:11',2,NULL,'2011-03-15 01:00:00','Product Test','2011-03-19 01:00:00',1,1),(3,'2011-06-14 21:34:11','2011-06-14 21:34:11',3,NULL,'2011-03-22 01:00:00','User Acceptance test','2011-03-25 01:00:00',1,1);
/*!40000 ALTER TABLE `TestCycle` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_TestCycle
BEFORE INSERT ON TestCycle FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM TestCycle
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `active` bit(1) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `naturalId` (`naturalId`,`account_id`),
  KEY `FK285FEBD1149035` (`account_id`),
  CONSTRAINT `FK285FEBD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--
-- WHERE:  account_id = 1

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'2011-06-14 21:34:10','2011-06-14 21:34:10',1,'','demo@example.com','John','Smith','/gHOKn+6yPr67XyYKgTiKQ==',NULL,1),(2,'2011-06-14 21:34:10','2011-06-14 21:34:10',2,'','hui.zhong.huang@example.com','Hui Zhong','Huáng','/gHOKn+6yPr67XyYKgTiKQ==',NULL,1),(3,'2011-06-14 21:34:10','2011-06-14 21:34:10',3,'','anne.lechauve@example.com','Anne','Le Chauve','/gHOKn+6yPr67XyYKgTiKQ==',NULL,1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_User
BEFORE INSERT ON User FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM User
  WHERE account_id=NEW.account_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `UserWidget`
--

DROP TABLE IF EXISTS `UserWidget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UserWidget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `naturalId` bigint(20) NOT NULL DEFAULT '-1',
  `widgetColumn` varchar(255) DEFAULT NULL,
  `open` bit(1) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `widget_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_id` (`project_id`,`naturalId`),
  KEY `FKDBE9CA0F8A330A89` (`project_id`),
  KEY `FKDBE9CA0FD2ABC284` (`user_id`),
  KEY `FKDBE9CA0FD1149035` (`account_id`),
  KEY `FKDBE9CA0FA81867E2` (`widget_id`),
  CONSTRAINT `FKDBE9CA0FA81867E2` FOREIGN KEY (`widget_id`) REFERENCES `ProjectWidget` (`id`),
  CONSTRAINT `FKDBE9CA0F8A330A89` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`),
  CONSTRAINT `FKDBE9CA0FD1149035` FOREIGN KEY (`account_id`) REFERENCES `Account` (`id`),
  CONSTRAINT `FKDBE9CA0FD2ABC284` FOREIGN KEY (`user_id`) REFERENCES `TMUser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UserWidget`
--
-- WHERE:  account_id = 1

LOCK TABLES `UserWidget` WRITE;
/*!40000 ALTER TABLE `UserWidget` DISABLE KEYS */;
/*!40000 ALTER TABLE `UserWidget` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER naturalIdTrigger_UserWidget
BEFORE INSERT ON UserWidget FOR EACH ROW
BEGIN
  DECLARE row_count BIGINT;
  SELECT CASE WHEN MAX(id) IS NULL THEN 0 ELSE MAX(id) END
  INTO row_count
  FROM UserWidget
  WHERE project_id=NEW.project_id;
  SET NEW.naturalId=row_count + 1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Defect_Tag`
--

DROP TABLE IF EXISTS `Defect_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Defect_Tag` (
  `Defect_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  KEY `FK548DFBCC8B28E570` (`tags_id`),
  KEY `FK548DFBCC6DCD6ACB` (`Defect_id`),
  CONSTRAINT `FK548DFBCC6DCD6ACB` FOREIGN KEY (`Defect_id`) REFERENCES `Defect` (`id`),
  CONSTRAINT `FK548DFBCC8B28E570` FOREIGN KEY (`tags_id`) REFERENCES `Tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Defect_Tag`
--
-- WHERE:  Defect_id in (select id from Defect where account_id = 1)

LOCK TABLES `Defect_Tag` WRITE;
/*!40000 ALTER TABLE `Defect_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Defect_Tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Filter_FilterConstraint`
--

DROP TABLE IF EXISTS `Filter_FilterConstraint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Filter_FilterConstraint` (
  `Filter_id` bigint(20) NOT NULL,
  `filterConstraints_id` bigint(20) NOT NULL,
  KEY `FKB6D95C82840A22` (`filterConstraints_id`),
  KEY `FKB6D95C9099596B` (`Filter_id`),
  CONSTRAINT `FKB6D95C9099596B` FOREIGN KEY (`Filter_id`) REFERENCES `Filter` (`id`),
  CONSTRAINT `FKB6D95C82840A22` FOREIGN KEY (`filterConstraints_id`) REFERENCES `FilterConstraint` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Filter_FilterConstraint`
--
-- WHERE:  Filter_id in (select id from Filter where account_id = 1)

LOCK TABLES `Filter_FilterConstraint` WRITE;
/*!40000 ALTER TABLE `Filter_FilterConstraint` DISABLE KEYS */;
/*!40000 ALTER TABLE `Filter_FilterConstraint` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Instance_Defect`
--

DROP TABLE IF EXISTS `Instance_Defect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Instance_Defect` (
  `Instance_id` bigint(20) NOT NULL,
  `defects_id` bigint(20) NOT NULL,
  KEY `FK1DF5745B9F42657A` (`defects_id`),
  KEY `FK1DF5745BD46E1FE5` (`Instance_id`),
  CONSTRAINT `FK1DF5745BD46E1FE5` FOREIGN KEY (`Instance_id`) REFERENCES `Instance` (`id`),
  CONSTRAINT `FK1DF5745B9F42657A` FOREIGN KEY (`defects_id`) REFERENCES `Defect` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Instance_Defect`
--
-- WHERE:  Instance_id in (select id from Instance where account_id = 1)

LOCK TABLES `Instance_Defect` WRITE;
/*!40000 ALTER TABLE `Instance_Defect` DISABLE KEYS */;
/*!40000 ALTER TABLE `Instance_Defect` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Instance_Tag`
--

DROP TABLE IF EXISTS `Instance_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Instance_Tag` (
  `Instance_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  KEY `FKDDD80CF08B28E570` (`tags_id`),
  KEY `FKDDD80CF0D46E1FE5` (`Instance_id`),
  CONSTRAINT `FKDDD80CF0D46E1FE5` FOREIGN KEY (`Instance_id`) REFERENCES `Instance` (`id`),
  CONSTRAINT `FKDDD80CF08B28E570` FOREIGN KEY (`tags_id`) REFERENCES `Tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Instance_Tag`
--
-- WHERE:  Instance_id in (select id from Instance where account_id = 1)

LOCK TABLES `Instance_Tag` WRITE;
/*!40000 ALTER TABLE `Instance_Tag` DISABLE KEYS */;
INSERT INTO `Instance_Tag` VALUES (1,4),(2,4);
/*!40000 ALTER TABLE `Instance_Tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ProjectRole_unitRoles`
--

DROP TABLE IF EXISTS `ProjectRole_unitRoles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectRole_unitRoles` (
  `ProjectRole_id` bigint(20) NOT NULL,
  `unitRoles` varchar(255) DEFAULT NULL,
  `unitRoles_ORDER` int(11) NOT NULL,
  PRIMARY KEY (`ProjectRole_id`,`unitRoles_ORDER`),
  KEY `FKB8677E69C5432369` (`ProjectRole_id`),
  CONSTRAINT `FKB8677E69C5432369` FOREIGN KEY (`ProjectRole_id`) REFERENCES `ProjectRole` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectRole_unitRoles`
--
-- WHERE:  ProjectRole_id in (select id from ProjectRole where account_id = 1)

LOCK TABLES `ProjectRole_unitRoles` WRITE;
/*!40000 ALTER TABLE `ProjectRole_unitRoles` DISABLE KEYS */;
INSERT INTO `ProjectRole_unitRoles` VALUES (1,'projectEdit',0),(1,'reqView',1),(1,'reqCreate',2),(1,'reqEdit',3),(1,'reqDelete',4),(1,'testRepoView',5),(1,'testRepoCreate',6),(1,'testRepoEdit',7),(1,'testRepoDelete',8),(1,'testPrepView',9),(1,'testPrepCreate',10),(1,'testPrepEdit',11),(1,'testPrepDelete',12),(1,'testExecView',13),(1,'testExecCreate',14),(1,'testExecEdit',15),(1,'testExecDelete',16),(1,'defectView',17),(1,'defectCreate',18),(1,'defectEdit',19),(1,'defectDelete',20);
/*!40000 ALTER TABLE `ProjectRole_unitRoles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ProjectWidget_parameters`
--

DROP TABLE IF EXISTS `ProjectWidget_parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ProjectWidget_parameters` (
  `ProjectWidget_id` bigint(20) NOT NULL,
  `parameters` varchar(255) DEFAULT NULL,
  `parameters_KEY` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`ProjectWidget_id`,`parameters_KEY`),
  KEY `FK2EA2F8CC5BD888E9` (`ProjectWidget_id`),
  CONSTRAINT `FK2EA2F8CC5BD888E9` FOREIGN KEY (`ProjectWidget_id`) REFERENCES `ProjectWidget` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ProjectWidget_parameters`
--
-- WHERE:  ProjectWidget_id in (select id from ProjectWidget where account_id = 1)

LOCK TABLES `ProjectWidget_parameters` WRITE;
/*!40000 ALTER TABLE `ProjectWidget_parameters` DISABLE KEYS */;
INSERT INTO `ProjectWidget_parameters` VALUES (1,'Defect','entity'),(1,'Defects over time','graphLabel'),(1,'temporal','graphType'),(1,'created','temporalField'),(1,'DATE','xAxis'),(1,'COUNT','yAxis'),(2,'Defect','entity'),(2,'All defects','title'),(3,'Requirement','entity'),(3,'All requirements','title'),(4,'Instance','entity'),(4,'All instances','title'),(5,'Script','entity'),(5,'All scripts','title');
/*!40000 ALTER TABLE `ProjectWidget_parameters` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Requirement_Script`
--

DROP TABLE IF EXISTS `Requirement_Script`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Requirement_Script` (
  `Requirement_id` bigint(20) NOT NULL,
  `linkedScripts_id` bigint(20) NOT NULL,
  KEY `FK1254D88780C732C9` (`Requirement_id`),
  KEY `FK1254D887D72A2061` (`linkedScripts_id`),
  CONSTRAINT `FK1254D887D72A2061` FOREIGN KEY (`linkedScripts_id`) REFERENCES `Script` (`id`),
  CONSTRAINT `FK1254D88780C732C9` FOREIGN KEY (`Requirement_id`) REFERENCES `Requirement` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Requirement_Script`
--
-- WHERE:  Requirement_id in (select id from Requirement where account_id = 1)

LOCK TABLES `Requirement_Script` WRITE;
/*!40000 ALTER TABLE `Requirement_Script` DISABLE KEYS */;
/*!40000 ALTER TABLE `Requirement_Script` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Requirement_Tag`
--

DROP TABLE IF EXISTS `Requirement_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Requirement_Tag` (
  `Requirement_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  KEY `FKE64A315E8B28E570` (`tags_id`),
  KEY `FKE64A315E80C732C9` (`Requirement_id`),
  CONSTRAINT `FKE64A315E80C732C9` FOREIGN KEY (`Requirement_id`) REFERENCES `Requirement` (`id`),
  CONSTRAINT `FKE64A315E8B28E570` FOREIGN KEY (`tags_id`) REFERENCES `Tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Requirement_Tag`
--
-- WHERE:  Requirement_id in (select id from Requirement where account_id = 1)

LOCK TABLES `Requirement_Tag` WRITE;
/*!40000 ALTER TABLE `Requirement_Tag` DISABLE KEYS */;
INSERT INTO `Requirement_Tag` VALUES (1,2);
/*!40000 ALTER TABLE `Requirement_Tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Script_Tag`
--

DROP TABLE IF EXISTS `Script_Tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Script_Tag` (
  `Script_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  KEY `FK44A8C6268B28E570` (`tags_id`),
  KEY `FK44A8C626232B42E5` (`Script_id`),
  CONSTRAINT `FK44A8C626232B42E5` FOREIGN KEY (`Script_id`) REFERENCES `Script` (`id`),
  CONSTRAINT `FK44A8C6268B28E570` FOREIGN KEY (`tags_id`) REFERENCES `Tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Script_Tag`
--
-- WHERE:  Script_id in (select id from Script where account_id = 1)

LOCK TABLES `Script_Tag` WRITE;
/*!40000 ALTER TABLE `Script_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `Script_Tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `TMUser_ProjectRole`
--

DROP TABLE IF EXISTS `TMUser_ProjectRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TMUser_ProjectRole` (
  `TMUser_id` bigint(20) NOT NULL,
  `projectRoles_id` bigint(20) NOT NULL,
  KEY `FK5F1EE1343C8E29EB` (`TMUser_id`),
  KEY `FK5F1EE1347A8D0714` (`projectRoles_id`),
  CONSTRAINT `FK5F1EE1347A8D0714` FOREIGN KEY (`projectRoles_id`) REFERENCES `ProjectRole` (`id`),
  CONSTRAINT `FK5F1EE1343C8E29EB` FOREIGN KEY (`TMUser_id`) REFERENCES `TMUser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TMUser_ProjectRole`
--
-- WHERE:  TMUser_id in (select id from TMUser where account_id = 1)

LOCK TABLES `TMUser_ProjectRole` WRITE;
/*!40000 ALTER TABLE `TMUser_ProjectRole` DISABLE KEYS */;
INSERT INTO `TMUser_ProjectRole` VALUES (1,1),(2,1),(3,1);
/*!40000 ALTER TABLE `TMUser_ProjectRole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
-- MySQL dump 10.13  Distrib 5.1.54, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: tm
-- ------------------------------------------------------
-- Server version	5.1.54-1ubuntu4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `TMUser_accountRoles`
--

DROP TABLE IF EXISTS `TMUser_accountRoles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TMUser_accountRoles` (
  `TMUser_id` bigint(20) NOT NULL,
  `accountRoles` varchar(255) DEFAULT NULL,
  `accountRoles_ORDER` int(11) NOT NULL,
  PRIMARY KEY (`TMUser_id`,`accountRoles_ORDER`),
  KEY `FK83FEFF0B3C8E29EB` (`TMUser_id`),
  CONSTRAINT `FK83FEFF0B3C8E29EB` FOREIGN KEY (`TMUser_id`) REFERENCES `TMUser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TMUser_accountRoles`
--
-- WHERE:  TMUser_id in (select id from TMUser where account_id = 1)

LOCK TABLES `TMUser_accountRoles` WRITE;
/*!40000 ALTER TABLE `TMUser_accountRoles` DISABLE KEYS */;
INSERT INTO `TMUser_accountRoles` VALUES (1,'accountAdmin',0),(1,'userCreate',1),(1,'userEdit',2),(1,'userDelete',3),(1,'projectCreate',4),(1,'projectEdit',5),(1,'projectDelete',6);
/*!40000 ALTER TABLE `TMUser_accountRoles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-06-14 23:43:04
