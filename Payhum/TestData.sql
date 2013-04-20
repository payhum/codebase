-- This file inserts dummy data into the repos
--

USE payhumrepo;

--
-- Dumping data for table `benefitype`
--

/*!40000 ALTER TABLE `benefitype` DISABLE KEYS */;
INSERT INTO `benefitype` (`id`,`name`,`cap`,`version`) VALUES 
 (3,'Transport Allowance',599,1),
 (4,'House Allowance',1500,1),
 (5,'Cash Indeminty Allowance',200,1),
 (6,'fuel',1500,1);
/*!40000 ALTER TABLE `benefitype` ENABLE KEYS */;

--
-- Dumping data for table `position`
--

/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` (`id`,`name`,`salary`,`raisePerYear`,`version`) VALUES 
 (1,'SOFTWARE ENGINEER',12000,1500,1),
 (2,'System Administrator',5000,1200,1),
 (3,'Senior Accountant',5000,1000,1),
 (4,'Managerial Constultant',14000,2000,1),
 (5,'Senior Accountant',5000,1000,1),
 (6,'aa',3455,1234,1);
/*!40000 ALTER TABLE `position` ENABLE KEYS */;

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`,`companyId`,`name`,`address`) VALUES 
 (1,'C1',"ABC Solutions","#10, 5th Avenue, XYZ-400 200"),
 (2,'C2',"DEF LLC","#20, 10th Main, PQR-400 200");
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

--
-- Dumping data for table `employee`
--

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`companyId`,`empNationalID`) VALUES 
 (2,'MODETH-0002','JOHN','B','WIN','Male','1980-06-19 00:00:00','2011-06-14 00:00:00',4,'/data/photo/185145_481927465161666_2082997324_n.jpg','ACTIVE',1,'true',0,1,'N1'),
 (3,'MODETH-0001','Desta','Abebe','Muluken','Male','1987-01-05 00:00:00','2012-07-02 00:00:00',3,'/data/photo/418446_185246901607959_1591185693_n.jpg','IN ACTIVE',1,'true','0',1,'N2'),
 (4,'MODETH-0004','AMAN','DEKSISO','DEKSISO','Male','1980-09-06 00:00:00','2012-07-04 00:00:00',4,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',0,1,'N3'),
 (5,'ECOMEX-0005','New','Employee','Name','Male','2000-02-01 00:00:00','2012-07-09 00:00:00',2,'/data/photo/Photo-0002.jpg','ACTIVE',1,'true',0,1,'N4'),
 (6,'ETHMTN-0006','Abebe','Abebe','Abebe','Male','1994-06-14 00:00:00','2012-07-09 00:00:00',5,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',0,1,'N5'),
 (7,'EEPCO-0007','James','Smith','Mailman','Male','1989-05-16 00:00:00','2012-07-16 00:00:00',5,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'true',0,1,'N6'),
 (8,'HOF-0008','Haile','Gebre','Silasie','Male','1980-06-10 00:00:00','2012-07-16 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1,1,'N7');
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`,`companyId`,`empNationalID`) VALUES 
 (12,'MODETH-0012','Anderson','Neyo','Mick','Female','1980-08-01 00:00:00','2012-08-01 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1,1,'N8'),
 (13,'MODETH-0013','Abera','Girma','Lemma','Female','2012-08-02 00:00:00','2012-08-02 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1,1,'N9'),
 (14,'MODETH-0014','James','Gasolin','Chant','Male','1940-08-05 23:44:45','2012-08-06 00:00:00',2,'employees.bmp','ACTIVE',1,'true',1,1,'N10'),
 (15,'MODETH-0015','asdfasd','fasdf','asdfadsf','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',1,'/data/photo/Koala.jpg','ACTIVE',1,'true',1,1,'N11'),
 (16,'MODETH-0016','asdf','dsdd','ddd','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1,1,'N12'),
 (17,'MODETH-0017','ssxdfere','adfa','zxcavdfadsf','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',1,'/data/photo/Tulips.jpg','ACTIVE',1,'true',1,1,'N13'),
 (18,'MODETH-0018','Asdfs','asdf','asdfwerwer','Male','1989-02-07 00:00:00','2012-08-10 00:00:00',1,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'true',2,1,'N14'),
 (19,'MODETH-0019','Mesay1','Solomon','Solomon','Male','1980-01-01 00:00:00','2012-08-10 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',2,1,'N15'),
 (20,'MODETH-0020','Biruk','Abebe','Degu','Male','1975-02-02 00:00:00','2012-08-10 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',0,1,'N16');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

--
-- Dumping data for table `benefit`
--

/*!40000 ALTER TABLE `benefit` DISABLE KEYS */;
INSERT INTO `benefit` (`id`,`typeId`,`amount`,`employeeId`,`version`) VALUES 
 (1,5,1500,3,1),
 (2,5,1000,2,1),
 (3,4,700,3,1);
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
-- Dumping data for table `tbl_leave`
--

/*!40000 ALTER TABLE `tbl_leave` DISABLE KEYS */;
INSERT INTO `tbl_leave` (`id`,`leaveType`,`unuseddays`,`employeeId`,`year`,`version`) VALUES 
 (5,4,10,3,2012,1);
/*!40000 ALTER TABLE `tbl_leave` ENABLE KEYS */;

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
INSERT INTO `emp_payroll_view` (`id`, `employeeId`, `FULL_NAME`, `baseSalary`, `accomodationType`,`bonus`,`allowances`) VALUES
 (1,2,'JohnBWin',30000000,0,4000000,50000);
INSERT INTO `emp_payroll_view` (`id`, `employeeId`, `FULL_NAME`, `baseSalary`, `accomodationType`) VALUES
 (2,3,'Desta',2000000,0),
 (3,4,'AMAN_DEKSISO',3000000,1),
 (4,5,'New_Employee',2500000,1),
 (5,6,'Abebe',1500000,1),
 (6,7,'JamesSmith',40000000,1),
 (7,8,'HaileGebre',3500000,0),
 (8,12,'AndersonNeyo',2346777555,0),
 (9,13,'AberaGirma',33300000,0),
 (10,14,'JamesGasolin',45000000,1),
 (11,15,'asdfasd',1000000,1),
 (12,16,'asdf', 200000,0),
 (13,17,'ssxdfere',12312312,1),
 (14,18,'Asdfs',45643545,0),
 (15,19,'Mesay1Solomon',12312311,1),
 (16,20,'BirukAbebe',1000000,1);
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
(1,2,'Bob',5,0,1),
(2,2,'Mary',15,0,1),
(3,2,'Boe',9,0,1),
(4,2,'Wily',35,1,0);
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
INSERT INTO `taxrates` (`id`, `income_from`, `income_to`,`income_percnt`,`version`) VALUES
(1,1,1000,1,1),
(2,1001,2000,2,1),
(3,2001,-1,3,1);
/*!40000 ALTER TABLE `taxrates` ENABLE KEYS */;
