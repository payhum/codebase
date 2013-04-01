-- Payhum Schema Creation file.

--
-- Create schema payhumrepo
--

CREATE DATABASE IF NOT EXISTS payhumrepo;
USE payhumrepo;

--
-- Temporary table structure for view `emp_benefit_view`
--
DROP TABLE IF EXISTS `emp_benefit_view`;
DROP VIEW IF EXISTS `emp_benefit_view`;
CREATE TABLE `emp_benefit_view` (
  `EMP_ID` varchar(45),
  `SALARY` double,
  `BENEFIT_AMNT` double,
  `BENEFIT_TYPE` varchar(45)
);

--
-- Definition of table `position`
--

DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `salary` double NOT NULL,
  `raisePerYear` double NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `middlename` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `sex` varchar(6) NOT NULL,
  `birthdate` datetime NOT NULL,
  `hiredate` datetime NOT NULL,
  `positionId` int(10) unsigned NOT NULL,
  `photo_path` varchar(255) NOT NULL,
  `status` varchar(20) NOT NULL,
  `version` int(11) NOT NULL default '1',
  `married` varchar(6) NOT NULL,
  `residentType` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_employee_position` (`positionId`),
  KEY `FK_employee_id` (`employeeId`),
  CONSTRAINT `FK_employee_position` FOREIGN KEY (`positionId`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Employee Payroll `emp_payroll_view`
--
DROP TABLE IF EXISTS `emp_payroll_view`;
CREATE TABLE `emp_payroll_view` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `FULL_NAME` varchar(137) NOT NULL,
  `GROSS_SALARY` double,
  `taxableIncome` double,
  `taxAmount` double,
  `totalIncome` double,
  `taxableOverseasIncome` double,
  `allowances` double,
  `baseSalary` double NOT NULL,
  `bonus` double,
  `accomodationAmount` double,
  `employerSS` double,
  `accomodationType` int(10) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_emp_payroll_emp` (`employeeId`),
  CONSTRAINT `FK_emp_payroll_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `benefitype`
--

DROP TABLE IF EXISTS `benefitype`;
CREATE TABLE `benefitype` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `cap` double NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `benefit`
--

DROP TABLE IF EXISTS `benefit`;
CREATE TABLE `benefit` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `typeId` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  `employeeId` int(10) unsigned NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_benefit_type` (`typeId`),
  KEY `FK_benefit_employee` (`employeeId`),
  CONSTRAINT `FK_benefit_employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_benefit_type` FOREIGN KEY (`typeId`) REFERENCES `benefitype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `deductiontype`
--

DROP TABLE IF EXISTS `deductiontype`;
CREATE TABLE `deductiontype` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `deduction`
--

DROP TABLE IF EXISTS `deduction`;
CREATE TABLE `deduction` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `deductionType` int(10) unsigned NOT NULL,
  `dueDate` varchar(45) NOT NULL,
  `done` tinyint(1) NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_dedecuction_type` (`deductionType`),
  KEY `FK_dedecuction_emp` (`employeeId`),
  CONSTRAINT `FK_dedecuction_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_dedecuction_type` FOREIGN KEY (`deductionType`) REFERENCES `deductiontype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `paygroup`
--

DROP TABLE IF EXISTS `paygroup`;
CREATE TABLE `paygroup` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `payRate` double NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `employeepaygroup`
--

DROP TABLE IF EXISTS `employeepaygroup`;
CREATE TABLE `employeepaygroup` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `payGroupId` int(10) unsigned NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_employeePayGroup_emp` (`employeeId`),
  KEY `FK_employeePayGroup_group` (`payGroupId`),
  CONSTRAINT `FK_employeePayGroup_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_employeePayGroup_group` FOREIGN KEY (`payGroupId`) REFERENCES `paygroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `leavetype`
--

DROP TABLE IF EXISTS `leavetype`;
CREATE TABLE `leavetype` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `day_cap` double NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `leaverequest`
--

DROP TABLE IF EXISTS `leaverequest`;
CREATE TABLE `leaverequest` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `leaveTypeId` int(10) unsigned NOT NULL,
  `leaveDate` datetime NOT NULL,
  `returnDate` datetime NOT NULL,
  `status` int(10) unsigned NOT NULL default '0',
  `noOfDays` int(10) unsigned NOT NULL,
  `description` text NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_leaverequest_employee` (`employeeId`),
  KEY `FK_leaverequest_leaveType` (`leaveTypeId`),
  CONSTRAINT `FK_leaverequest_employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_leaverequest_leaveType` FOREIGN KEY (`leaveTypeId`) REFERENCES `leavetype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `leaveapproval`
--

DROP TABLE IF EXISTS `leaveapproval`;
CREATE TABLE `leaveapproval` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `approverId` int(10) unsigned NOT NULL,
  `requestId` int(10) unsigned NOT NULL,
  `approvedbydate` datetime NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_leaveapproval_employee` (`approverId`),
  KEY `FK_leaveapproval_request` (`requestId`),
  CONSTRAINT `FK_leaveapproval_employee` FOREIGN KEY (`approverId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_leaveapproval_request` FOREIGN KEY (`requestId`) REFERENCES `leaverequest` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `template` varchar(45) NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `tbl_leave`
--

DROP TABLE IF EXISTS `tbl_leave`;
CREATE TABLE `tbl_leave` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `leaveType` int(10) unsigned NOT NULL,
  `unuseddays` int(10) unsigned NOT NULL,
  `employeeId` int(10) unsigned NOT NULL,
  `year` int(10) unsigned NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_leave_leavetype` USING BTREE (`leaveType`),
  KEY `FK_tbl_leave_employee` (`employeeId`),
  CONSTRAINT `FK_leave_leavetype` FOREIGN KEY (`leaveType`) REFERENCES `leavetype` (`id`),
  CONSTRAINT `FK_tbl_leave_employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `employeeId` int(10) unsigned NOT NULL,
  `roleId` int(10) unsigned NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_users_employeeId` (`employeeId`),
  KEY `FK_users_roles` (`roleId`),
  CONSTRAINT `FK_users_employeeId` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_users_roles` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `payroll`
--

DROP TABLE IF EXISTS `payroll`;
CREATE TABLE `payroll` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `runOnDate` datetime NOT NULL,
  `runBy` int(10) unsigned NOT NULL,
  `file` blob NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_payroll_run_by_user` (`runBy`),
  CONSTRAINT `FK_payroll_run_by_user` FOREIGN KEY (`runBy`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of view `emp_benefit_view`
--

DROP TABLE IF EXISTS `emp_benefit_view`;
DROP VIEW IF EXISTS `emp_benefit_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `payhumrepo`.`emp_benefit_view` AS select `payhumrepo`.`employee`.`employeeId` AS `EMP_ID`,`payhumrepo`.`position`.`salary` AS `SALARY`,`payhumrepo`.`benefit`.`amount` AS `BENEFIT_AMNT`,`payhumrepo`.`benefitype`.`name` AS `BENEFIT_TYPE` from (((`payhumrepo`.`employee` join `payhumrepo`.`position`) join `payhumrepo`.`benefit`) join `payhumrepo`.`benefitype`) where ((`payhumrepo`.`employee`.`positionId` = `payhumrepo`.`position`.`id`) and (`payhumrepo`.`employee`.`id` = `payhumrepo`.`benefit`.`employeeId`) and (`payhumrepo`.`benefit`.`typeId` = `payhumrepo`.`benefitype`.`id`));

--
-- New tables created
--
DROP TABLE IF EXISTS `deduction_decl`;
CREATE TABLE `deduction_decl` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_dedecuction_decl` (`employeeId`),
  CONSTRAINT `FK_dedecuction_decl` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `deduction_done`;
CREATE TABLE `deduction_done` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_deducdone_emp` (`employeeId`),
  CONSTRAINT `FK_deducdone_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `exemptions_done`;
CREATE TABLE `exemptions_done` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_exemptions_emp` (`employeeId`),
  CONSTRAINT `FK_exemptions_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `emp_dependents`;
CREATE TABLE `emp_dependents` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `age` int(10) unsigned NOT NULL,
  `occupationType` int(10) unsigned NOT NULL,
  `depType` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_dependents_emp` (`employeeId`),
  CONSTRAINT `FK_dependents_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


