-- Payhum Schema Creation file.

--
-- Create schema payhumrepo
--

CREATE DATABASE IF NOT EXISTS payhummaster;
USE payhummaster;

--
-- Definition of table `position`
--

DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `lowsal` double NOT NULL,
  `highsal` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `company`
--
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `companyId` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `branch`
--
DROP TABLE IF EXISTS `branch`;
CREATE TABLE `branch` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `companyId` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_branch_company` (`companyId`),
  CONSTRAINT `FK_branch_company` FOREIGN KEY (`companyId`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `department`
--
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `deptname` varchar(45) NOT NULL,
  `branchId` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_dept_branch` (`branchId`),
  CONSTRAINT `FK_dept_branch` FOREIGN KEY (`branchId`) REFERENCES `branch` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `types`
-- 
DROP TABLE IF EXISTS `types`;
create table `types`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(90) NOT NULL,
  `desc` varchar(256) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `inactiveDate` datetime,
  `positionId` int(10) unsigned NOT NULL,
  `photo_path` varchar(255) NOT NULL,
  `status` varchar(20) NOT NULL,
  `version` int(11) NOT NULL default '1',
  `married` varchar(6) NOT NULL,
  `residentType` int(10) unsigned NOT NULL,
  `deptId` int(10) unsigned NOT NULL,
  `empNationalID` varchar(45) NOT NULL,
  `emerContactName` varchar(45) NOT NULL,
  `emerContactNo` varchar(45) NOT NULL,
  `address` varchar(90) NOT NULL,
  `phoneNo` varchar(15) NOT NULL,
  `nationality` varchar(45),
  `ppNumber` varchar(15),
  `ppExpDate` datetime,
  `ppIssuePlace` varchar(45),
  `currency` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_employee_position` (`positionId`),
  KEY `FK_employee_id` (`employeeId`),
  KEY `FK_employee_dept` (`deptId`),
  KEY `FK_employee_res` (`residentType`),
  KEY `FK_employee_curr` (`currency`),
  CONSTRAINT `FK_employee_position` FOREIGN KEY (`positionId`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_employee_dept` FOREIGN KEY (`deptId`) REFERENCES `department` (`id`),
  CONSTRAINT `FK_employee_res` FOREIGN KEY (`residentType`) REFERENCES `types` (`id`),
  CONSTRAINT `FK_employee_curr` FOREIGN KEY (`currency`) REFERENCES `types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Employee Payroll `emp_payroll`
--
DROP TABLE IF EXISTS `emp_payroll`;
CREATE TABLE `emp_payroll` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `FULL_NAME` varchar(137) NOT NULL,
  `GROSS_SALARY` double default 0,
  `taxableIncome` double default 0,
  `taxAmount` double default 0,
  `totalIncome` double default 0,
  `taxableOverseasIncome` double default 0,
  `allowances` double default 0,
  `baseSalary` double default 0,
  `bonus` double default 0,
  `accomodationAmount` double default 0,
  `employerSS` double default 0,
  `accomodationType` int(10) unsigned NOT NULL,
  `netPay` double default 0,
  `totalDeductions` double default 0,
  `overtimeamt` double default 0,
  `paidTaxAmt` double default 0,
  `paidNetPay` double default 0,
  `paidSS` double default 0,
  `otherIncome` double default 0,
  `leaveLoss` double default 0,
  `taxPaidByEmployer` int(2) unsigned NOT NULL default 0,
  `withholdTax` int(2) unsigned NOT NULL default 1,
  `withholdSS` int(2) unsigned NOT NULL default 1,
  PRIMARY KEY  (`id`),
  KEY `FK_emp_payroll_emp` (`employeeId`),
  KEY `FK_emp_payroll_acc` (`accomodationType`),
  CONSTRAINT `FK_emp_payroll_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_emp_payroll_acc` FOREIGN KEY (`accomodationType`) REFERENCES `types` (`id`)
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
-- Definition of table `leavetype`
--

DROP TABLE IF EXISTS `leavetype`;
CREATE TABLE `leavetype` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `day_cap` int NOT NULL,
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
  CONSTRAINT `FK_deduction_decl` FOREIGN KEY (`payrollId`) REFERENCES `emp_payroll` (`id`),
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
  CONSTRAINT `FK_deducdone_emp` FOREIGN KEY (`payrollId`) REFERENCES `emp_payroll` (`id`),
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
  CONSTRAINT `FK_exemptions_emp` FOREIGN KEY (`payrollId`) REFERENCES `emp_payroll` (`id`),
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
  KEY `FK_dependents_occ` (`occupationType`),
  KEY `FK_dependents_type` (`depType`),
  CONSTRAINT `FK_dependents_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_dependents_occ` FOREIGN KEY (`occupationType`) REFERENCES `types` (`id`),
  CONSTRAINT `FK_dependents_type` FOREIGN KEY (`depType`) REFERENCES `types` (`id`)
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
  `socialSec` double NOT NULL,
  `bankName` varchar(45) NOT NULL,
  `bankBranch` varchar(45) NOT NULL,
  `routingNo` varchar(45) NOT NULL,
  `accountNo` varchar(45) NOT NULL,
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
  `routingNo` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_empacc_emp` (`employeeId`),
  CONSTRAINT `FK_empacc_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `glemployee`
-- 
DROP TABLE IF EXISTS `glemployee`;
CREATE TABLE `glemployee`
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
CREATE TABLE `holidays`
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
CREATE TABLE `paycycle`
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
-- Definition of table `taxrates`
-- 
DROP TABLE IF EXISTS `taxrates`;
create table `taxrates`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `income_from` double NOT NULL,
  `income_to` double NOT NULL,
  `income_percent` double NOT NULL,
  `residentTypeId` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_taxrates_type` (`residentTypeId`),
  CONSTRAINT `FK_taxrates_type` FOREIGN KEY (`residentTypeId`) REFERENCES `types` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `taxdetails`
