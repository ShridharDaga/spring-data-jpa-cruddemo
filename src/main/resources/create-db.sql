--DROP SCHEMA IF EXISTS `spring-data-jpa`;
--
--CREATE SCHEMA `spring-data-jpa`;
--
--use `spring-data-jpa`;
--
--SET FOREIGN_KEY_CHECKS = 0;
--
--DROP TABLE IF EXISTS `employee`;
--
--CREATE TABLE `employee` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `first_name` varchar(45) DEFAULT NULL,
--  `last_name` varchar(45) DEFAULT NULL,
--  `email` varchar(45) DEFAULT NULL,
--  `department_id` int DEFAULT NULL,
--    PRIMARY KEY (`id`),
--    KEY `FK_DETAIL_idx` (`department_id`),
--    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`department_id`)
--    REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--
----
---- Data for table `employee`
----
--
--INSERT INTO `employee` VALUES
--	(1,'Leslie','Andrews','leslie@luv2code.com', 1),
--	(2,'Emma','Baumgarten','emma@luv2code.com', 1),
--	(3,'Avani','Gupta','avani@luv2code.com', 1),
--	(4,'Yuri','Petrov','yuri@luv2code.com', 2),
--	(5,'Juan','Vega','juan@luv2code.com', 5);
--
--DROP TABLE IF EXISTS `department`;
--
--CREATE TABLE `department` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `name` varchar(45) DEFAULT NULL
----  `manager_id` int DEFAULT NULL,
----  PRIMARY KEY (`id`),
----  KEY `FK_DETAIL_idx` (`manager_id`),
----  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`manager_id`)
----  REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--
----INSERT INTO `department` VALUES
----	(1,'HR', 1),
----	(2,'Sales', 3),
----	(3,'Marketing', 3),
----	(4,'Accounts', 4),
----	(5,'Innovation', 1);
--
--
--INSERT INTO `department` VALUES
--	(1,'HR'),
--	(2,'Sales'),
--	(3,'Marketing'),
--	(4,'Accounts'),
--	(5,'Innovation');
--
--DROP TABLE IF EXISTS `manager`;
--
--CREATE TABLE `manager` (
--  `id` int DEFAULT NULL,
--  `department_id` int DEFAULT NULL,
--
--  KEY `FK_DETAIL_idx` (`department_id`),
--  CONSTRAINT `FK_DETAIL` FOREIGN KEY (`department_id`)
--  REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
--
--  KEY `FK_DETAIL_idx` (`id`),
--    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`id`)
--    REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--
--INSERT INTO `manager` VALUES
--	(1, 1),
--	(1, 3),
--	(3, 2),
--	(4, 4);
--
--SET FOREIGN_KEY_CHECKS = 1;


--DROP SCHEMA IF EXISTS `spring-data-jpa`;
--
--CREATE SCHEMA `spring-data-jpa`;
--
--use `spring-data-jpa`;
--
--SET FOREIGN_KEY_CHECKS = 0;
--
--DROP TABLE IF EXISTS `employee`;
--
--CREATE TABLE `employee` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `first_name` varchar(45) DEFAULT NULL,
--  `last_name` varchar(45) DEFAULT NULL,
--  `email` varchar(45) DEFAULT NULL,
--  `department_id` int DEFAULT NULL,
--    PRIMARY KEY (`id`),
--    KEY `FK_DETAIL_idx` (`department_id`),
--    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`department_id`)
--    REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--
----
---- Data for table `employee`
----
--
--INSERT INTO `employee` VALUES
--	(1,'Leslie','Andrews','leslie@luv2code.com', 1),
--	(2,'Emma','Baumgarten','emma@luv2code.com', 1),
--	(3,'Avani','Gupta','avani@luv2code.com', 1),
--	(4,'Yuri','Petrov','yuri@luv2code.com', 2),
--	(5,'Juan','Vega','juan@luv2code.com', 5);
--
--DROP TABLE IF EXISTS `department`;
--
--CREATE TABLE `department` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `name` varchar(45) DEFAULT NULL,
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--
--INSERT INTO `department` VALUES
--	(1,'HR'),
--	(2,'Sales'),
--	(3,'Marketing'),
--	(4,'Accounts'),
--	(5,'Innovation');
--
--DROP TABLE IF EXISTS `manager`;
--
--CREATE TABLE `manager` (
--  `id` int NOT NULL AUTO_INCREMENT,
--  `emp_id` int DEFAULT NULL,
--  `department_id` int DEFAULT NULL,
--
--  KEY (`department_id`),
--  CONSTRAINT FOREIGN KEY (`department_id`)
--  REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
--
--  KEY (`emp_id`),
--    CONSTRAINT FOREIGN KEY (`id`)
--    REFERENCES `employee` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
--) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--
--INSERT INTO `manager` VALUES
--	(1, 2, 3),
--	(2, 3, 4),
--	(3, 1, 2),
--	(4, 4, 1);
--
--SET FOREIGN_KEY_CHECKS = 1;




DROP SCHEMA IF EXISTS `spring-data-jpa`;

CREATE SCHEMA `spring-data-jpa`;

use `spring-data-jpa`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_DETAIL_idx` (`department_id`),
    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`department_id`)
    REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `employee`
--

INSERT INTO `employee` VALUES
	(1,'Leslie','Andrews','leslie@luv2code.com', 1),
	(2,'Emma','Baumgarten','emma@luv2code.com', 1),
	(3,'Avani','Gupta','avani@luv2code.com', 1),
	(4,'Yuri','Petrov','yuri@luv2code.com', 2),
	(5,'Juan','Vega','juan@luv2code.com', 5);

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `department` VALUES
	(1,'HR'),
	(2,'Sales'),
	(3,'Marketing'),
	(4,'Accounts'),
	(5,'Innovation');

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `emp_id` int DEFAULT NULL,
  `department_id` int DEFAULT NULL,

  KEY (`department_id`),
  CONSTRAINT FOREIGN KEY (`department_id`)
  REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,

  KEY (`emp_id`),
    CONSTRAINT FOREIGN KEY (`emp_id`)
    REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `manager` VALUES
	(1, 2, 3),
	(2, 3, 4),
	(3, 1, 2),
	(4, 4, 1);

SET FOREIGN_KEY_CHECKS = 1;








