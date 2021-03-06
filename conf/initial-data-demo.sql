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
-- Dumping data for table `general_Product`
--

/*!40000 ALTER TABLE `general_Product` DISABLE KEYS */;
/*!40000 ALTER TABLE `general_Product` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `account_Account`
--
-- WHERE:  id = 1

/*!40000 ALTER TABLE `account_Account` DISABLE KEYS */;
INSERT INTO `account_Account` VALUES (1,'2011-06-15 20:10:54','2011-06-15 20:10:54',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'demo@example.com','ACME',NULL,'demo',NULL);
/*!40000 ALTER TABLE `account_Account` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `account_AccountProduct`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `account_AccountProduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_AccountProduct` ENABLE KEYS */;

--
-- Dumping data for table `account_AccountProductPeriod`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `account_AccountProductPeriod` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_AccountProductPeriod` ENABLE KEYS */;

--
-- Dumping data for table `account_PurchaseOrder`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `account_PurchaseOrder` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_PurchaseOrder` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_approach_Release`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_approach_Release` DISABLE KEYS */;
INSERT INTO `tm_approach_Release` VALUES (1,'2011-06-15 20:10:56','2011-06-15 20:10:56',1,'Template',1,1),(2,'2011-06-15 20:10:56','2011-06-15 20:10:56',2,'Roll-out UK',1,1),(3,'2011-06-15 20:10:56','2011-06-15 20:10:56',3,'Roll-out AT',1,1),(4,'2011-08-01 20:58:17','2011-08-01 20:58:17',1,'Release 1',1,2),(5,'2011-08-01 20:58:21','2011-08-01 20:58:21',2,'Release 2',1,2);
/*!40000 ALTER TABLE `tm_approach_Release` ENABLE KEYS */;

--
-- Dumping data for table `tm_approach_TestCycle`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_approach_TestCycle` DISABLE KEYS */;
INSERT INTO `tm_approach_TestCycle` VALUES (1,'2011-06-15 20:10:56','2011-06-15 20:10:56',1,NULL,'2011-03-01 01:00:00','Application test','2011-03-12 01:00:00',1,1),(2,'2011-06-15 20:10:56','2011-06-15 20:10:56',2,NULL,'2011-03-15 01:00:00','Product Test','2011-03-19 01:00:00',1,1),(3,'2011-06-15 20:10:56','2011-06-15 20:10:56',3,NULL,'2011-03-22 01:00:00','User Acceptance test','2011-03-25 01:00:00',1,1),(4,'2011-08-01 20:58:29','2011-08-01 20:59:49',1,'A long first cycle','2011-08-01 00:00:00','Cycle 1','2011-09-01 00:00:00',1,2),(5,'2011-08-01 20:58:34','2011-08-01 20:59:14',2,'A short second cycle','2011-08-25 00:00:00','Cycle 2','2011-07-23 00:00:00',1,2);
/*!40000 ALTER TABLE `tm_approach_TestCycle` ENABLE KEYS */;

--
-- Dumping data for table `tm_Defect`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_Defect` DISABLE KEYS */;
INSERT INTO `tm_Defect` VALUES (1,'2011-06-02 00:00:00','2011-08-01 20:57:03',1,'','Missing customizing',1,1,3,2,2),(2,'2011-07-28 19:29:50','2011-07-28 19:29:50',2,'Test instance ran: Create moveable asset\n\nExpected result: Asset <<<assetDescription>>> created in company code <<<companyCode>>>\nActual result: Asset not created: short dump','Asset not created',1,1,34,1,1);
/*!40000 ALTER TABLE `tm_Defect` ENABLE KEYS */;

--
-- Dumping data for table `tm_DefectStatus`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_DefectStatus` DISABLE KEYS */;
INSERT INTO `tm_DefectStatus` VALUES (1,'2011-06-15 20:10:55','2011-06-15 20:10:55',1,'','New',0,1,1),(2,'2011-06-15 20:10:56','2011-06-15 20:10:56',2,'\0','Open',1,1,1),(3,'2011-06-15 20:10:57','2011-06-15 20:10:57',3,'\0','Closed',1,1,1),(4,'2011-08-01 20:57:28','2011-08-01 20:57:28',1,'','New',0,1,2),(5,'2011-08-01 20:57:28','2011-08-01 21:04:45',2,'\0','Open',1,1,2),(6,'2011-08-01 20:57:28','2011-08-01 21:04:49',3,'\0','Closed',2,1,2);
/*!40000 ALTER TABLE `tm_DefectStatus` ENABLE KEYS */;

--
-- Dumping data for table `tm_Filter`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_Filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tm_Filter` ENABLE KEYS */;

