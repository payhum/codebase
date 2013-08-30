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
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.CompanyPayrollFactory;
import com.openhr.factories.ConfigDataFactory;

public class BankFile extends Action {
	
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
		
		String fileName = "Bank_" + compName + "_" + branch.getName() + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// CompanyName, Branch Name, EmployeeName, Emp ID, Emp National ID, Payroll Cycle, NetPay,BankName,Branch,Routing No,AccountNumber,NetPay
		// Total Tax Amount : XXX
		// Total Social Security Amount : XXX		
		Double totalTaxAmt = 0D;
		Double totalEmprSSAmt = 0D;
		Double totalEmpeSSAmt = 0D;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMyyyy");
		
		List<CompanyPayroll> compPayroll = CompanyPayrollFactory.findByCompAndProcessedDate(branch, cal.getTime());
		String[] supportedCurrencies = new String [4];
		supportedCurrencies[0] = PayhumConstants.CURRENCY_MMK;
		supportedCurrencies[1] = PayhumConstants.CURRENCY_USD;
		supportedCurrencies[2] = PayhumConstants.CURRENCY_EURO;
		supportedCurrencies[3] = PayhumConstants.CURRENCY_POUND;
		
		StringBuilder allEmpPayStr = new StringBuilder();
		int sectionCounter = 1;
		
		for(int i = 0; i < supportedCurrencies.length; i++) {
			if(hasEmpWithThisCurrency(supportedCurrencies[i], compPayroll)) {
				// Populate the header.
				allEmpPayStr.append("Section " + sectionCounter++ + ": Deposit request for employees in " + supportedCurrencies[i] + " .\n");
				allEmpPayStr.append("Company Name");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Branch Name");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Employee Name");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Employee ID");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Employee National ID / Passport No");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Bank Name");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Bank Branch");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Routing Number");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Account Number");
				allEmpPayStr.append(COMMA);
				allEmpPayStr.append("Payroll Cycle");
				allEmpPayStr.append(COMMA);

				//TODO VJ
				/*
				allEmpPayStr.append("Total Income");
				allEmpPayStr.append(COMMA);

				allEmpPayStr.append("Total Deductions");
				allEmpPayStr.append(COMMA);

				allEmpPayStr.append("Taxable Income");
				allEmpPayStr.append(COMMA);
				
				allEmpPayStr.append("Tax Amount");
				allEmpPayStr.append(COMMA);
				*/
				//TODO VJ
				
				allEmpPayStr.append("Net Pay(" + supportedCurrencies[i] + ")");
				allEmpPayStr.append("\n");
				
				//TODO VJ
				//List<EmployeePayroll> empPayrolls = EmpPayTaxFactroy.findAllEmpPayroll();
				//TODO VJ
				
				for(CompanyPayroll compPay : compPayroll) {
					if(compPay.getBankName().equals("-")) {
						// Its Employee who is paid by cash, include it in separate file.
						continue;
					}
					
					if(compPay.getCurrencySym().equalsIgnoreCase(supportedCurrencies[i])) {
						StringBuilder empPayStr = new StringBuilder();
						empPayStr.append(comp.getName());
						empPayStr.append(COMMA);
						empPayStr.append(branch.getName());
						empPayStr.append(COMMA);			
						empPayStr.append(compPay.getEmpFullName());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getEmployeeId());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getEmpNationalID());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getBankName());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getBankBranch());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getRoutingNo());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getAccountNo());
						empPayStr.append(COMMA);
						empPayStr.append(sdf.format(compPay.getProcessedDate()));
						empPayStr.append(COMMA);
						
						//TODO VJ
						// Here emp id is int, but this is the string id ..need to fix this as well.
						/*EmployeePayroll empP = getEmpPayroll(empPayrolls, compPay.getEmployeeId());
						
						empPayStr.append(empP.getTotalIncome() / 6);
						empPayStr.append(COMMA);
						
						empPayStr.append(empP.getTotalDeductions() / 6);
						empPayStr.append(COMMA);
						
						empPayStr.append(empP.getTaxableIncome() / 6);
						empPayStr.append(COMMA);
						
						empPayStr.append(empP.getTaxAmount() / 6);
						empPayStr.append(COMMA);
						*/
						//TODO VJ
						
						empPayStr.append(new DecimalFormat("###.##").format(compPay.getNetPay()));
						empPayStr.append("\n");
						
						totalTaxAmt += compPay.getTaxAmount();
						totalEmprSSAmt += compPay.getEmprSocialSec();
						totalEmpeSSAmt += compPay.getEmpeSocialSec();
						
						allEmpPayStr.append(empPayStr);
					}
				}
			}
		}
		
		// Capture the tax, SS from the emps who do not have bank accounts
		for(CompanyPayroll compPay : compPayroll) {
			if(compPay.getBankName().equals("-")) {
				totalTaxAmt += compPay.getTaxAmount();
				totalEmprSSAmt += compPay.getEmprSocialSec();
				totalEmpeSSAmt += compPay.getEmpeSocialSec();
			}
		}
		
		allEmpPayStr.append("\nSection " + sectionCounter++ + ": Deposit request for total tax amount for specific pay cycle.\n");
		allEmpPayStr.append("Company Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Company ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Department");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Payroll Cycle");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Amount(MMK)");
		allEmpPayStr.append("\n");
		allEmpPayStr.append(comp.getName());
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(comp.getId());
		allEmpPayStr.append(COMMA);			
		allEmpPayStr.append("Internal Revenue Department");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(sdf.format(compPayroll.get(0).getProcessedDate()));
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalTaxAmt));
		allEmpPayStr.append("\n");
		
		allEmpPayStr.append("\nSection " + sectionCounter++ + ": Deposit request for total SS amount for specific pay cycle.\n");
		allEmpPayStr.append("Company Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Company ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Department");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Payroll Cycle");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employer SS Amount(MMK)");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee SS Amount(MMK)");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Total SS Amount(MMK)");
		allEmpPayStr.append("\n");
		allEmpPayStr.append(comp.getName());
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(comp.getId());
		allEmpPayStr.append(COMMA);			
		allEmpPayStr.append("Social Security");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(sdf.format(compPayroll.get(0).getProcessedDate()));
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalEmprSSAmt));
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalEmpeSSAmt));
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalEmpeSSAmt + totalEmprSSAmt));
		allEmpPayStr.append("\n");
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin");
	}

	//TODO VJ
	private EmployeePayroll getEmpPayroll(List<EmployeePayroll> empPayrolls,
			Integer employeeId) {
		String empId = Integer.toString(employeeId);
		for(EmployeePayroll emp: empPayrolls) {
			if(emp.getEmployeeId().getEmployeeId().equalsIgnoreCase(empId)) {
				return emp;
			}
		}
		return null;
	}

	private boolean hasEmpWithThisCurrency(String currency,
			List<CompanyPayroll> compPayroll) {
		for(CompanyPayroll compPay : compPayroll) {
			if(compPay.getCurrencySym().equalsIgnoreCase(currency)) {
				return true;
			}
		}
		
		return false;
	}
}