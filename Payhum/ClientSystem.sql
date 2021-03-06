-- This file inserts pre-populated data into the repos
-- for Client Module

USE payhumrepo;

--
-- Dumping data for table `position`
--

/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` (`id`,`name`,`lowsal`,`highsal`) VALUES 
 (1,'Staff_1',50000,1000000);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;

--
-- Dumping data for table types
--
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` (`id`, `name`, `desc`,`type`) VALUES
(1,'Donation','Donation made by the employee','TAXDETAILS'),
(2,'Employee Social Security','Percentage of base salary paid as Social Security by the employee','TAXDETAILS'),
(3,'Basic Allowance Limit','Maximum amount of deduction for Basic Allowance','TAXDETAILS'),
(4,'Basic Allowance Percentage','Percentage of Total Income paid exempted as Basic Allowance','TAXDETAILS'),
(5,'Income Limit for no Tax','Amount upto which there is no Tax','TAXDETAILS'),
(6,'Children','Amount which is exempted if the child is studying (applicable per child)','TAXDETAILS'),
(7,'Supporting Spouse','Amount which is exempted if Spouse is dependent','TAXDETAILS'),
(8,'Free Fully Furnished Accomodation','Employer sponsored. Percentage of Gross Salary added as additional income','TAXDETAILS'),
(9,'Free Not Furnished Accomodation','Employer sponsored. Percentage of Gross Salary added as additional income','TAXDETAILS'),
(10,'Employer contribution for Social Security','Employer contributions percentage of base salary','TAXDETAILS'),
(11,'Local','Local employee','RESIDENTTYPE'),
(12,'Resident Foreigner','Resident foreigner employee','RESIDENTTYPE'),
(13,'Non Resident Foreigner','Non resident foreigner employee','RESIDENTTYPE'),
(14,'Free Fully Furnished Accomodation','Fully furnished accomodation','ACCOMODATIONTYPE'),
(15,'Free Not Furnished Accomodation','Not furnished accomodation','ACCOMODATIONTYPE'),
(16,'No Accomodation','No accomodation provided','ACCOMODATIONTYPE'),
(17,'Student','Student','OCCUPATIONTYPE'),
(18,'Working','Working','OCCUPATIONTYPE'),
(19,'None','No occupation','OCCUPATIONTYPE'),
(20,'Spouse','Spouse','DEPENDENTTYPE'),
(21,'Child','Child','DEPENDENTTYPE'),
(22,'Limit for the Employer contribution for Social Security','Maximum amount limit for the Employer contribution for Social Security','TAXDETAILS'),
(23,'Limit for the Employee contribution for Social Security','Maximum amount limit for the Employee contribution for Social Security','TAXDETAILS'),
(24,'MMK','Myanmar Currency - Kyats','CURRENCY'),
(25,'USD','United States of America Currency - Dollars($)','CURRENCY'),
(26,'EURO','European Union Currency - EURO','CURRENCY'),
(27,'POUND','United Kingdom Currency - Pounds','CURRENCY');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`companyId`,`name`, `fystart`) VALUES 
 (1,'C-0000','MasterComp', 1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

--
-- Dumping data for table `branch`
--

/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` (`id`,`name`,`address`,`companyId`) VALUES 
 (1,'MMain','Main Office', 1);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;

--
-- Dumping data for table `department`
--
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`,`deptname`,`branchId`) VALUES 
 (1,'MAdmin',1);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

--
-- Dumping data for table `employee`
--
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`deptId`,`empNationalID`,`emerContactName`,`emerContactNo`,`address`,`phoneNo`, `currency`) VALUES 
 (1,'CLI-0000','Guest',' ',' ','Male','1980-06-19 00:00:00','2011-06-14 00:00:00',1,'/data/photo/185145_481927465161666_2082997324_n.jpg','ACTIVE',1,'true',11,1,'N1','A','1','address1','123456',24);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

--
-- Dumping data for table `leavetype`
--

/*!40000 ALTER TABLE `leavetype` DISABLE KEYS */;
INSERT INTO `leavetype` (`id`,`name`,`day_cap`) VALUES 
 (1,'Paid Leave',0);
