-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: hcaschedule
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.04.1

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
-- Table structure for table `Appointment`
--

DROP TABLE IF EXISTS `Appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Appointment` (
  `Appointment$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `Appointment$block` int(11) DEFAULT NULL,
  `Appointment$startMinute` int(11) DEFAULT NULL,
  `Appointment$weekDay` int(11) DEFAULT NULL,
  `R_Patient` bigint(20) DEFAULT NULL,
  `R_Schedule` bigint(20) DEFAULT NULL,
  `R_HCA` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Appointment$PersistentID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Appointment`
--

LOCK TABLES `Appointment` WRITE;
/*!40000 ALTER TABLE `Appointment` DISABLE KEYS */;
INSERT INTO `Appointment` VALUES (1,2,22,2,1,1,1);
/*!40000 ALTER TABLE `Appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Availability`
--

DROP TABLE IF EXISTS `Availability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Availability` (
  `Availability$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `Availability$effective` date DEFAULT NULL,
  `Availability$open$0$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$0$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$0$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$0$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$0$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$0$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$0$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$0$3$startMinute` int(11) DEFAULT NULL,
  `Availability$open$1$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$1$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$1$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$1$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$1$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$1$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$1$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$1$3$startMinute` int(11) DEFAULT NULL,
  `Availability$open$2$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$2$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$2$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$2$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$2$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$2$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$2$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$2$3$startMinute` int(11) DEFAULT NULL,
  `Availability$open$3$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$3$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$3$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$3$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$3$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$3$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$3$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$3$3$startMinute` int(11) DEFAULT NULL,
  `Availability$open$4$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$4$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$4$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$4$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$4$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$4$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$4$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$4$3$startMinute` int(11) DEFAULT NULL,
  `Availability$open$5$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$5$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$5$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$5$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$5$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$5$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$5$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$5$3$startMinute` int(11) DEFAULT NULL,
  `Availability$open$6$0$endMinute` int(11) DEFAULT NULL,
  `Availability$open$6$0$startMinute` int(11) DEFAULT NULL,
  `Availability$open$6$1$endMinute` int(11) DEFAULT NULL,
  `Availability$open$6$1$startMinute` int(11) DEFAULT NULL,
  `Availability$open$6$2$endMinute` int(11) DEFAULT NULL,
  `Availability$open$6$2$startMinute` int(11) DEFAULT NULL,
  `Availability$open$6$3$endMinute` int(11) DEFAULT NULL,
  `Availability$open$6$3$startMinute` int(11) DEFAULT NULL,
  `R_Patient` bigint(20) DEFAULT NULL,
  `R_HCA` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`Availability$PersistentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Availability`
--

LOCK TABLES `Availability` WRITE;
/*!40000 ALTER TABLE `Availability` DISABLE KEYS */;
/*!40000 ALTER TABLE `Availability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HCA`
--

DROP TABLE IF EXISTS `HCA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HCA` (
  `HCA$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `HCA$calendarID` text,
  `HCA$cellNumber` text,
  `HCA$emailAddress` text,
  `HCA$firstName` text,
  `HCA$hasVehicle` tinyint(1) DEFAULT NULL,
  `HCA$homeNumber` text,
  `HCA$lastName` text,
  `HCA$lat` double DEFAULT NULL,
  `HCA$lon` double DEFAULT NULL,
  `HCA$password` text,
  `HCA$userName` text,
  `HCA$voided` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`HCA$PersistentID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HCA`
--

LOCK TABLES `HCA` WRITE;
/*!40000 ALTER TABLE `HCA` DISABLE KEYS */;
INSERT INTO `HCA` VALUES (1,NULL,NULL,NULL,'hca1',NULL,NULL,NULL,NULL,NULL,'hca1','hca1',NULL),(2,NULL,'','','hca2',NULL,'','',NULL,NULL,'hca2','hca2',0);
/*!40000 ALTER TABLE `HCA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MediaMessage`
--

DROP TABLE IF EXISTS `MediaMessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MediaMessage` (
  `MediaMessage$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `MediaMessage$created` bigint(20) DEFAULT NULL,
  `MediaMessage$mimetype` text,
  `MediaMessage$note` text,
  `MediaMessage$timestamp` datetime DEFAULT NULL,
  `MediaMessage$type` int(11) DEFAULT NULL,
  `MediaMessage$urgent` tinyint(1) DEFAULT NULL,
  `MediaMessage$uri` text,
  `MediaMessage$voided` tinyint(1) DEFAULT NULL,
  `R_Appointment` bigint(20) DEFAULT NULL,
  `R_HCA` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`MediaMessage$PersistentID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MediaMessage`
--

LOCK TABLES `MediaMessage` WRITE;
/*!40000 ALTER TABLE `MediaMessage` DISABLE KEYS */;
INSERT INTO `MediaMessage` VALUES (1,1371572786070,'image/jpeg','','2013-06-18 10:26:26',2,0,NULL,0,1,NULL),(2,1371572803242,'image/jpeg','','2013-06-18 10:26:43',2,0,NULL,0,1,NULL),(3,1371572804360,'image/jpeg','','2013-06-18 10:26:44',2,0,NULL,0,1,NULL),(4,1371572805145,'image/jpeg','','2013-06-18 10:26:45',2,0,NULL,0,1,NULL);
/*!40000 ALTER TABLE `MediaMessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Patient`
--

DROP TABLE IF EXISTS `Patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Patient` (
  `Patient$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `Patient$alerts` text,
  `Patient$ccEmail` text,
  `Patient$ccName` text,
  `Patient$ccPhone` text,
  `Patient$cellNumber` text,
  `Patient$emailAddress` text,
  `Patient$emergAddr` text,
  `Patient$emergName` text,
  `Patient$emergPhone` text,
  `Patient$emergRelation` text,
  `Patient$extraInformation` text,
  `Patient$firstName` text,
  `Patient$homeNumber` text,
  `Patient$lastName` text,
  `Patient$medicalHistory` text,
  `Patient$risks` text,
  `Patient$socialHistory` text,
  `Patient$voided` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Patient$PersistentID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Patient`
--

LOCK TABLES `Patient` WRITE;
/*!40000 ALTER TABLE `Patient` DISABLE KEYS */;
INSERT INTO `Patient` VALUES (1,NULL,NULL,NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,'','','',NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `Patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PersistentAddress`
--

DROP TABLE IF EXISTS `PersistentAddress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PersistentAddress` (
  `PersistentAddress$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `PersistentAddress$PCode` text,
  `PersistentAddress$city` text,
  `PersistentAddress$country` text,
  `PersistentAddress$latitude` double DEFAULT NULL,
  `PersistentAddress$longituted` double DEFAULT NULL,
  `PersistentAddress$province` text,
  `PersistentAddress$street` text,
  `R_Patient` bigint(20) DEFAULT NULL,
  `R_HCA` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PersistentAddress$PersistentID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PersistentAddress`
--

LOCK TABLES `PersistentAddress` WRITE;
/*!40000 ALTER TABLE `PersistentAddress` DISABLE KEYS */;
INSERT INTO `PersistentAddress` VALUES (1,'','','CA',NULL,NULL,'','',1,NULL),(2,'','','CA',NULL,NULL,'','',NULL,2);
/*!40000 ALTER TABLE `PersistentAddress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Schedule`
--

DROP TABLE IF EXISTS `Schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Schedule` (
  `Schedule$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `Schedule$startDate` date DEFAULT NULL,
  PRIMARY KEY (`Schedule$PersistentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `Schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ScheduleChange`
--

DROP TABLE IF EXISTS `ScheduleChange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ScheduleChange` (
  `ScheduleChange$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `ScheduleChange$date` date DEFAULT NULL,
  `ScheduleChange$timeBlocks$0$endMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$0$startMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$1$endMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$1$startMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$2$endMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$2$startMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$3$endMinute` int(11) DEFAULT NULL,
  `ScheduleChange$timeBlocks$3$startMinute` int(11) DEFAULT NULL,
  `R_Patient` bigint(20) DEFAULT NULL,
  `R_HCA` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ScheduleChange$PersistentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ScheduleChange`
--

LOCK TABLES `ScheduleChange` WRITE;
/*!40000 ALTER TABLE `ScheduleChange` DISABLE KEYS */;
/*!40000 ALTER TABLE `ScheduleChange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Service`
--

DROP TABLE IF EXISTS `Service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Service` (
  `Service$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `Service$description` text,
  `Service$duration` int(11) DEFAULT NULL,
  `Service$name` text,
  `Service$voided` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Service$PersistentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Service`
--

LOCK TABLES `Service` WRITE;
/*!40000 ALTER TABLE `Service` DISABLE KEYS */;
/*!40000 ALTER TABLE `Service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceRecord`
--

DROP TABLE IF EXISTS `ServiceRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceRecord` (
  `ServiceRecord$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `ServiceRecord$status` int(11) DEFAULT NULL,
  `ServiceRecord$timestamp` datetime DEFAULT NULL,
  `R_Service` bigint(20) DEFAULT NULL,
  `R_Appointment` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ServiceRecord$PersistentID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceRecord`
--

LOCK TABLES `ServiceRecord` WRITE;
/*!40000 ALTER TABLE `ServiceRecord` DISABLE KEYS */;
INSERT INTO `ServiceRecord` VALUES (1,2,'2013-06-18 10:39:24',1,1);
/*!40000 ALTER TABLE `ServiceRecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ServiceRequirement`
--

DROP TABLE IF EXISTS `ServiceRequirement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ServiceRequirement` (
  `ServiceRequirement$PersistentID` int(11) NOT NULL AUTO_INCREMENT,
  `ServiceRequirement$sensitive` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$time` int(11) DEFAULT NULL,
  `ServiceRequirement$days$0` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$days$1` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$days$2` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$days$3` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$days$4` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$days$5` tinyint(1) DEFAULT NULL,
  `ServiceRequirement$days$6` tinyint(1) DEFAULT NULL,
  `R_Service` bigint(20) DEFAULT NULL,
  `R_Patient` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ServiceRequirement$PersistentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceRequirement`
--

LOCK TABLES `ServiceRequirement` WRITE;
/*!40000 ALTER TABLE `ServiceRequirement` DISABLE KEYS */;
/*!40000 ALTER TABLE `ServiceRequirement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-06-18 10:44:58
