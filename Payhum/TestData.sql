-- This file inserts dummy data into the repos
--

USE payhumrepo;

--
-- Dumping data for table `benefitype`
--

/*!40000 ALTER TABLE `benefitype` DISABLE KEYS */;
INSERT INTO `benefitype` (`id`,`name`,`cap`) VALUES 
 (1,'Transport Allowance',599),
 (2,'House Allowance',1500),
 (3,'Cash Indeminty Allowance',200),
 (4,'fuel',1500);
/*!40000 ALTER TABLE `benefitype` ENABLE KEYS */;

--
-- Dumping data for table `position`
--

/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` (`id`,`name`,`lowsal`,`highsal`) VALUES 
 (1,'Software Engineer',50000,1500000),
 (2,'System Administrator',50000,120000),
 (3,'Senior Accountant',50000,100000),
 (4,'Managerial Constultant',50000,200000),
 (5,'Senior Accountant',50000,1000000);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`companyId`,`name`) VALUES 
 (1,'C1',"ABC Solutions"),
 (2,'C2',"DEF LLC");
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

--
-- Dumping data for table `branch`
--

/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` (`id`,`name`,`address`,`companyId`) VALUES 
 (1,'MAIN',"#10, XYZ Road, Avenue Park", 1),
 (2,'MAIN',"#20, ADF Marg", 2);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;

--
-- Dumping data for table `department`
--
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`id`,`deptname`,`branchId`) VALUES 
 (1,'Finance',1),
 (2,'Sales',1);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;

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
(8,'Fully Furnished Accomodation','Employer sponsored. Percentage of Gross Salary added as additional income','TAXDETAILS'),
(9,'Not Furnished Accomodation','Employer sponsored. Percentage of Gross Salary added as additional income','TAXDETAILS'),
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
(21,'Child','Child','DEPENDENTTYPE');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;

--
-- Dumping data for table `employee`
--
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`deptId`,`empNationalID`) VALUES 
 (2,'MODETH-0002','JOHN','B','WIN','Male','1980-06-19 00:00:00','2011-06-14 00:00:00',4,'/data/photo/185145_481927465161666_2082997324_n.jpg','ACTIVE',1,'true',11,1,'N1'),
 (3,'MODETH-0001','Desta','Abebe','Muluken','Male','1987-01-05 00:00:00','2012-07-02 00:00:00',3,'/data/photo/418446_185246901607959_1591185693_n.jpg','IN ACTIVE',1,'true',11,1,'N2'),
 (4,'MODETH-0004','AMAN','DEKSISO','DEKSISO','Male','1980-09-06 00:00:00','2012-07-04 00:00:00',4,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',11,1,'N3'),
 (5,'ECOMEX-0005','New','Employee','Name','Male','2000-02-01 00:00:00','2012-07-09 00:00:00',2,'/data/photo/Photo-0002.jpg','ACTIVE',1,'true',11,1,'N4'),
 (6,'ETHMTN-0006','Abebe','Abebe','Abebe','Male','1994-06-14 00:00:00','2012-07-09 00:00:00',5,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',11,1,'N5'),
 (7,'EEPCO-0007','James','Smith','Mailman','Male','1989-05-16 00:00:00','2012-07-16 00:00:00',5,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'true',11,1,'N6'),
 (8,'HOF-0008','Haile','Gebre','Silasie','Male','1980-06-10 00:00:00','2012-07-16 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',11,1,'N7');
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`deptId`,`empNationalID`) VALUES 
 (12,'MODETH-0012','Anderson','Neyo','Mick','Female','1980-08-01 00:00:00','2012-08-01 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',12,1,'N8'),
 (13,'MODETH-0013','Abera','Girma','Lemma','Female','2012-08-02 00:00:00','2012-08-02 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',12,1,'N9'),
 (14,'MODETH-0014','James','Gasolin','Chant','Male','1940-08-05 23:44:45','2012-08-06 00:00:00',2,'employees.bmp','ACTIVE',1,'true',12,1,'N10'),
 (15,'MODETH-0015','asdfasd','fasdf','asdfadsf','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',1,'/data/photo/Koala.jpg','ACTIVE',1,'true',12,1,'N11'),
 (16,'MODETH-0016','asdf','dsdd','ddd','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',12,1,'N12'),
 (17,'MODETH-0017','ssxdfere','adfa','zxcavdfadsf','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',1,'/data/photo/Tulips.jpg','ACTIVE',1,'true',12,1,'N13'),
 (18,'MODETH-0018','Asdfs','asdf','asdfwerwer','Male','1989-02-07 00:00:00','2012-08-10 00:00:00',1,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'true',13,1,'N14'),
 (19,'MODETH-0019','Mesay1','Solomon','Solomon','Male','1980-01-01 00:00:00','2012-08-10 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',13,1,'N15'),
 (20,'MODETH-0020','Biruk','Abebe','Degu','Male','1975-02-02 00:00:00','2012-08-10 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',11,1,'N16');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

--
-- Dumping data for table `benefit`
--

/*!40000 ALTER TABLE `benefit` DISABLE KEYS */;
INSERT INTO `benefit` (`id`,`typeId`,`amount`,`employeeId`) VALUES 
 (1,3,1500,3),
 (2,3,50000,2),
 (3,4,700,3);
/*!40000 ALTER TABLE `benefit` ENABLE KEYS */;

--
-- Dumping data for table `leavetype`
--

/*!40000 ALTER TABLE `leavetype` DISABLE KEYS */;
INSERT INTO `leavetype` (`id`,`name`,`day_cap`,`version`) VALUES 
 (1,'Sick Leave',9,1),
 (2,'Maternity Leave',90,1),
 (3,'Leave of Absence',6,1),
 (4,'Paternity Leave',10,1);
/*!40000 ALTER TABLE `leavetype` ENABLE KEYS */;

--
-- Dumping data for table `leaverequest`
--

/*!40000 ALTER TABLE `leaverequest` DISABLE KEYS */;
INSERT INTO `leaverequest` (`id`,`employeeId`,`leaveTypeId`,`leaveDate`,`returnDate`,`status`,`noOfDays`,`description`,`version`) VALUES 
 (1,3,3,'2012-08-22 16:22:59','2012-08-22 16:22:59',0,4,'hhhh',1);
/*!40000 ALTER TABLE `leaverequest` ENABLE KEYS */;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`name`,`version`) VALUES 
 (1,'Administration',1),
 (2,'Development',1),
 (3,'Finance',1),
 (4,'Leave Management',1),
 (5,'Leave Management1',1),
 (6,'Sales',1),
 (7,'PR',1),
 (8,'MasterAdmin',1);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

