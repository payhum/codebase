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
import com.openhr.data.ConfigData;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.CompanyPayrollFactory;
import com.openhr.factories.ConfigDataFactory;

public class BankFile extends Action {
	
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ConfigData config = ConfigDataFactory.findByName(PayhumConstants.PROCESS_COMPANY); 
		int compId = Integer.parseInt(config.getConfigValue());
		List<Company> comps = CompanyFactory.findById(compId);
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
		
		String fileName = "Bank_" + compName + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// CompanyName, Company ID, EmployeeName, Emp ID, Emp National ID, Payroll Cycle, NetPay,BankName,Branch,Routing No,AccountNumber,NetPay
		// Total Tax Amount : XXX
		// Total Social Security Amount : XXX		
		Double totalTaxAmt = 0D;
		Double totalSSAmt = 0D;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMyyyy");
		
		List<CompanyPayroll> compPayroll = CompanyPayrollFactory.findByCompAndProcessedDate(comp, cal.getTime());
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
				allEmpPayStr.append("Company ID");
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
				allEmpPayStr.append("Amount(" + supportedCurrencies[i] + ")");
				allEmpPayStr.append("\n");
				
				for(CompanyPayroll compPay : compPayroll) {
					if(compPay.getBankName().equals("-")) {
						// Its Employee who is paid by cash, include it in separate file.
						totalTaxAmt += compPay.getTaxAmount();
						totalSSAmt += compPay.getSocialSec();
						continue;
					}
					
					if(compPay.getCurrencySym().equalsIgnoreCase(supportedCurrencies[i])) {
						StringBuilder empPayStr = new StringBuilder();
						empPayStr.append(compPay.getCompanyId().getName());
						empPayStr.append(COMMA);
						empPayStr.append(compPay.getCompanyId().getId());
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
						empPayStr.append(new DecimalFormat("###.##").format(compPay.getNetPay()));
						empPayStr.append("\n");
						
						totalTaxAmt += compPay.getTaxAmount();
						totalSSAmt += compPay.getSocialSec();
						
						allEmpPayStr.append(empPayStr);
					}
				}
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
		allEmpPayStr.append("Amount(MMK)");
		allEmpPayStr.append("\n");
		allEmpPayStr.append(comp.getName());
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(comp.getId());
		allEmpPayStr.append(COMMA);			
		allEmpPayStr.append("Social Security");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(sdf.format(compPayroll.get(0).getProcessedDate()));
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalSSAmt));
		allEmpPayStr.append("\n");
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin");
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