-- This file inserts pre-populated data into the repos
-- for Master Module

USE payhumrepo;

--
-- Dumping data for table `position`
--

/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` (`id`,`name`,`lowsal`,`highsal`) VALUES 
 (1,'Master_Staff_1',50000,1000000);
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
(23,'Limit for the Employee contribution for Social Security','Maximum amount limit for the Employee contribution for Social Security','TAXDETAILS');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`companyId`,`name`) VALUES 
 (1,'C-0001','MasterComp');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

--
-- Dumping data for table `branch`
--

/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` (`id`,`name`,`address`,`companyId`) VALUES 
 (1,'Main','Address1', 1);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;

--
-- Dumping data for table `department`
--
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`,`deptname`,`branchId`) VALUES 
 (1,'Administration',1);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

--
-- Dumping data for table `employee`
--
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`deptId`,`empNationalID`,`emerContactName`,`emerContactNo`,`address`,`phoneNo`) VALUES 
 (1,'MAS-0001','Max','A','Leo','Male','1980-06-19 00:00:00','2011-06-14 00:00:00',1,'/data/photo/185145_481927465161666_2082997324_n.jpg','ACTIVE',1,'true',11,1,'N1','A','1','address1','123456');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

--
-- Dumping data for table emp_payroll_view
--
/*!40000 ALTER TABLE `emp_payroll` DISABLE KEYS */;
INSERT INTO `emp_payroll` (`id`, `employeeId`, `FULL_NAME`, `accomodationType`) VALUES
 (1,1,'JohnBWin',14);
/*!40000 ALTER TABLE `emp_payroll` ENABLE KEYS */;

--
-- Dumping data for table licenses
--
/*!40000 ALTER TABLE `licenses` DISABLE KEYS */;
INSERT INTO `licenses` (`id`, `fromdate`, `todate`, `companyId`,`active`,`licensekey`) VALUES
(1,'2013-04-01 00:00:00','2014-03-31 00:00:00',1,1,'10-119-87-41109-76');
/*!40000 ALTER TABLE `licenses` ENABLE KEYS */;

--
-- Dumping data for table `roles`
--
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`name`,`version`) VALUES 
 (1,'MasterAdmin',1),
 (2,'HumanResource',1),
 (3,'PageAdmin',1),
 (4,'Accountant',1),
 (5,'Employee',1);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`username`,`password`,`employeeId`,`roleId`,`version`) VALUES 
 (1,'John','welcome',1,1,1);
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
(1,1,500000,1,11),
(2,500001,1000000,2,11),
(3,1000001,1500000,3,11),
(4,1500001,2000000,4,11),
(5,2000001,3000000,5,11),
(6,3000001,4000000,6,11),
(7,4000001,6000000,7,11),
(8,6000001,8000000,9,11),
(9,8000001,10000000,11,11),
(10,10000001,15000000,13,11),
(11,15000001,20000000,15,11),
(12,20000001,20000001,20,11),
(13,1,1,35,13);
/*!40000 ALTER TABLE `taxrates` ENABLE KEYS */;

--
-- Dumping data for table taxdetails
--
/*!40000 ALTER TABLE `taxdetails` DISABLE KEYS */;
INSERT INTO `taxdetails` (`id`, `typeId`, `amount`) VALUES
(1,1,25),
(2,2,1.5),
(3,3,10000000),
(4,4,20),
(5,5,1440000),
(6,6,200000),
(7,7,300000),
(8,8,12.5),
(9,9,10),
(10,10,2.5),
(11,22,9300),
(12,23,5580);
/*!40000 ALTER TABLE `taxdetails` ENABLE KEYS */;

--
-- Dumping data for table paycycle
--
/*!40000 ALTER TABLE `paycycle` DISABLE KEYS */;
INSERT INTO `paycycle` (`id`, `name`, `selected`) VALUES
(1,'Monthly',3);
/*!40000 ALTER TABLE `paycycle` ENABLE KEYS */;

--
-- Dumping data for table payhum_config
--
/*!40000 ALTER TABLE `payhum_config` DISABLE KEYS */;
INSERT INTO `payhum_config` (`configName`, `configValue`) VALUES
('EMODE','MMODE'),
('PCOMP','0');
/*!40000 ALTER TABLE `paycycle` ENABLE KEYS */;
