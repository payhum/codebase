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


public class GovtFile extends Action {
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
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        
		String monthYear = new SimpleDateFormat("MMM_yyyy").format(now);
		
		String fileName = "Govt_" + compName + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// EmployeeName,EmpNationalID,TaxAmount
		
		List<CompanyPayroll> compPayroll = CompanyPayrollFactory.findByCompMonthYear(compId, month, year);
		StringBuilder allEmpPayStr = new StringBuilder();
		for(CompanyPayroll compPay : compPayroll) {
			StringBuilder empPayStr = new StringBuilder();
			empPayStr.append(compPay.getEmpFullName());
			empPayStr.append(COMMA);
			empPayStr.append(compPay.getEmpNationalID());
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("MMK ###.##").format(compPay.getTaxAmount()));
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
		}
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin");
	}
}