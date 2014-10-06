CREATE DATABASE  IF NOT EXISTS `rej` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rej`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: rej
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appointment_details` varchar(255) DEFAULT NULL,
  `appointment_endt_date` datetime DEFAULT NULL,
  `appointment_start_date` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `doctor` bigint(20) DEFAULT NULL,
  `patient` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_gwfpw4dniri5s62kfvflto66x` (`doctor`),
  KEY `FK_swn8uotvddprfn0lrv540g50f` (`patient`),
  CONSTRAINT `FK_swn8uotvddprfn0lrv540g50f` FOREIGN KEY (`patient`) REFERENCES `person` (`id`),
  CONSTRAINT `FK_gwfpw4dniri5s62kfvflto66x` FOREIGN KEY (`doctor`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'Wizyta controlna','2014-08-15 12:50:00','2014-08-15 12:15:00',0,4,1);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bhours`
--

DROP TABLE IF EXISTS `bhours`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bhours` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `close_time` datetime DEFAULT NULL,
  `open_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `week_day` int(11) NOT NULL,
  `working` tinyint(1) DEFAULT NULL,
  `doctor` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_a5qdpfrix2oejdeprocq1ab2w` (`doctor`),
  CONSTRAINT `FK_a5qdpfrix2oejdeprocq1ab2w` FOREIGN KEY (`doctor`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bhours`
--

LOCK TABLES `bhours` WRITE;
/*!40000 ALTER TABLE `bhours` DISABLE KEYS */;
INSERT INTO `bhours` VALUES (1,'1970-01-01 14:00:00','1970-01-01 07:00:00',1,3,NULL,1),(2,'1970-01-01 12:00:00','1970-01-01 07:00:00',0,4,NULL,1),(3,'1970-01-01 14:00:00','1970-01-01 06:00:00',0,5,NULL,4),(4,'1970-01-01 11:00:00','1970-01-01 04:00:00',0,5,NULL,1),(5,'1970-01-01 17:00:00','1970-01-01 15:00:00',1,3,NULL,1),(6,'1970-01-01 17:00:00','1970-01-01 15:00:00',1,5,NULL,1),(7,'1970-01-01 12:00:00','1970-01-01 08:00:00',0,2,NULL,1),(8,'1970-01-01 12:00:00','1970-01-01 07:00:00',0,1,NULL,2),(9,'1970-01-01 16:00:00','1970-01-01 07:00:00',0,3,NULL,2),(10,'1970-01-01 12:00:00','1970-01-01 08:00:00',0,4,NULL,2),(11,'1970-01-01 16:00:00','1970-01-01 12:00:00',0,2,NULL,3);
/*!40000 ALTER TABLE `bhours` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doctor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `profession` varchar(20) NOT NULL,
  `surname` varchar(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (1,'Przemek ','Logopeda','Zawadzki',0),(2,'Jacek ','Laryngolog','Wojciechowski',0),(3,'Krzysztof','Pediatra','Majewski',0),(4,'Maciej ','Psycholog','Duda',0);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(40) NOT NULL,
  `birth_date` datetime DEFAULT NULL,
  `card_number` varchar(20) NOT NULL,
  `father_name` varchar(50) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `legitimationid` varchar(20) NOT NULL,
  `maiden_surname` varchar(20) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `nfz` varchar(20) NOT NULL,
  `pesel` varchar(12) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `telephone_number` varchar(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `attending_physician` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3tadys8jf6jgtftyisu1sgekm` (`attending_physician`),
  CONSTRAINT `FK_3tadys8jf6jgtftyisu1sgekm` FOREIGN KEY (`attending_physician`) REFERENCES `doctor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'ul. Pasterska 4 waw','1920-11-25 00:00:00','123456','Mirosław','Celestyn','123','Kamińska','remicek@gmail.com','123456','75091858791',' Gorski','51 513 20 57',0,1),(2,'ul. Brzegowa 54 Wro','1969-11-11 00:00:00','123','Arkadiusz','Radzimierz ','123','Małgorzata','remicek@gmail.com','12345','63011133738','Sobczak','1234',0,3),(3,' Gdańsk prosta 12','1988-12-12 00:00:00','1234','Zbigniew','Miron','123414','Asia','arkwaw@gmail.com','133','82060919199','Nowakowski','142424',0,2);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `ROLE` varchar(45) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `uni_username_role` (`ROLE`,`username`),
  KEY `fk_username_idx` (`username`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (2,'admin','ROLE_ADMIN'),(1,'admin','ROLE_USER'),(3,'user','ROLE_USER');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('admin','$2a$10$Upu3lux9ZafrSH7w9Yte.eTD6T1u/YpCChP42xGthRZRnNmGkH6A.',1),('user','$2a$10$Upu3lux9ZafrSH7w9Yte.eTD6T1u/YpCChP42xGthRZRnNmGkH6A.',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-08-03 15:15:37
