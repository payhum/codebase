package com.openhr;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.EmpBankAccountFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LicenseFactory;
import com.openhr.taxengine.TaxEngine;

public class Report extends Action {
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
					// License enddate is valid, so lets check the key.
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
		
		// License validation is done and there is a active license, lets proceed and run the tax engine
		// to compute the payroll for the current pay period
		List<Employee> empList = EmployeeFactory.findByCompanyID(comp.getId());
		TaxEngine taxEngine = new TaxEngine(comp, empList);
		taxEngine.execute();
		
		String monthYear = new SimpleDateFormat("MMM_yyyy").format(now);
		String fileName = compName + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// CompID,EmpID,EmpFullName,EmpNationalID,BankName,BankBranch,AccountNo,TaxAmount,NetPay
		List<EmployeePayroll> empPayrollList = EmpPayTaxFactroy.findAllEmpPayroll();
		
		StringBuilder allEmpPayStr = new StringBuilder();
		for(EmployeePayroll empPay : empPayrollList) {
			EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPay.getEmployeeId().getId());
			
			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(empPay.getEmployeeId().getCompanyId().getId());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getId());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getFullName());
			empPayStr.append(COMMA);
			empPayStr.append(empPay.getEmployeeId().getEmpNationalID());
			empPayStr.append(COMMA);
			empPayStr.append(empBankAcct.getBankName());
			empPayStr.append(COMMA);
			empPayStr.append(empBankAcct.getBankBranch());
			empPayStr.append(COMMA);
			empPayStr.append(empBankAcct.getAccountNo());
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(empPay.getTaxAmount() / 12));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(empPay.getNetPay() / 12));
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
		}
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("report.form");
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
}