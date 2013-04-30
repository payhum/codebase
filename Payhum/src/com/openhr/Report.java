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

import com.openhr.company.Company;
import com.openhr.company.LicenseValidator;
import com.openhr.company.Licenses;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;
import com.openhr.data.Users;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.EmpBankAccountFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LicenseFactory;
import com.openhr.factories.PayrollFactory;
import com.openhr.taxengine.TaxEngine;

public class Report extends Action {
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//TODO get logged in user, company, branch and dept details
		Users runBy = new Users();
		runBy.setId(2);
		
	    Calendar currDtCal = Calendar.getInstance();

	    // Zero out the hour, minute, second, and millisecond
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);

	    Date now = currDtCal.getTime();

		List<Company> comps = CompanyFactory.findAll();
		Company comp = comps.get(0);
		String compName = comp.getName();
		compName = compName.replace(" ", "_");
		
		// Check for licenses
		List<Licenses> compLicenses = LicenseFactory.findByCompanyId(comps.get(0).getId());
		for(Licenses lis : compLicenses) {
			if(lis.getActive() == 1) {
				Date endDate = lis.getTodate();
				if( ! isLicenseActive(now, endDate)) {
					// License has expired and throw an error
					throw new Exception("License has expired");
				} else {
					// License end date is valid, so lets check the key.
					String licenseKeyStr = LicenseValidator.formStringToEncrypt(compName, endDate);
					if(LicenseValidator.encryptAndCompare(licenseKeyStr, lis.getLicensekey())) {
						// License key is valid, so proceed.
						break;
					} else {
						throw new Exception("License is tampered. Contact Support.");
					}
				}
			}
		}
		
		// If the payroll run dates is empty, it means this is first time, so lets populate.
		populatePayrollDates();

		PayrollDate payrollDate = getTobeProcessedDate();
		Calendar salaryProcessDate = Calendar.getInstance();
		salaryProcessDate.setTime(payrollDate.getRunDate());
		List<Employee> activeEmpList = new ArrayList<Employee>();
		List<Employee> inActiveEmpList = new ArrayList<Employee>();
		
		// License validation is done and there is a active license, lets proceed and run the tax engine
		// to compute the payroll for the current pay period
		//TODO: Get dept ID
		Integer deptId = 0; // Means All depts
		Integer branchId = 0; // Means All branches
		
		if(branchId == 0) {
			// Employees of all Branches
			activeEmpList = EmployeeFactory.findAllActive();
			inActiveEmpList = EmployeeFactory.findInActiveByDate(salaryProcessDate.getTime());
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
		
		TaxEngine taxEngine = new TaxEngine(comp, activeEmpList, inActiveEmpList);
		List<EmployeePayroll> empPayrollList = taxEngine.execute(payroll);
		
		String monthYear = new SimpleDateFormat("MMM_yyyy").format(now);
		String fileName = compName + "_" + branchId  + "_"  + deptId + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// CompID,EmpID,EmpFullName,EmpNationalID,BankName,BankBranch,AccountNo,NetPay,TaxAmt,SS
		StringBuilder allEmpPayStr = new StringBuilder();
		for(EmployeePayroll empPay : empPayrollList) {
			EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPay.getEmployeeId().getId());
			EmpPayrollMap empPayrollMap = PayrollFactory.findEmpPayrollMapByEmpPayrollDate(empPay, payroll);

			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(empPay.getEmployeeId().getDeptId().getBranchId().getCompanyId().getId());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getId());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getFullName());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getEmpNationalID());
			empPayStr.append(COMMA);
			if(empPayrollMap.getMode() == 0) {
				empPayStr.append("Cash");
				empPayStr.append(COMMA);
				empPayStr.append("Cash");
				empPayStr.append(COMMA);
				empPayStr.append("Cash");
				empPayStr.append(COMMA);
			} else {
				empPayStr.append(empBankAcct.getBankName());
				empPayStr.append(COMMA);
				empPayStr.append(empBankAcct.getBankBranch());
				empPayStr.append(COMMA);
				empPayStr.append(empBankAcct.getAccountNo());
				empPayStr.append(COMMA);
			}
			empPayStr.append(new DecimalFormat("###.##").format(empPayrollMap.getNetPay()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(empPayrollMap.getTaxAmt()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(empPayrollMap.getSocialSec()));
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
		}
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("report.form");
	}
	
	private PayrollDate getTobeProcessedDate() throws Exception {
		List<PayrollDate> payDates = PayrollFactory.findAllPayrollDate();
		List<Payroll> payRuns = PayrollFactory.findAllPayrollRuns();
		
		for(PayrollDate payDate: payDates) {
			boolean processed = false;
			for(Payroll run: payRuns) {
				if(run.getPayDateId() == payDate) {
					processed = true;
					break;
				}
			}
			
			if(!processed) {
				// If this date is post the current date, then there is nothing pending, so it could be adhoc?
				Date currDate = new Date();
				if(currDate.after(payDate.getRunDate())
				|| currDate.equals(payDate.getRunDate())) {
					throw new Exception("All payroll dates are processed, should be an adhoc one.");
				}
				
				return payDate;
			}
		}
		
		return null;
	}

	public Report() {

	}
	
	private boolean isLicenseActive(Date currentDate, Date endDate) {
	    if (currentDate.after(endDate)) {
	    	// License has expired
	    	return false;
	    }
	    
	    return true;
	}
	
	private void populatePayrollDates() throws Exception {
		// Check if the payroll dates are calculated or not, if not default to Monthly and choose last friday of
		// each month as payroll date.
		List<PayrollDate> payrollDates = PayrollFactory.findAllPayrollDate();
		if(payrollDates == null || payrollDates.isEmpty()) {
			Calendar currCal = Calendar.getInstance();
			int currMonth = currCal.get(Calendar.MONTH);
			int currYear = currCal.get(Calendar.YEAR);
			
			if(currMonth >= 3) {
				for(int i = currMonth; i < 12; i++) {
					Date rDate = getLastFriday(i, currYear);
					PayrollFactory.insertPayrollDate(rDate);
				}
			} else {
				for(int i = 0; i <= currMonth; i++) {
					Date rDate = getLastFriday(i, currYear + 1);
					PayrollFactory.insertPayrollDate(rDate);
				}
			}
		}
	}
	
	private Date getLastFriday( int month, int year ) {
		Calendar retCal = Calendar.getInstance();
		retCal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
		retCal.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		return retCal.getTime();
	}
}