--
-- Dumping data for table `tm_FilterConstraint`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_FilterConstraint` DISABLE KEYS */;
/*!40000 ALTER TABLE `tm_FilterConstraint` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_Instance`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_Instance` DISABLE KEYS */;
INSERT INTO `tm_test_Instance` VALUES (1,'2011-06-15 20:10:57','2011-07-28 19:29:18',1,'Create moveable asset','2011-06-18 00:00:00',3,1,1,33,1,2),(2,'2011-07-28 19:27:24','2011-07-29 09:38:36',2,'Create fixed asset',NULL,NULL,1,1,3,1,2);
/*!40000 ALTER TABLE `tm_test_Instance` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_InstanceParam`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_InstanceParam` DISABLE KEYS */;
INSERT INTO `tm_test_InstanceParam` VALUES (1,'2011-06-15 20:10:57','2011-07-28 19:26:59',1,'2000',1,1,1,1),(2,'2011-06-15 20:10:57','2011-07-28 19:26:59',2,' ',1,1,1,2),(3,'2011-06-15 20:10:57','2011-07-28 19:26:59',3,'',1,1,1,3),(4,'2011-06-15 20:10:58','2011-07-28 19:26:59',4,'34005020',1,1,1,4),(5,'2011-06-15 20:10:58','2011-06-15 20:10:58',5,'5',1,1,1,5),(6,'2011-07-28 19:27:24','2011-07-28 19:28:13',6,'2000',1,1,2,1),(7,'2011-07-28 19:27:25','2011-07-28 19:28:13',7,'Land',1,1,2,2),(8,'2011-07-28 19:27:25','2011-07-28 19:28:13',8,'',1,1,2,3),(9,'2011-07-28 19:27:25','2011-07-28 19:28:13',9,'34005020',1,1,2,4),(10,'2011-07-28 19:27:25','2011-07-28 19:28:13',10,'',1,1,2,5);
/*!40000 ALTER TABLE `tm_test_InstanceParam` ENABLE KEYS */;

--
-- Dumping data for table `tm_Project`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_Project` DISABLE KEYS */;
INSERT INTO `tm_Project` VALUES (1,'2011-06-15 20:10:54','2011-06-15 20:10:55',1,NULL,NULL,'SAP Implementation',NULL,1,NULL,1),(2,'2011-08-01 20:57:28','2011-08-01 20:57:58',2,'',NULL,'TIA Custom Development',NULL,1,NULL,2);
/*!40000 ALTER TABLE `tm_Project` ENABLE KEYS */;

--
-- Dumping data for table `tm_ProjectCategory`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_ProjectCategory` DISABLE KEYS */;
INSERT INTO `tm_ProjectCategory` VALUES (1,'2011-06-15 20:10:54','2011-06-15 20:10:54',1,'ERP',1),(2,'2011-08-01 20:57:21','2011-08-01 20:57:21',2,'Custom',1);
/*!40000 ALTER TABLE `tm_ProjectCategory` ENABLE KEYS */;

--
-- Dumping data for table `tm_ProjectRole`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_ProjectRole` DISABLE KEYS */;
INSERT INTO `tm_ProjectRole` VALUES (1,'2011-06-15 20:10:55','2011-06-15 20:10:55',1,'Test Lead',1,1),(30,'2011-06-16 15:09:37','2011-06-16 15:45:39',2,'Test Preparation and Execution',1,1),(31,'2011-06-16 15:10:17','2011-06-16 15:10:17',3,'Test Execution',1,1),(32,'2011-06-16 15:10:25','2011-06-16 15:10:25',4,'Defect Resolution',1,1);
/*!40000 ALTER TABLE `tm_ProjectRole` ENABLE KEYS */;

