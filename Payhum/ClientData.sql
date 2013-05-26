-- This file inserts pre-populated data into the repos
-- for Client Module

USE payhumrepo;
--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`companyId`,`name`) VALUES 
 (2,'C-0001','Comp2');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

--
-- Dumping data for table `branch`
--

/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` (`id`,`name`,`address`,`companyId`) VALUES 
 (2,'Main','2nd Address', 2);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;

--
-- Dumping data for table `department`
--
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`,`deptname`,`branchId`) VALUES 
 (2,'Sales',2);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

--
-- Dumping data for table licenses
--
/*!40000 ALTER TABLE `licenses` DISABLE KEYS */;
INSERT INTO `licenses` (`id`, `fromdate`, `todate`, `companyId`,`active`,`licensekey`) VALUES
(1,'2013-05-01 00:00:00','2013-06-30 00:00:00',2,1,'10-119-87-37-99-76');
/*!40000 ALTER TABLE `licenses` ENABLE KEYS */;

--
-- Dumping data for table `employee`
--
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`deptId`,`empNationalID`,`emerContactName`,`emerContactNo`,`address`,`phoneNo`,`currency`) VALUES 
 (2,'CLI-0001','JOHN','B','WIN','Male','1980-06-19 00:00:00','2011-06-14 00:00:00',1,'/data/photo/185145_481927465161666_2082997324_n.jpg','ACTIVE',1,'true',11,2,'N1','A','1','address1','123456',24);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

--
-- Dumping data for table `benefit`
--

/*!40000 ALTER TABLE `benefit` DISABLE KEYS */;
INSERT INTO `benefit` (`id`,`typeId`,`amount`,`employeeId`) VALUES 
 (1,1,50000,2);
/*!40000 ALTER TABLE `benefit` ENABLE KEYS */;

--
-- Dumping data for table emp_payroll_view
--
/*!40000 ALTER TABLE `emp_payroll` DISABLE KEYS */;
INSERT INTO `emp_payroll` (`id`, `employeeId`, `FULL_NAME`, `accomodationType`) VALUES
 (2,2,'JohnBWin',14);
/*!40000 ALTER TABLE `emp_payroll` ENABLE KEYS */;

--
-- Dumping data for table employee_account
--
/*!40000 ALTER TABLE `employee_account` DISABLE KEYS */;
INSERT INTO `employee_account` (`id`, `employeeId`, `bankName`, `bankBranch`,`accountNo`,`routingNo`) VALUES
 (1,2,'First Bank','J Colony','12345','4567');
/*!40000 ALTER TABLE `employee_account` ENABLE KEYS */;

--
-- Dumping data for table emp_dependents
--
/*!40000 ALTER TABLE `emp_dependents` DISABLE KEYS */;
INSERT INTO `emp_dependents` (`id`, `employeeId`, `name`, `age`, `occupationType`, `depType`) VALUES
(1,2,'Bob',5,17,21),
(2,2,'Mary',15,17,21),
(3,2,'Boe',9,17,21),
(4,2,'Wily',35,19,20);
/*!40000 ALTER TABLE `emp_dependents` ENABLE KEYS */;

--
-- Dumping data for table emp_salary
--
/*!40000 ALTER TABLE `emp_salary` DISABLE KEYS */;
INSERT INTO `emp_salary` (`id`, `employeeId`, `fromdate`,`todate`,`basesalary`) VALUES
(1,2,'2013-04-01 00:00:00','2013-04-01 00:00:00',30000000);
/*!40000 ALTER TABLE `emp_salary` ENABLE KEYS */;

--
-- Dumping data for table emp_bonus
--
/*!40000 ALTER TABLE `emp_bonus` DISABLE KEYS */;
INSERT INTO `emp_bonus` (`id`, `employeeId`, `givendate`,`amount`) VALUES
(1,2,'2013-05-01 00:00:00',4000000);
/*!40000 ALTER TABLE `emp_bonus` ENABLE KEYS */;
