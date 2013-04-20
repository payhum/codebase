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
-- Definition of table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `companyId` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `address` varchar(90) NOT NULL,
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
  `residentType` int(11) NOT NULL default '0',
  `companyId` int(10) unsigned NOT NULL,
  `empNationalID` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_employee_position` (`positionId`),
  KEY `FK_employee_id` (`employeeId`),
  KEY `FK_employee_company` (`companyId`),
  CONSTRAINT `FK_employee_position` FOREIGN KEY (`positionId`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_employee_company` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Employee Payroll `emp_payroll_view`
--
DROP TABLE IF EXISTS `emp_payroll_view`;
CREATE TABLE `emp_payroll_view` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `FULL_NAME` varchar(137) NOT NULL,
  `GROSS_SALARY` double default 0,
  `taxableIncome` double default 0,
  `taxAmount` double default 0,
  `totalIncome` double default 0,
  `taxableOverseasIncome` double default 0,
  `allowances` double default 0,
  `baseSalary` double NOT NULL,
  `bonus` double default 0,
  `accomodationAmount` double default 0,
  `employerSS` double default 0,
  `accomodationType` int(10) NOT NULL,
  `netPay` double default 0,
  `totalDeductions` double default 0,
  `overtimeamt` double default 0,
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
-- Definition of table `exemptionstype`
--
DROP TABLE IF EXISTS `exemptionstype`;
CREATE TABLE `exemptionstype` (
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
-- Definition of table `deduction_decl`
--
DROP TABLE IF EXISTS `deduction_decl`;
CREATE TABLE `deduction_decl` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `payrollId` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(256),
  PRIMARY KEY  (`id`),
  KEY `FK_deduction_decl` (`payrollId`),
  KEY `FK_deduction_type` (`type`),
  CONSTRAINT `FK_deduction_decl` FOREIGN KEY (`payrollId`) REFERENCES `emp_payroll_view` (`id`),
  CONSTRAINT `FK_deduction_type` FOREIGN KEY (`type`) REFERENCES `deductiontype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `deduction_done`
--
DROP TABLE IF EXISTS `deduction_done`;
CREATE TABLE `deduction_done` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `payrollId` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_deducdone_emp` (`payrollId`),
  KEY `FK_deducdone_type` (`type`),
  CONSTRAINT `FK_deducdone_emp` FOREIGN KEY (`payrollId`) REFERENCES `emp_payroll_view` (`id`),
  CONSTRAINT `FK_deducdone_type` FOREIGN KEY (`type`) REFERENCES `deductiontype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `exemptions_done`
--

DROP TABLE IF EXISTS `exemptions_done`;
CREATE TABLE `exemptions_done` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `payrollId` int(10) unsigned NOT NULL,
  `type` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_exemptions_emp` (`payrollId`),
  KEY `FK_exemptions_type` (`type`),
  CONSTRAINT `FK_exemptions_emp` FOREIGN KEY (`payrollId`) REFERENCES `emp_payroll_view` (`id`),
  CONSTRAINT `FK_exemptions_type` FOREIGN KEY (`type`) REFERENCES `exemptionstype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `emp_dependents`
--

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


--
-- Definition of table `licenses`
--

DROP TABLE IF EXISTS `licenses`;
CREATE TABLE `licenses` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `fromdate` datetime NOT NULL,
  `todate` datetime NOT NULL,
  `companyId` int(10) unsigned NOT NULL,
  `active` int(2) NOT NULL,
  `licensekey` varchar(256) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_licenses_comp` (`companyId`),
  CONSTRAINT `FK_licenses_comp` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `company_payroll`
-- 
DROP TABLE IF EXISTS `company_payroll`;
CREATE TABLE `company_payroll` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `companyId` int(10) unsigned NOT NULL,
  `processedDate` datetime NOT NULL,
  `employeeId` int(10) unsigned NOT NULL,
  `empFullName` varchar(60) NOT NULL,
  `empNationalID` varchar(45) NOT NULL, 
  `taxAmount` double NOT NULL,
  `netPay` double NOT NULL,
  `bankName` varchar(45) NOT NULL,
  `bankBranch` varchar(45) NOT NULL,
  `accountNo` varchar(45) NOT NULL,
  `month` int(2) unsigned NOT NULL,
  `year` int(4) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_comppay_comp` (`companyId`),
  CONSTRAINT `FK_comppay_comp` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `employee_account`
-- 
DROP TABLE IF EXISTS `employee_account`;
CREATE TABLE `employee_account` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `bankName` varchar(45) NOT NULL,
  `bankBranch` varchar(45) NOT NULL,
  `accountNo` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_empacc_emp` (`employeeId`),
  CONSTRAINT `FK_empacc_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `glemployee`
-- 
DROP TABLE IF EXISTS `glemployee`;
CREATE TABLE glemployee
(
  `gl_id` int(10)  NOT NULL auto_increment,
  `acc_no` int(30) NOT NULL,
  `accnt_name` varchar(70),
  `employeeId` int(10) unsigned NOT NULL,
  `debit`  double,
  `credit` double,
  `date` datetime,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY (`gl_id`),
  KEY `FK_glemployee_employee` (`employeeId`),
  CONSTRAINT `FK_glemployee_employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `holidays`
-- 
DROP TABLE IF EXISTS `holidays`;
CREATE TABLE holidays
(
  `id` int(10)  NOT NULL auto_increment,
  `date` datetime NOT NULL,
  `name` varchar(70),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `paycycle`
-- 
DROP TABLE IF EXISTS `paycycle`;
CREATE TABLE paycycle
(
  `id` int(10)  NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `selected` int (2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `overtime_payrate`
-- 
DROP TABLE IF EXISTS `overtime_payrate`;
create table `overtime_payrate`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `day_group` varchar(30) NOT NULL,
  `grosspay_percent` double NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `overtime`
-- 
DROP TABLE IF EXISTS `overtime`;
create table `overtime`
(
  `id` int (10) unsigned NOT NULL auto_increment,
  `overtimedate` datetime NOT NULL,
  `noofhours` double NOT NULL,
  `status` int (2) NOT NULL,
  `employeeid` int(10) unsigned NOT NULL,
  `approvedby` varchar(45),
  `approveddate` datetime,
  PRIMARY KEY  (`id`),
  KEY `FK_overtime_employee` (`employeeId`),
  CONSTRAINT `FK_overtime_employee` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `overtime`
-- 
DROP TABLE IF EXISTS `taxrates`;
create table `taxrates`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `income_from` double NOT NULL,
  `income_to` double NOT NULL,
  `income_percnt` double NOT NULL,
  `version` int(11) NOT NULL default '1',
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