--
-- Dumping data for table `tm_ProjectTreeNode`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_ProjectTreeNode` DISABLE KEYS */;
INSERT INTO `tm_ProjectTreeNode` VALUES (1,0,'Template',1,'','/Template','approachTree','release',1,1,1),(2,1,'Application Test',1,'\0','/Template/Application Test','approachTree','testCycle',1,1,1),(3,1,'Product Test',2,'\0','/Template/Product Test','approachTree','testCycle',1,1,1),(4,1,'User Acceptance Test',3,'\0','/Template/User Acceptance Test','approachTree','testCycle',1,1,1),(5,0,'Roll-out UK',2,'','/Roll-out UK','approachTree','release',1,1,5),(6,0,'Roll-out AT',3,'','/Roll-out AT','approachTree','release',1,1,6),(7,0,'Finance',1,'','/Finance','repositoryTree','scriptFolder',1,1,7),(8,1,'Assets',3,'','/Finance/Assets','repositoryTree','scriptFolder',1,1,7),(9,1,'Customers',2,'','/Finance/Customers','repositoryTree','scriptFolder',1,1,7),(10,1,'General Ledger',4,'','/Finance/General Ledger','repositoryTree','scriptFolder',1,1,7),(11,0,'Logistics',5,'','/Logistics','repositoryTree','scriptFolder',1,1,11),(12,0,'Purchasing',6,'','/Purchasing','repositoryTree','scriptFolder',1,1,12),(13,2,'Create asset master',1,'','/Finance/Assets/Create asset master','repositoryTree','script',1,1,7),(14,0,'Finance',1,'','/Finance','requirementTree','requirementFolder',1,1,14),(15,2,'AP user access restriction',1,'','/Finance/Accounts Payable/AP user access restriction','requirementTree','requirement',1,1,14),(35,0,'Logistics',4,'','/Logistics','requirementTree','requirementFolder',1,1,35),(36,0,'Purchasing',5,'','/Purchasing','requirementTree','requirementFolder',1,1,36),(37,1,'Accounts Payable',6,'','/Finance/Accounts Payable','requirementTree','requirementFolder',1,1,14),(38,2,'Goods invoices 3-way matched (PO and GR)',7,'','/Finance/Accounts Payable/Goods invoices 3-way matched (PO and GR)','requirementTree','requirement',1,1,14),(39,2,'Services invoices 2-way matched (PO)',8,'','/Finance/Accounts Payable/Services invoices 2-way matched (PO)','requirementTree','requirement',1,1,14),(41,1,'Accounts Receivable',7,'','/Finance/Accounts Receivable','requirementTree','requirementFolder',1,1,14),(42,2,'Follow up of overdue balances',10,'','/Finance/Accounts Receivable/Follow up of overdue balances','requirementTree','requirement',1,1,14),(43,2,'Due date on customer invoices',11,'','/Finance/Accounts Receivable/Due date on customer invoices','requirementTree','requirement',1,1,14),(44,2,'Creation of credit notes',12,'','/Finance/Accounts Receivable/Creation of credit notes','requirementTree','requirement',1,1,14),(45,1,'Assets',8,'','/Finance/Assets','requirementTree','requirementFolder',1,1,14),(46,2,'Asset Master maintenance',13,'','/Finance/Assets/Asset Master maintenance','requirementTree','requirement',1,1,14),(47,2,'Edit asset master',2,'','/Finance/Assets/Edit asset master','repositoryTree','script',1,1,7),(49,0,'Release 1',4,'','/Release 1','approachTree','release',1,2,49),(50,0,'Release 2',5,'','/Release 2','approachTree','release',1,2,50),(51,1,'Cycle 1',4,'','/Release 1/Cycle 1','approachTree','testCycle',1,2,49),(52,1,'Cycle 2',5,'','/Release 1/Cycle 2','approachTree','testCycle',1,2,49);
/*!40000 ALTER TABLE `tm_ProjectTreeNode` ENABLE KEYS */;

--
-- Dumping data for table `tm_ProjectWidget`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_ProjectWidget` DISABLE KEYS */;
INSERT INTO `tm_ProjectWidget` VALUES (1,'2011-06-15 20:10:55','2011-06-15 20:10:55',1,'Defects','Oxiras','Displays the total number of defects created each day','','','Number of defects by day','graph',1,1,NULL),(2,'2011-06-15 20:10:55','2011-06-15 20:10:55',2,'Defects','Oxiras','Displays all defects','','','Defects report','report',1,1,NULL),(3,'2011-06-15 20:10:55','2011-06-15 20:10:55',3,'Requirements','Oxiras','Displays all requirements','','','Requirements report','report',1,1,NULL),(4,'2011-06-15 20:10:55','2011-06-15 20:10:55',4,'Instances','Oxiras','Displays all test instances','','','Test instances','report',1,1,NULL),(5,'2011-06-15 20:10:55','2011-06-15 20:10:55',5,'Scripts','Oxiras','Displays all test scripts','','','Test scripts','report',1,1,NULL),(6,'2011-08-01 20:57:28','2011-08-01 20:57:29',1,'Defects','Oxiras','Displays the total number of defects created each day','','','Number of defects by day','graph',1,2,NULL),(7,'2011-08-01 20:57:29','2011-08-01 20:57:29',2,'Defects','Oxiras','Displays all defects','','','Defects report','report',1,2,NULL),(8,'2011-08-01 20:57:29','2011-08-01 20:57:29',3,'Requirements','Oxiras','Displays all requirements','','','Requirements report','report',1,2,NULL),(9,'2011-08-01 20:57:29','2011-08-01 20:57:29',4,'Instances','Oxiras','Displays all test instances','','','Test instances','report',1,2,NULL),(10,'2011-08-01 20:57:29','2011-08-01 20:57:29',5,'Scripts','Oxiras','Displays all test scripts','','','Test scripts','report',1,2,NULL);
/*!40000 ALTER TABLE `tm_ProjectWidget` ENABLE KEYS */;