/*!40000 ALTER TABLE `leavetype` ENABLE KEYS */;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`name`,`version`) VALUES 
 (1,'HumanResource',1),
 (2,'PageAdmin',1),
 (3,'Accountant',1),
 (4,'Employee',1);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`username`,`password`,`employeeId`,`roleId`,`version`) VALUES 
 (1,'guest','welcome',1,1,1),
 (2,'cadmin','allowme',1,1,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

--
-- Dumping data for table exemptionstype
--
/*!40000 ALTER TABLE `exemptionstype` DISABLE KEYS */;
INSERT INTO `exemptionstype` (`id`, `name`, `description`, `version`) VALUES
(1, 'Supporting Spouse', 'Supporting Spouse', 1),
(2, 'Children', 'Children', 1),
(3, 'Basic Allowance', 'Basic Allowance', 1),
(4, 'Basic Allowance Percentage', 'Allowance Percentage', 1),
(5, 'Basic Allowance Limit', 'Basic Allowance Limit', 1);
/*!40000 ALTER TABLE `exemptionstype` ENABLE KEYS */;


--
-- Dumping data for table deductiontype
--
/*!40000 ALTER TABLE `deductiontype` DISABLE KEYS */;
INSERT INTO `deductiontype` (`id`, `name`, `description`, `version`) VALUES
(1, 'Self Life Insurance', 'Life Insurance of Self', 1),
(2, 'Spouse Insurance', 'Life Insurance of Spouse', 1),
(3, 'Life Insurance', 'self and spouse insurance', 1),
(4, 'Donation', 'Donation to Charity', 1),
(5, 'Employee Social Security', 'Social Security', 1);
/*!40000 ALTER TABLE `deductiontype` ENABLE KEYS */;

--
-- Dumping data for table overtime_payrate
--
/*!40000 ALTER TABLE `overtime_payrate` DISABLE KEYS */;
INSERT INTO `overtime_payrate` (`id`, `day_group`, `grosspay_percent`) VALUES
(1,'Weekday',1),
(2,'Weekend',2),
(3,'Holidays',2);
/*!40000 ALTER TABLE `overtime_payrate` ENABLE KEYS */;

--
-- Dumping data for table taxrates
--
/*!40000 ALTER TABLE `taxrates` DISABLE KEYS */;
INSERT INTO `taxrates` (`id`, `income_from`, `income_to`,`income_percent`,`residentTypeId`) VALUES
(1,1,2000000,0,11),
(2,2000001,5000000,5,11),
(3,5000001,10000000,10,11),
(4,10000001,20000000,15,11),
(5,20000001,30000000,20,11),
(12,30000001,30000001,25,11),
(13,1,1,35,13);
/*!40000 ALTER TABLE `taxrates` ENABLE KEYS */;

--
-- Dumping data for table taxdetails
--
/*!40000 ALTER TABLE `taxdetails` DISABLE KEYS */;
INSERT INTO `taxdetails` (`id`, `typeId`, `amount`) VALUES
(1,1,25),
(2,2,2),
(3,3,10000000),
(4,4,20),
(5,5,2000000),
(6,6,300000),
(7,7,500000),
(8,8,12.5),
(9,9,10),
(10,10,3),
(11,22,108000),
(12,23,72000);
/*!40000 ALTER TABLE `taxdetails` ENABLE KEYS */;

--
-- Dumping data for table paycycle
--
/*!40000 ALTER TABLE `paycycle` DISABLE KEYS */;
INSERT INTO `paycycle` (`id`, `name`, `selected`, `dayofmonth`) VALUES
(1,'Monthly',3, 15);
/*!40000 ALTER TABLE `paycycle` ENABLE KEYS */;

--
-- Dumping data for table payhum_config
--
/*!40000 ALTER TABLE `payhum_config` DISABLE KEYS */;
INSERT INTO `payhum_config` (`configName`, `configValue`) VALUES
('EMODE','CMODE'),
('PBRANCH','0'),
('LUSERCOMP','1'),
('USD_MMK','1'),
('EURO_MMK','1'),
('POUND_MMK','1'),
('PAYROLLDATE_ID','0'),
('SAL_PAY_DATE', '');
/*!40000 ALTER TABLE `payhum_config` ENABLE KEYS */;

--
-- Dumping data for Holidays
--
/*!40000 ALTER TABLE `holidays` DISABLE KEYS */;
INSERT INTO `holidays` (`id`, `date`, `name`) VALUES
(1, '2013-01-01 00:00:00','Kayin New Year Day'),
(2, '2013-01-04 00:00:00','Independence Day'),
(3, '2013-02-12 00:00:00','Union Day'),
(4, '2013-03-02 00:00:00','Peasant’s Day'),
(5, '2013-03-01 00:00:00','Full Moon Day of Tabaung'),
(6, '2013-03-27 00:00:00','Tatmadaw Day (Armed Forces Day)'),
(7, '2013-04-12 00:00:00','Myanmar New Year'),
(8, '2013-04-13 00:00:00','Myanmar New Year'),
(9, '2013-04-14 00:00:00','Myanmar New Year'),
(10, '2013-04-15 00:00:00','Myanmar New Year'),
(11, '2013-04-16 00:00:00','Myanmar New Year'),
(12, '2013-04-17 00:00:00','Myanmar New Year'),
(13, '2013-04-18 00:00:00','Myanmar New Year'),
(14, '2013-04-19 00:00:00','Myanmar New Year'),
(15, '2013-04-20 00:00:00','Myanmar New Year'),
(16, '2013-04-21 00:00:00','Myanmar New Year'),
(17, '2013-05-01 00:00:00','May Day (World Workers Day)'),
(18, '2013-06-01 00:00:00','Full Moon Day of Kasone'),
(19, '2013-07-01 00:00:00','Full Moon Day of Waso'),
(20, '2013-07-19 00:00:00','Martyr’s Day'),
(21, '2013-10-01 00:00:00','Full Moon Day of Thadingyut'),
(22, '2013-11-01 00:00:00','Full Moon Day of Tazangmone'),
(23, '2013-11-01 00:00:00','National Day'),
(24, '2013-12-25 00:00:00','Christmas Day');
/*!40000 ALTER TABLE `holidays` ENABLE KEYS */;

--
-- Dumping data for table `benefitype`
--
/*!40000 ALTER TABLE `benefitype` DISABLE KEYS */;
INSERT INTO `benefitype` (`id`,`name`,`cap`) VALUES 
 (1,'Transport Allowance',50000);
/*!40000 ALTER TABLE `benefitype` ENABLE KEYS */;

--
-- Create payhum user and give all privs to that user.
--
GRANT ALL ON payhumrepo.* TO 'payhum'@localhost IDENTIFIED BY 'welcome1';

