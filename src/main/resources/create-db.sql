
DROP SCHEMA IF EXISTS `spring-data-jpa`;

CREATE SCHEMA `spring-data-jpa`;

use `spring-data-jpa`;

SET FOREIGN_KEY_CHECKS = 0;

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
------------------------------------------------------------------------------
DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_DEPARTMENT_idx` (`department_id`),
    CONSTRAINT `FK_DEPARTMENT` FOREIGN KEY (`department_id`)
    REFERENCES `department` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `employee` VALUES
	(1,'Leslie','Andrews','leslie@luv2code.com', 1),
	(2,'Emma','Baumgarten','emma@luv2code.com', 1),
	(3,'Avani','Gupta','avani@luv2code.com', 1),
	(4,'Yuri','Petrov','yuri@luv2code.com', 2),
	(5,'Juan','Vega','juan@luv2code.com', 5);

------------------------------------------------------------------------------
DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `emp_id` int DEFAULT NULL,
  `department_id` int DEFAULT NULL,

UNIQUE KEY (`department_id`),
  KEY `FK_EMPLOYEE_idx`(`emp_id`),
    CONSTRAINT `FK_EMPLOYEE` FOREIGN KEY (`emp_id`)
    REFERENCES `employee` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,

  KEY `FK_DEPARTMENT_idx` (`department_id`),
    CONSTRAINT `FK_DEPARTMENTS` FOREIGN KEY (`department_id`)
    REFERENCES `department` (`id`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `manager` VALUES
	(1, 2),
	(2, 3),
	(3, 1),
	(4, 4 );

SET FOREIGN_KEY_CHECKS = 1;