--
-- Dumping data for table `tm_Requirement`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_Requirement` DISABLE KEYS */;
INSERT INTO `tm_Requirement` VALUES (1,'2011-06-15 20:10:57','2011-06-16 16:10:40',1,'','AP user access restriction',1,1,1),(7,'2011-06-16 17:02:59','2011-06-16 17:21:53',2,'Goods invoices are to be 3-way matched (PO and GR)','Goods invoices 3-way matched (PO and GR)',1,1,1),(8,'2011-06-16 17:05:03','2011-06-16 17:05:51',3,'','Services invoices 2-way matched (PO)',1,1,1),(10,'2011-06-16 17:16:54','2011-06-16 17:20:23',4,'','Follow up of overdue balances',1,1,1),(11,'2011-06-16 17:19:19','2011-06-16 17:20:04',5,'Customer invoices should carry a due date, for dunning purposes','Due date on customer invoices',1,1,1),(12,'2011-06-16 17:20:54','2011-06-16 17:21:01',6,'','Creation of credit notes',1,1,1),(13,'2011-07-27 21:27:19','2011-07-27 21:46:59',7,'A master record for AUC (Asset Under Construction) should include:\nCompany, Location, Project number, Project description.','Asset Master maintenance',1,1,1);
/*!40000 ALTER TABLE `tm_Requirement` ENABLE KEYS */;

--
-- Dumping data for table `tm_RequirementFolder`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_RequirementFolder` DISABLE KEYS */;
INSERT INTO `tm_RequirementFolder` VALUES (1,'2011-06-15 20:10:57','2011-06-15 20:10:57',1,'Finance',1,1),(4,'2011-06-16 16:49:42','2011-06-16 16:49:42',2,'Logistics',1,1),(5,'2011-06-16 16:49:47','2011-06-16 16:49:47',3,'Purchasing',1,1),(6,'2011-06-16 17:02:17','2011-06-16 17:02:17',4,'Accounts Payable',1,1),(7,'2011-06-16 17:15:26','2011-06-16 17:15:26',5,'Accounts Receivable',1,1),(8,'2011-07-27 21:24:52','2011-07-27 21:24:52',6,'Assets',1,1);
/*!40000 ALTER TABLE `tm_RequirementFolder` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_Run`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_Run` DISABLE KEYS */;
INSERT INTO `tm_test_Run` VALUES (1,'2011-07-28 19:28:29','2011-07-28 19:29:18',1,'2011-07-28 21:28:29',3,'\0',1,1,1,1);
/*!40000 ALTER TABLE `tm_test_Run` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_RunParam`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_RunParam` DISABLE KEYS */;
INSERT INTO `tm_test_RunParam` VALUES (1,'2011-07-28 19:28:29','2011-07-28 19:28:29',1,'companyCode','2000',1,1,1),(2,'2011-07-28 19:28:29','2011-07-28 19:28:29',2,'assetClass',' ',1,1,1),(3,'2011-07-28 19:28:29','2011-07-28 19:28:29',3,'assetDescription','',1,1,1),(4,'2011-07-28 19:28:29','2011-07-28 19:28:29',4,'costCenter','34005020',1,1,1),(5,'2011-07-28 19:28:29','2011-07-28 19:28:29',5,'usefulLife','5',1,1,1);
/*!40000 ALTER TABLE `tm_test_RunParam` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_RunStep`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_RunStep` DISABLE KEYS */;
INSERT INTO `tm_test_RunStep` VALUES (1,'2011-07-28 19:28:29','2011-07-28 19:29:18',1,'','Go to transaction AS01 \r Enter compay code <<<companyCode>>> \r Enter asset class <<<assetClass>>>','','Initial screen',1,2,1,1,1),(2,'2011-07-28 19:28:29','2011-07-28 19:29:18',2,'','Enter asset description <<<assetDescription>>>','','General tab',2,2,1,1,1),(3,'2011-07-28 19:28:29','2011-07-28 19:29:18',3,'','Enter cost center <<<costCenter>>>','','Time dependent tab',3,2,1,1,1),(4,'2011-07-28 19:28:29','2011-07-28 19:29:18',4,'Asset not created: short dump','Enter useful life <<<usefulLife>>> \rSave','Asset <<<assetDescription>>> created in company code <<<companyCode>>>','Depreciation Areas tab',4,3,1,1,1);
/*!40000 ALTER TABLE `tm_test_RunStep` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_Script`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_Script` DISABLE KEYS */;
INSERT INTO `tm_test_Script` VALUES (1,'2011-06-15 20:10:56','2011-06-15 20:10:56',1,NULL,'Create asset master',1,1,1),(2,'2011-07-28 19:26:23','2011-07-28 19:26:23',2,NULL,'Edit asset master',1,1,1);
/*!40000 ALTER TABLE `tm_test_Script` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_ScriptFolder`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_ScriptFolder` DISABLE KEYS */;
INSERT INTO `tm_test_ScriptFolder` VALUES (1,'2011-06-15 20:10:56','2011-06-15 20:10:56',1,'Finance',1,1),(2,'2011-06-15 20:10:56','2011-06-15 20:10:56',2,'Customers',1,1),(3,'2011-06-15 20:10:56','2011-06-15 20:10:56',3,'Assets',1,1),(4,'2011-06-15 20:10:56','2011-06-15 20:10:56',4,'General Ledger',1,1),(5,'2011-06-15 20:10:56','2011-06-15 20:10:56',5,'Logistics',1,1),(6,'2011-06-15 20:10:56','2011-06-15 20:10:56',6,'Purchasing',1,1);
/*!40000 ALTER TABLE `tm_test_ScriptFolder` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_ScriptParam`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_ScriptParam` DISABLE KEYS */;
INSERT INTO `tm_test_ScriptParam` VALUES (1,'2011-06-15 20:10:56','2011-06-15 20:10:56',1,'companyCode',1,1,1),(2,'2011-06-15 20:10:56','2011-06-15 20:10:56',2,'assetClass',1,1,1),(3,'2011-06-15 20:10:56','2011-06-15 20:10:56',3,'assetDescription',1,1,1),(4,'2011-06-15 20:10:56','2011-06-15 20:10:56',4,'costCenter',1,1,1),(5,'2011-06-15 20:10:56','2011-06-15 20:10:56',5,'usefulLife',1,1,1);
/*!40000 ALTER TABLE `tm_test_ScriptParam` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_ScriptStep`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_ScriptStep` DISABLE KEYS */;
INSERT INTO `tm_test_ScriptStep` VALUES (1,'2011-06-15 20:10:56','2011-06-15 20:10:56',1,'Go to transaction AS01 \r Enter compay code <<<companyCode>>> \r Enter asset class <<<assetClass>>>','','Initial screen',1,1,1,1),(2,'2011-06-15 20:10:56','2011-06-15 20:10:56',2,'Enter asset description <<<assetDescription>>>','','General tab',2,1,1,1),(3,'2011-06-15 20:10:56','2011-06-15 20:10:56',3,'Enter cost center <<<costCenter>>>','','Time dependent tab',3,1,1,1),(4,'2011-06-15 20:10:56','2011-06-15 20:10:56',4,'Enter useful life <<<usefulLife>>> \rSave','Asset <<<assetDescription>>> created in company code <<<companyCode>>>','Depreciation Areas tab',4,1,1,1);
/*!40000 ALTER TABLE `tm_test_ScriptStep` ENABLE KEYS */;

