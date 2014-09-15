-- This file inserts pre-populated data into the repos
-- for Client Module

USE payhumrepo;

delete from emp_payroll_map;

delete from payroll;

delete from payroll_date;

delete from deduction;

delete from leaveapproval;

delete from leaverequest;

delete from deduction_done;

delete from deduction_decl;

delete from exemptions_done;

delete from company_payroll;

delete from overtime;

delete from emp_bonus;

UPDATE emp_payroll SET 
	GROSS_SALARY = 0,
    taxableIncome = 0,
    taxAmount = 0,
    totalIncome = 0,
    taxableOverseasIncome = 0,
    allowances = 0,
    baseSalary = 0,
    bonus = 0,
    accomodationAmount = 0,
    employerSS = 0,
    netPay = 0,
    totalDeductions = 0,
    overtimeamt = 0,
    paidTaxAmt = 0,
    paidNetPay = 0,
    paidEmpeSS = 0,
    paidEmprSS = 0,
    otherIncome = 0,
    leaveLoss = 0,
    retroBaseSal = 0,
    newOtherIncome = 0,
    newOvertimeAmt = 0,
    newBonus = 0,
    paidBaseSalary = 0,
	paidTaxableAmt = 0,
	paidAccomAmt = 0,
	paidBasicAllow = 0,
	paidLeaveLoss = 0,
	paidAllowance = 0;
