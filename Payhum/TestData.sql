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
-- Dumping data for table `employee`
--

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`) VALUES 
 (2,'MODETH-0002','JOHN','B','WIN','Male','1980-06-19 00:00:00','2011-06-14 00:00:00',4,'/data/photo/185145_481927465161666_2082997324_n.jpg','ACTIVE',1,'true',0),
 (3,'MODETH-0001','Desta','Abebe','Muluken','Male','1987-01-05 00:00:00','2012-07-02 00:00:00',3,'/data/photo/418446_185246901607959_1591185693_n.jpg','IN ACTIVE',1,'true','0'),
 (4,'MODETH-0004','AMAN','DEKSISO','DEKSISO','Male','1980-09-06 00:00:00','2012-07-04 00:00:00',4,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',0),
 (5,'ECOMEX-0005','New','Employee','Name','Male','2000-02-01 00:00:00','2012-07-09 00:00:00',2,'/data/photo/Photo-0002.jpg','ACTIVE',1,'true',0),
 (6,'ETHMTN-0006','Abebe','Abebe','Abebe','Male','1994-06-14 00:00:00','2012-07-09 00:00:00',5,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',0),
 (7,'EEPCO-0007','James','Smith','Mailman','Male','1989-05-16 00:00:00','2012-07-16 00:00:00',5,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'true',0),
 (8,'HOF-0008','Haile','Gebre','Silasie','Male','1980-06-10 00:00:00','2012-07-16 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1);
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`) VALUES 
 (12,'MODETH-0012','Anderson','Neyo','Mick','Female','1980-08-01 00:00:00','2012-08-01 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1),
 (13,'MODETH-0013','Abera','Girma','Lemma','Female','2012-08-02 00:00:00','2012-08-02 00:00:00',1,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1),
 (14,'MODETH-0014','James','Gasolin','Chant','Male','1940-08-05 23:44:45','2012-08-06 00:00:00',2,'employees.bmp','ACTIVE',1,'true',1),
 (15,'MODETH-0015','asdfasd','fasdf','asdfadsf','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',1,'/data/photo/Koala.jpg','ACTIVE',1,'true',1),
 (16,'MODETH-0016','asdf','dsdd','ddd','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',1),
 (17,'MODETH-0017','ssxdfere','adfa','zxcavdfadsf','Male','2012-08-09 00:00:00','2012-08-09 00:00:00',1,'/data/photo/Tulips.jpg','ACTIVE',1,'true',1),
 (18,'MODETH-0018','Asdfs','asdf','asdfwerwer','Male','1989-02-07 00:00:00','2012-08-10 00:00:00',1,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'true',2),
 (19,'MODETH-0019','Mesay1','Solomon','Solomon','Male','1980-01-01 00:00:00','2012-08-10 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',2);
INSERT INTO `employee` (`id`,`employeeId`,`firstname`,`middlename`,`lastname`,`sex`,`birthdate`,`hiredate`,`positionId`,`photo_path`,`status`,`version`,`married`,`residentType`) VALUES 
 (20,'MODETH-0020','Biruk','Abebe','Degu','Male','1975-02-02 00:00:00','2012-08-10 00:00:00',2,'/data/photo/placeholder-pic.png','ACTIVE',1,'true',0),
 (21,'MODETH-0021','xxsd','wer','qwewqe','Male','2012-08-11 00:00:00','2012-08-11 00:00:00',2,'employees.bmp','ACTIVE',1,'true',0),
 (22,'MODETH-0022','James','Smith','Joshwa','Male','2012-08-20 00:00:00','2012-08-20 00:00:00',1,'movie-poster.jpg','ACTIVE',1,'true',0),
 (23,'MODETH-0023','Selam','Haile','Mule','Male','1960-01-28 00:00:00','2012-09-13 00:00:00',5,'Penguins.jpg','ACTIVE',1,'false',1),
 (24,'MODETH-0024','Mikias','Mikias','Mikias','Male','1969-10-14 00:00:00','2012-09-25 00:00:00',6,'/data/photo/placeholder-pic.png','IN ACTIVE',1,'false',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

--
-- Dumping data for table `benefit`
--

/*!40000 ALTER TABLE `benefit` DISABLE KEYS */;
INSERT INTO `benefit` (`id`,`typeId`,`amount`,`employeeId`,`version`) VALUES 
 (1,5,1500,3,1),
 (2,5,1000,2,1),
 (3,4,700,3,1),
 (4,4,1500,24,1);
/*!40000 ALTER TABLE `benefit` ENABLE KEYS */;

--
-- Dumping data for table `leavetype`
--

/*!40000 ALTER TABLE `leavetype` DISABLE KEYS */;
INSERT INTO `leavetype` (`id`,`name`,`day_cap`,`version`) VALUES 
 (1,'Sick Leave',9,1),
 (2,'Maternity Leave',90,1),
 (3,'Leave of Absence',6,1),
 (4,'Maternity Father\'s Leave',10,1);
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
 (7,'PR',1);
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
 (4,'Abebe','password',6,7,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

--
-- Dumping data for table emp_payroll_view
--
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `emp_payroll_view` (`id`, `employeeId`, `FULL_NAME`, `baseSalary`, `accomodationType`) VALUES
(1,2,'JohnBWin',2000000,1),
(2,3,'Desta',2000000,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

