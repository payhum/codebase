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

import com.openhr.company.Company;
import com.openhr.company.CompanyPayroll;
import com.openhr.factories.CompanyFactory;
import com.openhr.factories.CompanyPayrollFactory;

public class BankFile extends Action {
	
	private static final String COMMA = ",";
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Get the company id
		Integer compId = 1;
		List<Company> comps = CompanyFactory.findById(compId);
		String compName = comps.get(0).getName();
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
		// EmployeeName,BankName,Branch,AccountNumber,NetPay
		// Total Tax Amount : XXX
		// Total Social Security Amount : XXX		
		Double totalTaxAmt = 0D;
		Double totalSSAmt = 0D;
		
		List<CompanyPayroll> compPayroll = CompanyPayrollFactory.findByProcessedDate(cal.getTime());
		StringBuilder allEmpPayStr = new StringBuilder();
		// Populate the header.
		allEmpPayStr.append("Employee Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Bank Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Bank Branch");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Account Number");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Amount (MMK)");
		allEmpPayStr.append("\n");
		
		for(CompanyPayroll compPay : compPayroll) {
			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(compPay.getEmpFullName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getBankName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getBankBranch());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getAccountNo());
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(compPay.getNetPay()));
			empPayStr.append("\n");
			
			totalTaxAmt += compPay.getTaxAmount();
			totalSSAmt += compPay.getSocialSec();
			
			allEmpPayStr.append(empPayStr);
		}
		
		allEmpPayStr.append("Total Tax Amount : " + new DecimalFormat("MMK ###.##").format(totalTaxAmt));
		allEmpPayStr.append("\n");
		allEmpPayStr.append("Total Social Security Amount : " + new DecimalFormat("MMK ###.##").format(totalSSAmt));
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin");
	}
}