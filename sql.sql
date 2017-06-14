-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: estoque
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `aut_autorizacao`
--

DROP TABLE IF EXISTS `aut_autorizacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aut_autorizacao` (
  `AUT_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `AUT_NOME` varchar(20) NOT NULL,
  PRIMARY KEY (`AUT_ID`),
  UNIQUE KEY `AUT_NOME_UK` (`AUT_NOME`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aut_autorizacao`
--

LOCK TABLES `aut_autorizacao` WRITE;
/*!40000 ALTER TABLE `aut_autorizacao` DISABLE KEYS */;
INSERT INTO `aut_autorizacao` VALUES (3,'ROLE_ADMIN'),(1,'ROLE_USER'),(2,'ROLE_USUARIO');
/*!40000 ALTER TABLE `aut_autorizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empresa`
--

DROP TABLE IF EXISTS `empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresa` (
  `EMP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_NOME` varchar(45) NOT NULL,
  `EMP_RAZAOSOCIAL` varchar(100) NOT NULL,
  PRIMARY KEY (`EMP_ID`),
  UNIQUE KEY `EMP_ID_UNIQUE` (`EMP_ID`),
  UNIQUE KEY `EMP_NOME_UNIQUE` (`EMP_NOME`),
  UNIQUE KEY `EMP_RAZAOSOCIAL_UNIQUE` (`EMP_RAZAOSOCIAL`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empresa`
--

LOCK TABLES `empresa` WRITE;
/*!40000 ALTER TABLE `empresa` DISABLE KEYS */;
INSERT INTO `empresa` VALUES (5,'bãoão','ba'),(7,'ba','bas'),(8,'ast','ast'),(9,'Cowboy','Uma empresa qualquer'),(10,'asdasd','asdsad'),(11,'asw','asw'),(12,'aws','aws'),(13,'aw','aw'),(19,'teste12','teste12');
/*!40000 ALTER TABLE `empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventos`
--

DROP TABLE IF EXISTS `eventos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eventos` (
  `EV_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EV_NOME` varchar(45) NOT NULL,
  `EV_LAT` varchar(45) NOT NULL,
  `EV_LONG` varchar(45) NOT NULL,
  `EV_LOTACAO` int(11) NOT NULL,
  `EV_CIDADE` varchar(45) NOT NULL,
  `EV_DATA` varchar(45) NOT NULL,
  `EMP_ID` int(11) NOT NULL,
  PRIMARY KEY (`EV_ID`),
  KEY `fk_evento_emp_idx` (`EMP_ID`),
  CONSTRAINT `fk_evento_emp` FOREIGN KEY (`EMP_ID`) REFERENCES `empresa` (`EMP_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventos`
--

LOCK TABLES `eventos` WRITE;
/*!40000 ALTER TABLE `eventos` DISABLE KEYS */;
INSERT INTO `eventos` VALUES (2,'Festa do Cowboy','-23.1213196','-45.7024547',11,'cacapava','11/11/1111',5),(3,'Festa do Cowboy','-23.1213196','-50.7024547',125,'Ubatuba','22/22/2222',5),(4,'Festa do Cowboy','-23.1213196','-48.7024547',1251,'São josé dos Campos','33/33/3333',5);
/*!40000 ALTER TABLE `eventos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uau_usuario_autorizacao`
--

DROP TABLE IF EXISTS `uau_usuario_autorizacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uau_usuario_autorizacao` (
  `USR_ID` bigint(20) unsigned NOT NULL,
  `AUT_ID` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`USR_ID`,`AUT_ID`),
  KEY `UAU_AUTORIZACAO_FK` (`AUT_ID`),
  CONSTRAINT `uau_usuario_autorizacao_ibfk_1` FOREIGN KEY (`USR_ID`) REFERENCES `usr_usuario` (`USR_ID`) ON UPDATE CASCADE,
  CONSTRAINT `uau_usuario_autorizacao_ibfk_2` FOREIGN KEY (`AUT_ID`) REFERENCES `aut_autorizacao` (`AUT_ID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uau_usuario_autorizacao`
--

LOCK TABLES `uau_usuario_autorizacao` WRITE;
/*!40000 ALTER TABLE `uau_usuario_autorizacao` DISABLE KEYS */;
INSERT INTO `uau_usuario_autorizacao` VALUES (1,1),(29,1),(46,1),(49,1),(54,1),(55,1),(56,1),(61,1),(66,1),(76,1),(79,1),(82,1),(107,1),(108,1),(34,3),(67,3),(102,3),(103,3),(111,3),(112,3);
/*!40000 ALTER TABLE `uau_usuario_autorizacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uau_usuario_empresa`
--

DROP TABLE IF EXISTS `uau_usuario_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uau_usuario_empresa` (
  `USR_ID` bigint(20) unsigned NOT NULL,
  `EMP_ID` int(11) NOT NULL,
  KEY `fk_usuario_id_idx` (`USR_ID`),
  KEY `fk_empresa_id_idx` (`EMP_ID`),
  CONSTRAINT `fk_empresa_id` FOREIGN KEY (`EMP_ID`) REFERENCES `empresa` (`EMP_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_id` FOREIGN KEY (`USR_ID`) REFERENCES `usr_usuario` (`USR_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uau_usuario_empresa`
--

LOCK TABLES `uau_usuario_empresa` WRITE;
/*!40000 ALTER TABLE `uau_usuario_empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `uau_usuario_empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usr_usuario`
--

DROP TABLE IF EXISTS `usr_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usr_usuario` (
  `USR_ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `USR_NOME` varchar(20) NOT NULL,
  `USR_SENHA` varchar(50) NOT NULL,
  `USR_CPF` varchar(45) NOT NULL,
  `EMP_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`USR_ID`),
  UNIQUE KEY `USR_NOME_UK` (`USR_NOME`),
  KEY `FK_usuario_empresa_idx` (`EMP_ID`),
  CONSTRAINT `FK_usuario_empresa` FOREIGN KEY (`EMP_ID`) REFERENCES `empresa` (`EMP_ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usr_usuario`
--

LOCK TABLES `usr_usuario` WRITE;
/*!40000 ALTER TABLE `usr_usuario` DISABLE KEYS */;
INSERT INTO `usr_usuario` VALUES (1,'testa','c24bc7cb8663e12740df2e8b29036e0c','as',NULL),(3,'Lucas','Luz','',NULL),(4,'Pereira','Nascimento','',NULL),(13,'rafaesl','1234','',NULL),(14,'rafaesla','1234','',NULL),(17,'rafaeslae','1234','',NULL),(21,'rafaeslaes','1234','',NULL),(29,'teste','c24bc7cb8663e12740df2e8b29036e0c','as',NULL),(32,'admin','21232f297a57a5a743894a0e4a801fc3','',NULL),(34,'testea','testea','',NULL),(46,'teste1','e959088c6049f1104c84c9bde5560a13','123345678901',NULL),(49,'teste11','23bbfdffa48785df2d0b07783f12235e','123345678901',NULL),(54,'testona','8818A2FFA833F76BCDF85A2A4A93E98F','as',NULL),(55,'rafafafa','187ef4436122d1cc2f40dc2b92f0eba0','123',NULL),(56,'adss','091903C59612814C3A04B2B85E5D925D','123345678901',NULL),(61,'adão','67bd590f48dc45451b47d88ab3860636','123345678901',NULL),(66,'adãao','96f033cc93f838e08e24e7406f70042c','123345678901',NULL),(67,'b','92eb5ffee6ae2fec3ad71c777531578f','123345678901',NULL),(76,'as','7815696ecbf1c96e6894b779456d330e','as',NULL),(79,'ab','202cb962ac59075b964b07152d234b70','123',NULL),(82,'a','f970e2767d0cfe75876ea857f92e319b','a',NULL),(102,'c','4a8a08f09d37b73795649038408b5f33','c',NULL),(103,'m','6f8f57715090da2632453988d9a1501b','123213213',NULL),(107,'y','415290769594460e2e485922904f345d','y',NULL),(108,'123','202cb962ac59075b964b07152d234b70','a',NULL),(111,'teste12','0940004e70ce8d82b440d3c1244dfdee','12345567',19),(112,'teste3','507eb04c9c427e9f961e47a7204fac41','123',NULL);
/*!40000 ALTER TABLE `usr_usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-13 21:13:33