--
-- Dumping data for table `tm_User`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_User` DISABLE KEYS */;
INSERT INTO `tm_User` VALUES (1,'2011-06-15 20:10:55','2011-08-01 14:31:57','layout2','2011-08-01 17:31:57',1,1,1),(2,'2011-06-15 20:10:55','2011-06-15 20:10:55',NULL,NULL,1,1,2),(3,'2011-06-15 20:10:56','2011-06-15 20:10:56',NULL,NULL,1,1,3),(33,'2011-06-16 15:14:11','2011-06-16 16:02:35',NULL,NULL,1,1,33),(34,'2011-06-16 15:16:47','2011-06-16 16:02:43',NULL,NULL,1,1,34),(35,'2011-08-01 14:13:04','2011-08-01 14:13:05',NULL,NULL,1,NULL,35),(36,'2011-08-01 14:28:18','2011-08-01 14:28:18',NULL,NULL,1,NULL,36),(37,'2011-08-01 14:28:34','2011-08-01 14:28:34',NULL,NULL,1,NULL,37),(38,'2011-08-01 14:28:57','2011-08-01 14:28:57',NULL,NULL,1,NULL,38),(39,'2011-08-01 18:01:00','2011-08-01 18:01:00',NULL,NULL,1,NULL,39),(40,'2011-08-01 18:14:17','2011-08-01 18:14:17',NULL,NULL,1,NULL,40);
/*!40000 ALTER TABLE `tm_User` ENABLE KEYS */;

--
-- Dumping data for table `tm_test_Tag`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_test_Tag` DISABLE KEYS */;
INSERT INTO `tm_test_Tag` VALUES (1,'2011-06-15 20:10:57','2011-06-15 20:10:57',1,'Functional','REQUIREMENT',1,1),(2,'2011-06-15 20:10:57','2011-06-15 20:10:57',2,'Security & Control','REQUIREMENT',1,1),(3,'2011-06-15 20:10:57','2011-06-15 20:10:57',3,'Interface & Data','REQUIREMENT',1,1),(4,'2011-06-15 20:10:57','2011-06-15 20:10:57',4,'Finance','TESTINSTANCE',1,1),(5,'2011-06-15 20:10:57','2011-06-15 20:10:57',5,'Logistics','TESTINSTANCE',1,1),(6,'2011-06-15 20:10:57','2011-06-15 20:10:57',6,'Purchasing','TESTINSTANCE',1,1);
/*!40000 ALTER TABLE `tm_test_Tag` ENABLE KEYS */;