--
-- Dumping data for table `users`
--

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`,`username`,`password`,`employeeId`,`roleId`,`version`) VALUES 
 (1,'John','welcome',2,2,1),
 (2,'Administrator','123456',3,1,1),
 (3,'Mule','mule',18,7,1),
 (4,'Abebe','password',6,7,1),
 (5,'Madmin','welcome',3,8,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

--
-- Dumping data for table emp_payroll_view
--
/*!40000 ALTER TABLE `emp_payroll_view` DISABLE KEYS */;
INSERT INTO `emp_payroll_view` (`id`, `employeeId`, `FULL_NAME`, `accomodationType`) VALUES
 (1,2,'JohnBWin',14),
 (2,3,'Desta',14),
 (3,4,'AMAN_DEKSISO',15),
 (4,5,'New_Employee',15),
 (5,6,'Abebe',15),
 (6,7,'JamesSmith',15),
 (7,8,'HaileGebre',14),
 (8,12,'AndersonNeyo',14),
 (9,13,'AberaGirma',14),
 (10,14,'JamesGasolin',15),
 (11,15,'asdfasd',15),
 (12,16,'asdf', 14),
 (13,17,'ssxdfere',15),
 (14,18,'Asdfs',14),
 (15,19,'Mesay1Solomon',15),
 (16,20,'BirukAbebe',15);
/*!40000 ALTER TABLE `emp_payroll_view` ENABLE KEYS */;


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
-- Dumping data for table deduction_done
--
/*!40000 ALTER TABLE `deduction_done` DISABLE KEYS */;
INSERT INTO `deduction_done` (`id`, `payrollId`, `type`, `amount`) VALUES
(1,1,2,50000),
(2,1,4,10000),
(3,1,5,10000);
/*!40000 ALTER TABLE `deduction_done` ENABLE KEYS */;

--
-- Dumping data for table exemptions_done
--
/*!40000 ALTER TABLE `exemptions_done` DISABLE KEYS */;
INSERT INTO `exemptions_done` (`id`, `payrollId`, `type`, `amount`) VALUES
(1,1,1,20000),
(2,1,2,10000),
(3,1,3,10000);
/*!40000 ALTER TABLE `exemptions_done` ENABLE KEYS */;

--
-- Dumping data for table licenses
--
/*!40000 ALTER TABLE `licenses` DISABLE KEYS */;
INSERT INTO `licenses` (`id`, `fromdate`, `todate`, `companyId`,`active`,`licensekey`) VALUES
(1,'2012-08-22 16:22:59','2013-08-22 16:22:59',1,1,'123'),
(2,'2012-08-22 16:22:59','2014-08-22 16:22:59',2,1,'123');
/*!40000 ALTER TABLE `licenses` ENABLE KEYS */;

--
-- Dumping data for table employee_account
--
/*!40000 ALTER TABLE `employee_account` DISABLE KEYS */;
INSERT INTO `employee_account` (`id`, `employeeId`, `bankName`, `bankBranch`,`accountNo`) VALUES
 (1,2,'First Bank','J Colony','12345'),
 (2,3,'Yes Bank','Chruch Street','53421'),
 (3,4,'Citi Bank','Marks Road','00921312'),
 (4,5,'First Bank','J Colony','12345'),
 (5,6,'First Bank','J Colony','3244'),
 (6,7,'First Bank','J Colony','234'),
 (7,8,'First Bank','J Colony','34534'),
 (8,12,'First Bank','J Colony','45645'),
 (9,13,'First Bank','J Colony','2342'),
 (10,14,'First Bank','J Colony','45654'),
 (11,15,'First Bank','J Colony','231'),
 (12,16,'First Bank','J Colony','2342'),
 (13,17,'First Bank','J Colony','12312'),
 (14,18,'First Bank','J Colony','23424'),
 (15,19,'First Bank','J Colony','45456'),
 (16,20,'Yes Bank','J Colony','43534');
/*!40000 ALTER TABLE `employee_account` ENABLE KEYS */;

--
-- Dumping data for table deductions_decl
--
/*!40000 ALTER TABLE `deduction_decl` DISABLE KEYS */;
INSERT INTO `deduction_decl` (`id`, `payrollId`, `type`, `amount`,`description`) VALUES
(1,1,1,50000,'test1'),
(2,1,2,10000,'test2');
/*!40000 ALTER TABLE `deduction_decl` ENABLE KEYS */;


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
(1,1,1000,1,11),
(2,1001,2000,2,11),
(3,2001,-1,3,11),
(4,1,-1,35,13);
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
(10,10,2.5);
/*!40000 ALTER TABLE `taxdetails` ENABLE KEYS */;


--
-- Dumping data for table emp_salary
--
/*!40000 ALTER TABLE `emp_salary` DISABLE KEYS */;
INSERT INTO `emp_salary` (`id`, `employeeId`, `fromdate`,`basesalary`) VALUES
(1,2,'2013-04-01 00:00:00',30000000),
(2,3,'2013-04-01 00:00:00',2000000),
(3,4,'2013-04-01 00:00:00',3000000),
(4,5,'2013-04-01 00:00:00',2500000),
(5,6,'2013-04-01 00:00:00',1500000),
(6,7,'2013-04-01 00:00:00',40000000),
(7,8,'2013-04-01 00:00:00',3500000),
(8,12,'2013-04-01 00:00:00',2346777555),
(9,13,'2013-04-01 00:00:00',33300000),
(10,14,'2013-04-01 00:00:00',45000000),
(11,15,'2013-04-01 00:00:00',1000000),
(12,16,'2013-04-01 00:00:00', 200000),
(13,17,'2013-04-01 00:00:00',12312312),
(14,18,'2013-04-01 00:00:00',45643545),
(15,19,'2013-04-01 00:00:00',12312311),
(16,20,'2013-04-01 00:00:00',1000000);
/*!40000 ALTER TABLE `emp_salary` ENABLE KEYS */;

--
-- Dumping data for table emp_bonus
--
/*!40000 ALTER TABLE `emp_bonus` DISABLE KEYS */;
INSERT INTO `emp_bonus` (`id`, `employeeId`, `givendate`,`amount`) VALUES
(1,2,'2013-04-01 00:00:00',4000000);
/*!40000 ALTER TABLE `emp_bonus` ENABLE KEYS */;
