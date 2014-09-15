package com.openhr.payroll;

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

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.company.CompanyPayroll;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.CompanyPayrollFactory;
import com.openhr.factories.ConfigDataFactory;


public class GovtFile extends Action {
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		ConfigData config = ConfigDataFactory.findByName(PayhumConstants.PROCESS_BRANCH); 
		int branchId = Integer.parseInt(config.getConfigValue());
		List<Branch> branches = BranchFactory.findById(branchId);
		
		Branch branch = null;
		if(branches != null && !branches.isEmpty()) {
			branch = branches.get(0);
		}
		List<Company> comps = CompanyFactory.findById(branch.getCompanyId().getId());
		Company comp = comps.get(0);
		String compName = comp.getName();
		compName = compName.replace(" ", "_");
		
		Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
		String monthYear = new SimpleDateFormat("MMM_yyyy").format(now);
		
		String fileName = "IRD_" + compName + "_" + branch.getName() + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// EmployeeName,EmpNationalID,TaxAmount
		Double totalAmt = 0D;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMyyyy");
		
		List<CompanyPayroll> compPayroll = CompanyPayrollFactory.findByCompAndProcessedDate(branch, cal.getTime());
		StringBuilder allEmpPayStr = new StringBuilder();
		int sectionCounter = 1;
		
		// Header details
		// Company Name, ABC
		// Branch Name, MAIN
		allEmpPayStr.append("Company Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(compName);
		allEmpPayStr.append("\n");
		allEmpPayStr.append("Branch Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(branch.getName());
		allEmpPayStr.append("\n");
		
		// Populate the header.
		allEmpPayStr.append("Section : Deposit request for Local and Resident Foreigner employees.\n");
	
		allEmpPayStr.append("Employee Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Department Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee National ID / Passport No");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Payroll Cycle");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Base Salary (MMK)");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Tax Amount (MMK)");
		allEmpPayStr.append("\n");
		
		for(CompanyPayroll compPay : compPayroll) {
			if(compPay.getTaxAmount() == 0D
			|| compPay.getResidentType().equalsIgnoreCase(PayhumConstants.NON_RESIDENT_FOREIGNER)) {
				continue;
			}
			
			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(compPay.getEmpFullName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getEmployeeId());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getDeptName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getEmpNationalID());
			empPayStr.append(COMMA);
			empPayStr.append(sdf.format(compPay.getProcessedDate()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(compPay.getBaseSalary()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(compPay.getTaxAmount()));
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
			
			totalAmt += compPay.getTaxAmount();
		}
		
		allEmpPayStr.append("\n,,,,,TOTAL:,");
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalAmt));
		
		// Non-resident - Populate the header.
		allEmpPayStr.append("\n\nSection : Tax Amount details for Non-Resident Foreigner employees.\n");
	
		allEmpPayStr.append("Employee Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Department Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee National ID / Passport No");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Payroll Cycle");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Base Salary (MMK)");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Tax Amount");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Currency");
		allEmpPayStr.append("\n");
		
		for(CompanyPayroll compPay : compPayroll) {
			if(compPay.getTaxAmount() == 0D
			|| compPay.getResidentType().equalsIgnoreCase(PayhumConstants.LOCAL)
			|| compPay.getResidentType().equalsIgnoreCase(PayhumConstants.RESIDENT_FOREIGNER)) {
				continue;
			}
			
			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(compPay.getEmpFullName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getEmployeeId());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getDeptName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getEmpNationalID());
			empPayStr.append(COMMA);
			empPayStr.append(sdf.format(compPay.getProcessedDate()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(compPay.getBaseSalary()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(compPay.getTaxAmount()));
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getCurrencySym());
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
			
			totalAmt += compPay.getTaxAmount();
		}
		
		allEmpPayStr.append("\n,,,,,TOTAL:,,");
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalAmt));
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin");
	}
		
	private boolean hasEmpWithThisResType(String resType,
			List<CompanyPayroll> compPayroll) {
		for(CompanyPayroll compPay : compPayroll) {
			if(compPay.getResidentType().equalsIgnoreCase(resType)) {
				return true;
			}
		}
		
		return false;
	}
}