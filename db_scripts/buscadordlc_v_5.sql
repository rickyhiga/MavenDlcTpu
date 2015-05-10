CREATE DATABASE  IF NOT EXISTS `buscadordlc` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `buscadordlc`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: buscadordlc
-- ------------------------------------------------------
-- Server version	5.5.32

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
-- Table structure for table `documento`
--

DROP TABLE IF EXISTS `documento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `url` varchar(75) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url_UNIQUE` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documento`
--

LOCK TABLES `documento` WRITE;
/*!40000 ALTER TABLE `documento` DISABLE KEYS */;
/*!40000 ALTER TABLE `documento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posteo`
--

DROP TABLE IF EXISTS `posteo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `posteo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vocabulario_id` int(11) NOT NULL,
  `documento_id` int(11) NOT NULL,
  `cant_apariciones_tf` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_posteo_vocabulario_idx` (`vocabulario_id`),
  KEY `fk_posteo_documentos1_idx` (`documento_id`),
  CONSTRAINT `fk_posteo_documentos1` FOREIGN KEY (`documento_id`) REFERENCES `documento` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_posteo_vocabulario` FOREIGN KEY (`vocabulario_id`) REFERENCES `vocabulario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posteo`
--

LOCK TABLES `posteo` WRITE;
/*!40000 ALTER TABLE `posteo` DISABLE KEYS */;
/*!40000 ALTER TABLE `posteo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vocabulario`
--

DROP TABLE IF EXISTS `vocabulario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vocabulario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `termino` varchar(100) NOT NULL,
  `cant_doc` int(11) NOT NULL,
  `max_tf` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `termino_UNIQUE` (`termino`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vocabulario`
--

LOCK TABLES `vocabulario` WRITE;
/*!40000 ALTER TABLE `vocabulario` DISABLE KEYS */;
/*!40000 ALTER TABLE `vocabulario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'buscadordlc'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-10 16:51:44