--
-- Dumping data for table `account_User`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `account_User` DISABLE KEYS */;
INSERT INTO `account_User` VALUES (1,'2011-06-15 20:10:54','2011-06-15 20:10:54',1,'','demo@example.com','John','Smith','/gHOKn+6yPr67XyYKgTiKQ==',NULL,1),(2,'2011-06-15 20:10:54','2011-07-29 19:11:24',2,'','hui.zhong.huang@example.com','Hui Zhong','?','/gHOKn+6yPr67XyYKgTiKQ==','',1),(3,'2011-06-15 20:10:54','2011-06-15 20:10:54',3,'','anne.lechauve@example.com','Anne','Le Chauve','/gHOKn+6yPr67XyYKgTiKQ==',NULL,1),(33,'2011-06-16 15:14:11','2011-07-28 19:27:19',4,'','soren@example.com','Søren','Jensen','j4dT9l/2qYxWYc8ABvRGIA==','',1),(34,'2011-06-16 15:16:47','2011-06-16 15:16:47',5,'','sanjivani@example.com','Sanjivani','Sharma','NBuJzJXsWg2AdwYQS1TdZw==',NULL,1),(35,'2011-08-01 14:13:04','2011-08-01 14:44:15',6,'\0','a@b.c','A','B','CY9rzUYh03PK3k6DJie09g==',NULL,1),(36,'2011-08-01 14:28:18','2011-08-01 14:44:24',7,'\0','A@b.c','T','T','CY9rzUYh03PK3k6DJie09g==',NULL,1),(37,'2011-08-01 14:28:34','2011-08-01 14:44:20',8,'\0','ccccc@aaaaaa.com','AAAAA','BBBBB','CY9rzUYh03PK3k6DJie09g==',NULL,1),(38,'2011-08-01 14:28:57','2011-08-01 14:44:11',9,'\0','demo2@example.com','AAAAAAAA','BBBBBBBBB','CY9rzUYh03PK3k6DJie09g==',NULL,1),(39,'2011-08-01 18:01:00','2011-08-01 18:01:08',10,'\0','a@b.c','A','B','CY9rzUYh03PK3k6DJie09g==',NULL,1),(40,'2011-08-01 18:14:17','2011-08-01 18:14:22',11,'\0','t@t.t','test','test','CY9rzUYh03PK3k6DJie09g==',NULL,1);
/*!40000 ALTER TABLE `account_User` ENABLE KEYS */;