-- 
DROP TABLE IF EXISTS `taxdetails`;
create table `taxdetails`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `typeId` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_taxdetails_type` (`typeId`),
  CONSTRAINT `FK_taxdetails_type` FOREIGN KEY (`typeId`) REFERENCES `types` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `emp_salary`
-- 
DROP TABLE IF EXISTS `emp_salary`;
create table `emp_salary`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `fromdate` datetime NOT NULL,
  `todate` datetime,
  `basesalary` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_empsal_emp` (`employeeId`),
  CONSTRAINT `FK_empsal_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `emp_bonus`
-- 
DROP TABLE IF EXISTS `emp_bonus`;
create table `emp_bonus`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `employeeId` int(10) unsigned NOT NULL,
  `givendate` datetime NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_empbonus_emp` (`employeeId`),
  CONSTRAINT `FK_empbonus_emp` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `payroll_date`
--
DROP TABLE IF EXISTS `payroll_date`;
CREATE TABLE `payroll_date` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `runDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `payroll`
--
DROP TABLE IF EXISTS `payroll`;
CREATE TABLE `payroll` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `runOnDate` datetime NOT NULL,
  `runBy` int(10) unsigned NOT NULL,
  `payDateId` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_payroll_paydate`(`payDateId`),
  KEY `FK_payroll_run_by_user`(`runBy`),
  CONSTRAINT `FK_payroll_run_by_user` FOREIGN KEY (`runBy`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_payroll_paydate` FOREIGN KEY (`payDateId`) REFERENCES `payroll_date` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Definition of table `emp_payroll_map`
--
DROP TABLE IF EXISTS `emp_payroll_map`;
CREATE TABLE `emp_payroll_map` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `emppayId` int(10) unsigned NOT NULL,
  `payrollId` int(10) unsigned NOT NULL,
  `taxAmount` double NOT NUll,
  `netPay` double NOT NUll,
  `socialSec` double NOT NUll,
  `mode` int(2) NOT NULL default 0,
  PRIMARY KEY  (`id`),
  KEY `FK_emppaymap_emp` (`emppayId`),
  KEY `FK_emppaymap_payroll` (`payrollId`),
  CONSTRAINT `FK_emppaymap_emp` FOREIGN KEY (`emppayId`) REFERENCES `emp_payroll` (`id`),
  CONSTRAINT `FK_emppaymap_payroll` FOREIGN KEY (`payrollId`) REFERENCES `payroll` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Definition of table `payhum_config`
--
DROP TABLE IF EXISTS `payhum_config`;
CREATE TABLE `payhum_config` (
  `configName` varchar(45) NOT NULL,
  `configValue` varchar(45) NOT NULL,
  PRIMARY KEY  (`configName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- 
-- Creating Triggers and tables for Audit Trail
--

--
-- Table to capture the audit trail for emp_payroll
--
DROP TABLE IF EXISTS `audit_emp_payroll`;
CREATE TABLE `audit_emp_payroll` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `ref_ep_id` int(10) unsigned NOT NULL ,
  `employeeId` int(10) unsigned NOT NULL,
  `FULL_NAME` varchar(137) NOT NULL,
  `GROSS_SALARY` double default 0,
  `taxableIncome` double default 0,
  `taxAmount` double default 0,
  `totalIncome` double default 0,
  `taxableOverseasIncome` double default 0,
  `allowances` double default 0,
  `baseSalary` double default 0,
  `bonus` double default 0,
  `accomodationAmount` double default 0,
  `employerSS` double default 0,
  `accomodationType` int(10) unsigned NOT NULL,
  `netPay` double default 0,
  `totalDeductions` double default 0,
  `overtimeamt` double default 0,
  `paidTaxAmt` double default 0,
  `paidNetPay` double default 0,
  `paidSS` double default 0,
  `otherIncome` double default 0,
  `leaveLoss` double default 0,
  `taxPaidByEmployer` int(2) unsigned NOT NULL default 0,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table to capture the audit trail for employee
--
DROP TABLE IF EXISTS `audit_employee`;
CREATE TABLE `audit_employee` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `ref_emp_id` int(10) unsigned NOT NULL ,
  `employeeId` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `middlename` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `sex` varchar(6) NOT NULL,
  `birthdate` datetime NOT NULL,
  `hiredate` datetime NOT NULL,
  `inactiveDate` datetime,
  `positionId` int(10) unsigned NOT NULL,
  `photo_path` varchar(255) NOT NULL,
  `status` varchar(20) NOT NULL,
  `version` int(11) NOT NULL default '1',
  `married` varchar(6) NOT NULL,
  `residentType` int(10) unsigned NOT NULL,
  `deptId` int(10) unsigned NOT NULL,
  `empNationalID` varchar(45) NOT NULL,
  `emerContactName` varchar(45) NOT NULL,
  `emerContactNo` varchar(45) NOT NULL,
  `address` varchar(90) NOT NULL,
  `phoneNo` varchar(15) NOT NULL,
  `nationality` varchar(45),
  `ppNumber` varchar(15),
  `ppExpDate` datetime,
  `ppIssuePlace` varchar(45),
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table to capture the audit trail for types
-- 
DROP TABLE IF EXISTS `audit_types`;
create table `audit_types`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `ref_types_id` int(10) unsigned NOT NULL ,
  `name` varchar(45) NOT NULL,
  `desc` varchar(256) NOT NULL,
  `type` varchar(45) NOT NULL,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table to capture the audit trail for taxrates
-- 
DROP TABLE IF EXISTS `audit_taxrates`;
create table `audit_taxrates`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `ref_tr_id` int(10) unsigned NOT NULL ,
  `income_from` double NOT NULL,
  `income_to` double NOT NULL,
  `income_percent` double NOT NULL,
  `residentTypeId` int(10) unsigned NOT NULL,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table to capture the audit trail for taxdetails
-- 
DROP TABLE IF EXISTS `audit_taxdetails`;
create table `audit_taxdetails`
(
  `id` int(10) unsigned NOT NULL auto_increment,
  `ref_td_id` int(10) unsigned NOT NULL,
  `typeId` int(10) unsigned NOT NULL,
  `amount` double NOT NULL,
  `updatedDate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


delimiter $$
CREATE TRIGGER audit_emp_payroll_entry BEFORE UPDATE ON emp_payroll
  FOR EACH ROW BEGIN
	INSERT INTO audit_emp_payroll 
	( `ref_ep_id`, `employeeId`, `FULL_NAME`, `GROSS_SALARY`, `taxableIncome`,
	  `taxAmount`, `totalIncome`, `taxableOverseasIncome`, `allowances`, `baseSalary`,
	  `bonus`, `accomodationAmount`, `employerSS`, `accomodationType`, `netPay`,
	  `totalDeductions`, `overtimeamt`, `paidTaxAmt`, `paidNetPay`, `paidSS`,
	  `otherIncome`, `leaveLoss`, `taxPaidByEmployer`, `updatedDate`)
	VALUES(OLD.id, OLD.employeeId, OLD.FULL_NAME, OLD.GROSS_SALARY, OLD.taxableIncome,
	  OLD.taxAmount, OLD.totalIncome, OLD.taxableOverseasIncome, OLD.allowances, OLD.baseSalary,
	  OLD.bonus, OLD.accomodationAmount, OLD.employerSS, OLD.accomodationType, OLD.netPay,
	  OLD.totalDeductions, OLD.overtimeamt, OLD.paidTaxAmt, OLD.paidNetPay, OLD.paidSS,
	  OLD.otherIncome, OLD.leaveLoss, OLD.taxPaidByEmployer, NOW());
  END;
$$

delimiter $$
CREATE TRIGGER audit_employee_entry BEFORE UPDATE ON employee
  FOR EACH ROW BEGIN
	INSERT INTO audit_employee
	( `ref_emp_id`, `employeeId`, `firstname`, `middlename`, `lastname`, `sex`, `birthdate`,
	  `hiredate`, `inactiveDate`, `positionId`, `photo_path`, `status`, `version`, `married`,
      `residentType`, `deptId`, `empNationalID`, `emerContactName`, `emerContactNo`, `address`,
	  `phoneNo`, `nationality`, `ppNumber`, `ppExpDate`, `ppIssuePlace`, `currency`, `updatedDate`)
	VALUES(OLD.id, OLD.employeeId, OLD.firstname, OLD.middlename, OLD.lastname, OLD.sex, OLD.birthdate,
	  OLD.hiredate, OLD.inactiveDate, OLD.positionId, OLD.photo_path, OLD.status, OLD.version, OLD.married,
      OLD.residentType, OLD.deptId, OLD.empNationalID, OLD.emerContactName, OLD.emerContactNo, OLD.address,
	  OLD.phoneNo, OLD.nationality, OLD.ppNumber, OLD.ppExpDate, OLD.currency, OLD.ppIssuePlace, NOW());
  END;
$$

delimiter $$
CREATE TRIGGER audit_types_empty  BEFORE UPDATE ON types
  FOR EACH ROW BEGIN
	INSERT INTO audit_types
	( `ref_types_id`, `name`, `desc`, `type`, `updatedDate`)
	VALUES(OLD.id, OLD.name, OLD.desc, OLD.type, NOW());
  END;
$$


delimiter $$
CREATE TRIGGER audit_taxrates_empty  BEFORE UPDATE ON taxrates
  FOR EACH ROW BEGIN
	INSERT INTO audit_taxrates
	( `ref_tr_id`, `income_from`, `income_to`, `income_percent`, `residentTypeId`, `updatedDate`)
	VALUES(OLD.id, OLD.income_from, OLD.income_to, OLD.income_percent, OLD.residentTypeId, NOW());
  END;
$$


delimiter $$
CREATE TRIGGER audit_taxdetails_empty  BEFORE UPDATE ON taxdetails
  FOR EACH ROW BEGIN
	INSERT INTO audit_taxdetails
	( `ref_td_id`, `typeId`, `amount`, `updatedDate`)
	VALUES(OLD.id, OLD.typeId, OLD.amount, NOW());
  END;
$$
