-- MySQL dump 10.13  Distrib 5.1.67, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: hcaschedule
-- ------------------------------------------------------
-- Server version	5.1.67-0ubuntu0.11.10.1

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
) ENGINE=MyISAM AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Appointment`
--

LOCK TABLES `Appointment` WRITE;
/*!40000 ALTER TABLE `Appointment` DISABLE KEYS */;
INSERT INTO `Appointment` VALUES (1,2,720,3,1,1,1),(2,2,780,4,1,1,1),(3,2,780,5,1,1,1),(4,2,780,6,1,1,1),(5,2,780,6,1,1,2),(6,2,780,5,1,1,2),(7,2,780,4,1,1,2),(8,2,780,3,1,1,2),(9,2,780,2,1,1,2),(10,2,780,1,1,1,2),(11,2,780,0,1,1,2),(12,1,400,6,1,4,1),(13,2,700,6,1,4,1),(14,3,1000,6,2,4,1),(15,3,1000,5,2,4,1),(16,2,700,4,1,4,1),(17,1,400,4,1,4,1),(18,1,400,5,1,4,1),(19,2,700,5,4,4,1);
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
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Availability`
--

LOCK TABLES `Availability` WRITE;
/*!40000 ALTER TABLE `Availability` DISABLE KEYS */;
INSERT INTO `Availability` VALUES (1,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(2,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,1),(3,'2013-03-24',360,360,750,615,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,495,495,885,765,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,1),(4,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(5,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,NULL),(6,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(7,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,NULL),(8,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(9,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,NULL),(10,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(11,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,2),(12,'2013-04-07',360,360,840,720,0,0,0,0,0,0,0,0,0,0,0,0,360,360,480,480,0,0,0,0,360,360,840,840,0,0,0,0,360,360,660,660,0,0,0,0,360,360,765,765,0,0,0,0,360,360,480,480,0,0,0,0,NULL,2),(13,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(14,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,NULL),(15,'2013-04-07',360,360,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,NULL),(16,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL),(17,NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,3),(18,'2013-04-07',360,360,900,480,0,0,0,0,0,0,0,0,0,0,0,0,360,360,885,480,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,3);
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
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HCA`
--

LOCK TABLES `HCA` WRITE;
/*!40000 ALTER TABLE `HCA` DISABLE KEYS */;
INSERT INTO `HCA` VALUES (1,NULL,'','hhblower@gmail.com','Horatio',0,' 1 (780) 123-4567','Hornblower',53.5216273,-113.5261124,'pass1','user1',0),(2,NULL,'','test@test.com','David',1,'12345678','Test',60.222,-115.2222,'david','david',0),(3,NULL,'','test1@test.com','Test1',1,' 1 (780) 111-1111','Test',NULL,NULL,'Test1','Test1',0);
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
  `MediaMessage$timestamp` text,
  `MediaMessage$type` int(11) DEFAULT NULL,
  `MediaMessage$urgent` tinyint(1) DEFAULT NULL,
  `MediaMessage$uri` text,
  `MediaMessage$voided` tinyint(1) DEFAULT NULL,
  `R_Appointment` bigint(20) DEFAULT NULL,
  `R_HCA` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`MediaMessage$PersistentID`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MediaMessage`
--

LOCK TABLES `MediaMessage` WRITE;
/*!40000 ALTER TABLE `MediaMessage` DISABLE KEYS */;
INSERT INTO `MediaMessage` VALUES (4,1364568076854,'image/jpeg','','Fri Mar 29 08:40:07 MDT 2013',2,0,'file:///storage/sdcard0/DCIM/Camera/1364567994469.jpg',0,12,1),(2,1364483951065,'image/jpeg','','Thu Mar 28 09:19:10 MDT 2013',2,0,'file:///storage/sdcard0/DCIM/Camera/1364483939348.jpg',0,2,1),(3,1364484080067,'video/mp4','garbage','Thu Mar 28 09:21:17 MDT 2013',0,0,'file:///storage/sdcard0/DCIM/Camera/20130328_092057.mp4',0,2,1),(5,1365781949627,'Text','Good day with peter','Fri Apr 12 09:52:29 MDT 2013',3,0,'',0,18,1),(6,1365787542948,'image/jpeg','','Fri Apr 12 11:25:42 MDT 2013',2,0,'file:///mnt/sdcard/DCIM/Camera/1365787535151.jpg',0,18,1),(7,1365801184325,'image/jpeg','','Fri Apr 12 15:13:03 MDT 2013',2,0,'file:///mnt/sdcard/DCIM/Camera/1365801177116.jpg',0,18,1),(8,1365801412297,'video/mp4','garbage','Fri Apr 12 15:16:50 MDT 2013',0,0,'file:///mnt/sdcard/DCIM/Camera/20130412_151636.mp4',0,18,1);
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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Patient`
--

LOCK TABLES `Patient` WRITE;
/*!40000 ALTER TABLE `Patient` DISABLE KEYS */;
INSERT INTO `Patient` VALUES (1,NULL,'dgibbs@123.com','Dylan Gibbs','780-999-7271','','ppatient@gmail.com','116 St, Edmonton, AB T6G 2R3','Eleni Stroulia','780-123-4567','Friend',NULL,'Peter',' 1 (780) 456-7890','Patient','History of pneumonia','Risk of agitation, damage to property','Verbally abusive',0),(2,NULL,'','','','','','','Dylan Gibbs','780-901-1295','',NULL,'Sasha','','McDonald','','','',1),(3,NULL,'','','','','','','','','',NULL,'dasd','','asd','','','',0),(4,NULL,'','','','','patient,test','Patient, Mother','Patient Mother','222222222','Mother',NULL,'Patient','122333333','David','','','',0);
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
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PersistentAddress`
--

LOCK TABLES `PersistentAddress` WRITE;
/*!40000 ALTER TABLE `PersistentAddress` DISABLE KEYS */;
INSERT INTO `PersistentAddress` VALUES (1,'T5J 3R6','Edmonton','CA',123.5555,32.3333,'Alberta','10101 103 Ave',NULL,1),(2,'T5H 1E1','Edmonton','CA',239.5555,56.3333,'Alberta','11112 109 Avenue',1,NULL),(3,'T3H 4X9','','CA',239.5555,-23.3333,'AB','',2,NULL),(4,'','','CA',29.5555,-223.3333,'','',3,NULL),(5,'T6G 2C6','edmonton','CA',-29.5555,89.3333,'alberta','11135,83 ave',NULL,2),(6,'pppppp','patient','CA',NULL,NULL,'patient','patient, patinent',4,NULL),(7,'T6G 2C5','Edmonton','CA',NULL,NULL,'Alberta','11134, 82 Aveue',NULL,3);
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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
INSERT INTO `Schedule` VALUES (1,'2013-03-24'),(2,'2013-03-31'),(3,'2013-04-07'),(4,'2013-04-07');
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
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ScheduleChange`
--

LOCK TABLES `ScheduleChange` WRITE;
/*!40000 ALTER TABLE `ScheduleChange` DISABLE KEYS */;
INSERT INTO `ScheduleChange` VALUES (1,'2013-04-10',360,360,480,480,0,0,0,0,NULL,NULL),(2,'2013-04-10',360,360,480,480,0,0,0,0,NULL,NULL);
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
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Service`
--

LOCK TABLES `Service` WRITE;
/*!40000 ALTER TABLE `Service` DISABLE KEYS */;
INSERT INTO `Service` VALUES (1,'brushing of the teeth',15,'Teeth Brushing',0),(2,'bathing',20,'bathe',0),(3,'service0',10,'serivce0',0),(4,'service1',11,'service1',0),(5,'service2',10,'service2',0),(6,'service3',3,'service3',0),(7,'service4',4,'service4',0),(8,'service5',5,'service5',0),(9,'service6',6,'service6',0),(10,'service7',7,'service7',0),(11,'service8',8,'service8',0),(12,'service9',9,'service9',NULL);
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
  `R_Service` bigint(20) DEFAULT NULL,
  `R_Appointment` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ServiceRecord$PersistentID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceRecord`
--

LOCK TABLES `ServiceRecord` WRITE;
/*!40000 ALTER TABLE `ServiceRecord` DISABLE KEYS */;
INSERT INTO `ServiceRecord` VALUES (1,0,1,1),(2,1,1,2);
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
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ServiceRequirement`
--

LOCK TABLES `ServiceRequirement` WRITE;
/*!40000 ALTER TABLE `ServiceRequirement` DISABLE KEYS */;
INSERT INTO `ServiceRequirement` VALUES (1,0,0,1,0,1,0,1,0,1,1,1),(2,0,0,0,0,0,1,1,1,1,1,2),(3,1,0,1,1,1,0,0,0,0,1,4),(4,1,0,0,1,1,1,0,0,0,2,1);
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

-- Dump completed on 2013-06-07 19:38:02