--
-- Dumping data for table `tm_UserWidget`
--
-- WHERE:  account_id = 1

/*!40000 ALTER TABLE `tm_UserWidget` DISABLE KEYS */;
INSERT INTO `tm_UserWidget` VALUES (1,'2011-07-26 21:59:20','2011-07-27 21:24:01',1,'second','',1,1,1,1),(3,'2011-07-27 21:24:27','2011-07-27 21:24:27',2,'first','',1,1,1,4),(4,'2011-08-01 20:56:48','2011-08-01 20:56:48',3,'first','',1,1,1,2);
/*!40000 ALTER TABLE `tm_UserWidget` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_Defect_tm_test_Tag`
--
-- WHERE:  tm_Defect_id in (select id from tm_Defect where account_id = 1)

/*!40000 ALTER TABLE `tm_Defect_tm_test_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tm_Defect_tm_test_Tag` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_Filter_tm_FilterConstraint`
--
-- WHERE:  tm_Filter_id in (select id from tm_Filter where account_id = 1)

/*!40000 ALTER TABLE `tm_Filter_tm_FilterConstraint` DISABLE KEYS */;
/*!40000 ALTER TABLE `tm_Filter_tm_FilterConstraint` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_test_Instance_tm_Defect`
--
-- WHERE:  tm_test_Instance_id in (select id from tm_test_Instance where account_id = 1)

/*!40000 ALTER TABLE `tm_test_Instance_tm_Defect` DISABLE KEYS */;
INSERT INTO `tm_test_Instance_tm_Defect` VALUES (1,2);
/*!40000 ALTER TABLE `tm_test_Instance_tm_Defect` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_test_Instance_tm_test_Tag`
--
-- WHERE:  tm_test_Instance_id in (select id from tm_test_Instance where account_id = 1)

/*!40000 ALTER TABLE `tm_test_Instance_tm_test_Tag` DISABLE KEYS */;
INSERT INTO `tm_test_Instance_tm_test_Tag` VALUES (1,4);
/*!40000 ALTER TABLE `tm_test_Instance_tm_test_Tag` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_ProjectRole_unitRoles`
--
-- WHERE:  tm_ProjectRole_id in (select id from tm_ProjectRole where account_id = 1)

/*!40000 ALTER TABLE `tm_ProjectRole_unitRoles` DISABLE KEYS */;
INSERT INTO `tm_ProjectRole_unitRoles` VALUES (1,'projectEdit',0),(1,'reqView',1),(1,'reqCreate',2),(1,'reqEdit',3),(1,'reqDelete',4),(1,'testRepoView',5),(1,'testRepoCreate',6),(1,'testRepoEdit',7),(1,'testRepoDelete',8),(1,'testPrepView',9),(1,'testPrepCreate',10),(1,'testPrepEdit',11),(1,'testPrepDelete',12),(1,'testExecView',13),(1,'testExecCreate',14),(1,'testExecEdit',15),(1,'testExecDelete',16),(1,'defectView',17),(1,'defectCreate',18),(1,'defectEdit',19),(1,'defectDelete',20),(30,'reqView',0),(30,'reqCreate',1),(30,'reqEdit',2),(30,'testRepoView',3),(30,'testRepoCreate',4),(30,'testRepoEdit',5),(30,'testPrepView',6),(30,'testPrepCreate',7),(30,'testPrepEdit',8),(30,'testExecView',9),(30,'testExecCreate',10),(30,'testExecEdit',11),(30,'defectView',12),(30,'defectCreate',13),(30,'defectEdit',14),(31,'testExecView',0),(31,'testExecCreate',1),(31,'testExecEdit',2),(31,'defectView',3),(31,'defectCreate',4),(31,'defectEdit',5),(32,'defectView',0),(32,'defectEdit',1);
/*!40000 ALTER TABLE `tm_ProjectRole_unitRoles` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_ProjectWidget_parameters`
--
-- WHERE:  tm_ProjectWidget_id in (select id from tm_ProjectWidget where account_id = 1)

/*!40000 ALTER TABLE `tm_ProjectWidget_parameters` DISABLE KEYS */;
INSERT INTO `tm_ProjectWidget_parameters` VALUES (1,'Defect','entity'),(1,'Defects over time','graphLabel'),(1,'temporal','graphType'),(1,'created','temporalField'),(1,'DATE','xAxis'),(1,'COUNT','yAxis'),(2,'Defect','entity'),(2,'All defects','title'),(3,'Requirement','entity'),(3,'All requirements','title'),(4,'Instance','entity'),(4,'All instances','title'),(5,'Script','entity'),(5,'All scripts','title'),(6,'Defect','entity'),(6,'Defects over time','graphLabel'),(6,'temporal','graphType'),(6,'created','temporalField'),(6,'DATE','xAxis'),(6,'COUNT','yAxis'),(7,'Defect','entity'),(7,'All defects','title'),(8,'Requirement','entity'),(8,'All requirements','title'),(9,'Instance','entity'),(9,'All instances','title'),(10,'Script','entity'),(10,'All scripts','title');
/*!40000 ALTER TABLE `tm_ProjectWidget_parameters` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_Requirement_tm_test_Script`
--
-- WHERE:  tm_Requirement_id in (select id from tm_Requirement where account_id = 1)

/*!40000 ALTER TABLE `tm_Requirement_tm_test_Script` DISABLE KEYS */;
INSERT INTO `tm_Requirement_tm_test_Script` VALUES (13,1);
/*!40000 ALTER TABLE `tm_Requirement_tm_test_Script` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_Requirement_tm_test_Tag`
--
-- WHERE:  tm_Requirement_id in (select id from tm_Requirement where account_id = 1)

/*!40000 ALTER TABLE `tm_Requirement_tm_test_Tag` DISABLE KEYS */;
INSERT INTO `tm_Requirement_tm_test_Tag` VALUES (1,2),(8,1),(11,1),(10,1),(12,1),(7,1),(13,1);
/*!40000 ALTER TABLE `tm_Requirement_tm_test_Tag` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_test_Script_tm_test_Tag`
--
-- WHERE:  tm_test_Script_id in (select id from tm_test_Script where account_id = 1)

/*!40000 ALTER TABLE `tm_test_Script_tm_test_Tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tm_test_Script_tm_test_Tag` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_User_tm_ProjectRole`
--
-- WHERE:  tm_User_id in (select id from tm_User where account_id = 1)

/*!40000 ALTER TABLE `tm_User_tm_ProjectRole` DISABLE KEYS */;
INSERT INTO `tm_User_tm_ProjectRole` VALUES (1,1),(34,32),(33,31),(2,30),(3,30);
/*!40000 ALTER TABLE `tm_User_tm_ProjectRole` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
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
-- Dumping data for table `tm_User_accountRoles`
--
-- WHERE:  tm_User_id in (select id from tm_User where account_id = 1)

/*!40000 ALTER TABLE `tm_User_accountRoles` DISABLE KEYS */;
INSERT INTO `tm_User_accountRoles` VALUES (1,'userCreate',0),(1,'userEdit',1),(1,'userDelete',2),(1,'projectCreate',3),(1,'projectEdit',4),(1,'projectDelete',5),(1,'accountAdmin',6);
/*!40000 ALTER TABLE `tm_User_accountRoles` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-08-01 23:06:26
