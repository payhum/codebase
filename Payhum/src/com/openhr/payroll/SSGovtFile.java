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


public class SSGovtFile extends Action {
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
		
		String fileName = "SocialSec_" + compName + "_Payroll_" + monthYear + ".csv";
		
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/force-download");
		
		// Columns in the file will be:
		// EmployeeName,EmpNationalID,SocialSecAmt
		Double totalAmt = 0D;
		SimpleDateFormat sdf = new SimpleDateFormat("MMMyyyy");
		
		List<CompanyPayroll> compPayroll = CompanyPayrollFactory.findByCompAndProcessedDate(comp, cal.getTime());
		StringBuilder allEmpPayStr = new StringBuilder();

		allEmpPayStr.append("Company Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Company ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee Name");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Employee National ID");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Payroll Cycle");
		allEmpPayStr.append(COMMA);
		allEmpPayStr.append("Amount (MMK)");
		allEmpPayStr.append("\n");
		
		for(CompanyPayroll compPay : compPayroll) {
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
			empPayStr.append(sdf.format(compPay.getProcessedDate()));
			empPayStr.append(COMMA);
			empPayStr.append(new DecimalFormat("###.##").format(compPay.getSocialSec()));
			empPayStr.append("\n");
			
			allEmpPayStr.append(empPayStr);
			
			totalAmt += compPay.getSocialSec();
		}
		
		allEmpPayStr.append("\n,,,,,TOTAL:,");
		allEmpPayStr.append(new DecimalFormat("###.##").format(totalAmt));
		
		OutputStream os = response.getOutputStream();
		os.write(allEmpPayStr.toString().getBytes());
		os.close();
		
		return map.findForward("masteradmin");
	}
}
