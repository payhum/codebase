package com.openhr;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;
import com.openhr.data.TypesData;
import com.openhr.data.Users;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.EmpBankAccountFactory;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.PayrollFactory;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.TaxEngine;
import com.util.payhumpackages.PayhumUtil;

public class Report extends Action {
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//TODO get logged in user
		Users runBy = new Users();
		runBy.setId(1);
		
	    Calendar currDtCal = Calendar.getInstance();

	    // Zero out the hour, minute, second, and millisecond
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);

	    Date now = currDtCal.getTime();

	    ConfigData userComp = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP); 
		Integer compId = Integer.parseInt(userComp.getConfigValue());
		
		List<Company> comps = CompanyFactory.findById(compId);
		Company comp = null;
		if(comps != null && !comps.isEmpty()) {
			comp = comps.get(0);
		}
		
		String compName = comp.getName();
		compName = compName.replace(" ", "_");

		List<Employee> activeEmpList = new ArrayList<Employee>();
		List<Employee> inActiveEmpList = new ArrayList<Employee>();
		
		// License validation is done and there is a active license, lets proceed and run the tax engine
		// to compute the payroll for the current pay period
		//TODO: Get dept ID
		Integer deptId = 0; // Means All depts
		
		ConfigData config = ConfigDataFactory.findByName(PayhumConstants.PROCESS_BRANCH); 
		Integer branchId = Integer.parseInt(config.getConfigValue());
		
		List<Branch> branches = BranchFactory.findById(branchId);
		
		boolean adhoc = false;
		PayrollDate payrollDate = null;
		try {
			payrollDate = getTobeProcessedDate(now, branches.get(0));
		}
		catch(Exception e) {
			// Its an adhoc one.
			adhoc = true;
			payrollDate = new PayrollDate();
			payrollDate.setRunDate(now);
			
			PayrollFactory.insertPayrollDate(payrollDate);
		}
		Calendar salaryProcessDate = Calendar.getInstance();
		salaryProcessDate.setTime(payrollDate.getRunDateofDateObject());
		
		if(branchId == 0) {
			// Employees of all Branches
			activeEmpList = EmployeeFactory.findAllActive(compId);
			inActiveEmpList = EmployeeFactory.findInActiveByDate(salaryProcessDate.getTime(), compId);
		} else {
			if(deptId == 0) {
				// Employees of all depts of a given branch.
				activeEmpList = EmployeeFactory.findAllActiveByBranch(branchId);
				inActiveEmpList = EmployeeFactory.findInActiveByDateAndBranch(salaryProcessDate.getTime(), branchId);
			} else {
				// Particular dept of a particular branch
				activeEmpList = EmployeeFactory.findActiveByDeptID(deptId);
				inActiveEmpList = EmployeeFactory.findInActiveByDeptIDAndDate(deptId, salaryProcessDate.getTime());
			}
		}
		

		Payroll payroll = new Payroll();
		payroll.setRunOnDate(salaryProcessDate.getTime());
		payroll.setRunBy(runBy);
		payroll.setPayDateId(payrollDate);
		payroll.setBranchId(branches.get(0));
		PayrollFactory.insertPayroll(payroll);
		
		TaxEngine taxEngine = new TaxEngine(comp, branches.get(0), activeEmpList, inActiveEmpList);
		List<EmployeePayroll> empPayrollList = taxEngine.execute(payroll, adhoc);
		
		String monthYear = new SimpleDateFormat("MMM_yyyy").format(now);
		String fileName = compName + "_" + branchId  + "_"  + deptId + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// CompID,BranchName,EmpID,EmpFullName,EmpNationalID,DeptName,BankName,BankBranch,RoutingNo,AccountNo,NetPay,currency,residenttype,TaxAmt,emprSS,empess,basesalary
		StringBuilder allEmpPayStr = new StringBuilder();
		for(EmployeePayroll empPay : empPayrollList) {
			EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPay.getEmployeeId().getId());
			EmpPayrollMap empPayrollMap = PayrollFactory.findEmpPayrollMapByEmpPayrollDate(empPay, payroll);

			if(empPayrollMap == null) {
				// Payroll is not processed for this emp, skip it.
				continue;
			}
			
			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(empPay.getEmployeeId().getDeptId().getBranchId().getCompanyId().getId());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getDeptId().getBranchId().getName());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getEmployeeId());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getFullName());
			empPayStr.append(COMMA);
			if(empPay.getEmployeeId().getResidentType().getName().equalsIgnoreCase(PayhumConstants.LOCAL)) {
				empPayStr.append(empPay.getEmployeeId().getEmpNationalID());
			} else {
				empPayStr.append(empPay.getEmployeeId().getPpNumber());
			}
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getDeptId().getDeptname());
			empPayStr.append(COMMA);
			if(empPayrollMap.getMode().compareTo(0) == 0) {
				empPayStr.append("-");
				empPayStr.append(COMMA);
				empPayStr.append("-");
				empPayStr.append(COMMA);
				empPayStr.append("-");
				empPayStr.append(COMMA);
				empPayStr.append("-");
				empPayStr.append(COMMA);
			} else {
				empPayStr.append(empBankAcct.getBankName());
				empPayStr.append(COMMA);
				empPayStr.append(empBankAcct.getBankBranch());
				empPayStr.append(COMMA);
				empPayStr.append(empBankAcct.getRoutingNo());
				empPayStr.append(COMMA);
				empPayStr.append(empBankAcct.getAccountNo());
				empPayStr.append(COMMA);
			}
			
			empPayStr.append(new DecimalFormat("###.##").format(getAmountInRespectiveCurrency(empPay.getEmployeeId().getCurrency(), 
																			   empPayrollMap.getNetPay())));
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getCurrency().getName());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getResidentType().getName());
			empPayStr.append(COMMA);
			
			if(empPay.getWithholdTax().compareTo(1) == 0) {
				if(empPay.getEmployeeId().getResidentType().getName().equalsIgnoreCase(PayhumConstants.NON_RESIDENT_FOREIGNER)) {
					empPayStr.append(new DecimalFormat("###.##").format(getAmountInRespectiveCurrency(empPay.getEmployeeId().getCurrency(), 
							empPayrollMap.getTaxAmount())));	
				} else {
					empPayStr.append(new DecimalFormat("###.##").format(empPayrollMap.getTaxAmount()));	
				}
			} else {
				empPayStr.append("0.00");
			}
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(getAmountInRespectiveCurrency(empPay.getEmployeeId().getCurrency(), empPayrollMap.getEmprSocialSec())));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(getAmountInRespectiveCurrency(empPay.getEmployeeId().getCurrency(), empPayrollMap.getEmpeSocialSec())));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(getAmountInRespectiveCurrency(empPay.getEmployeeId().getCurrency(), empPayrollMap.getBaseSalary())));
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
		}
		
		ConfigData configpay = ConfigDataFactory.findByName(PayhumConstants.PAYROLLDATE_ID); 
		configpay.setConfigValue(Integer.toString(payrollDate.getId()));
		ConfigDataFactory.update(configpay);
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("report.form");
	}
	
	private PayrollDate getTobeProcessedDate(Date currDate, Branch branch) throws Exception {
		List<PayrollDate> payDates =  PayrollFactory.findPayrollDateByBranch(branch.getId());
		List<Payroll> payRuns = PayrollFactory.findAllPayrollRuns();
		
		for(PayrollDate payDate: payDates) {
			boolean processed = false;
			for(Payroll run: payRuns) {
				if(run.getPayDateId() == payDate
				&& branch.getId().compareTo(run.getBranchId().getId()) == 0) {
					processed = true;
					break;
				}
			}
			
			if(!processed) {
				// If this date is post the current date, but its not processed, lets process it.
				if(currDate.after(payDate.getRunDateofDateObject())
				|| currDate.equals(payDate.getRunDateofDateObject())) {
					return payDate;
				} else {
					// Current date is before the next processing date, so its an adhoc
					throw new Exception("All payroll dates are processed, should be an adhoc one.");
				}
			}
		}
		
		return null;
	}

	private Double getAmountInRespectiveCurrency(TypesData currency, Double amount) {
		Double conversionRate = 1D;
		
		if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.USD_MMK_CONVER);
			conversionRate = Double.valueOf(currencyConver.getConfigValue());
		} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_EURO)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.EURO_MMK_CONVER);
			conversionRate = Double.valueOf(currencyConver.getConfigValue());
		} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_POUND)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.POUND_MMK_CONVER);
			conversionRate = Double.valueOf(currencyConver.getConfigValue());
		} 
		
		return amount / conversionRate;
	}
	
	public Report() {

	}
}