package com.openhr.zipfile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.PayPeriodData;
import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.PayPeriodFactory;
import com.openhr.factories.PayrollFactory;
import com.openhr.factories.PdfFactory;
import com.util.payhumpackages.PayhumUtil;

public class PayZipDownload extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ConfigData configpay = ConfigDataFactory.findByName(PayhumConstants.PAYROLLDATE_ID); 
		String payRunDate = configpay.getConfigValue();
		
		Calendar currDtCal = Calendar.getInstance();

	    // Zero out the hour, minute, second, and millisecond
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);

	    List<PayrollDate> payrollDates = new ArrayList<PayrollDate>();
		List<EmpPayrollMap> employees = new ArrayList<EmpPayrollMap>();
		
		List<PayrollDate> pds = PayrollFactory.findPayrollDateByID(Integer.parseInt(payRunDate));
		
		payrollDates.addAll(PayrollFactory.findPayrollDateByBranch(pds.get(0).getBranchId().getId()));
		
		List<Payroll> prd = EmpPayTaxFactroy.findPayrollByIDAndBranch(payRunDate, pds.get(0).getBranchId().getId());
		employees.addAll(EmpPayTaxFactroy.findTaxMonthlyForEmployeeByDate(prd.get(0)));
		
		List<Payroll> payRuns = PayrollFactory.findAllPayrollRunsPerBranch(pds.get(0).getBranchId().getId());
		Integer divNo = PayhumUtil.remainingPaycycles(payrollDates, payRuns);
		
		Integer mulNO = PayrollFactory.findAllPayrollRunsPerBranch(prd.get(0).getBranchId().getId()).size();
				
		boolean monthly = false;
		List<PayPeriodData> payPeriods = PayPeriodFactory.findAll();
		for(PayPeriodData ppd : payPeriods) {
			if(ppd.getPeriodName().equalsIgnoreCase("Monthly")) {
				monthly = true;
				break;
			}
		}
		
		Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(pds.get(0).getRunDateofDateObject());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

		String monthYear = new SimpleDateFormat("MMM_yyyy").format(now);
		
		String fileName = "Paystubs_" + pds.get(0).getBranchId().getCompanyId().getName() + "_" + 
				pds.get(0).getBranchId().getName() + "_Payroll_" + monthYear + ".zip"; 
		
		fileName = fileName.replace(" ", "_");
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition",
				"attachment; filename=" + fileName );

		ServletOutputStream os = response.getOutputStream();

		ZipOutputStream zos = new ZipOutputStream(os);

		PdfFactory.zipFileDownload(zos, employees, mulNO, divNo, monthly, pds.get(0));

		zos.flush();

		zos.close();

		os.flush();
		os.close();

		return mapping.findForward("masteradmin.form");
	}

}
