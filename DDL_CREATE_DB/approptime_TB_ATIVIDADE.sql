CREATE DATABASE  IF NOT EXISTS `approptime` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `approptime`;
-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: approptime
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.16.04.1

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
-- Table structure for table `TB_ATIVIDADE`
--

DROP TABLE IF EXISTS `TB_ATIVIDADE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TB_ATIVIDADE` (
  `ID` bigint(10) NOT NULL,
  `ID_TAREFA` bigint(10) NOT NULL,
  `DT_INICIO` datetime NOT NULL,
  `DT_TERMINO` datetime DEFAULT NULL,
  `DS_OBSERVACOES` varchar(2000) DEFAULT NULL,
  `ID_TAG` bigint(10) DEFAULT NULL,
  `ID_WORKSPACE` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_TB_ATIVIDADE_TB_TAREFA_idx` (`ID_TAREFA`),
  KEY `fk_TB_ATIVIDADE_TB_TAG_idx` (`ID_TAG`),
  KEY `fk_TB_ATIVIDADE_TB_WORKSPACE_idx` (`ID_WORKSPACE`),
  CONSTRAINT `fk_TB_ATIVIDADE_TB_TAG` FOREIGN KEY (`ID_TAG`) REFERENCES `TB_TAG` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_ATIVIDADE_TB_TAREFA` FOREIGN KEY (`ID_TAREFA`) REFERENCES `TB_TAREFA` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_TB_ATIVIDADE_TB_WORKSPACE` FOREIGN KEY (`ID_WORKSPACE`) REFERENCES `TB_WORKSPACE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TB_ATIVIDADE`
--

LOCK TABLES `TB_ATIVIDADE` WRITE;
/*!40000 ALTER TABLE `TB_ATIVIDADE` DISABLE KEYS */;
INSERT INTO `TB_ATIVIDADE` VALUES (1,1,'2019-05-23 12:07:02','2019-05-23 12:17:40','',NULL,NULL),(2,1,'2019-05-23 12:17:40','2019-05-23 12:28:55','Realizando alterações no template',NULL,NULL),(3,2,'2019-05-23 12:28:55','2019-05-23 12:30:59','',NULL,NULL),(4,2,'2019-07-05 06:21:02','2019-07-05 06:25:21','',3,NULL);
/*!40000 ALTER TABLE `TB_ATIVIDADE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-05  4:49:10