package com.openhr.zipfile;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

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

public class PayZipDownload extends DispatchAction

{

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String payRunDate = request.getParameter("payRollDates");

		// Date paydate=new Date(new Long(payRunDate));
		PayPeriodData pd = PayPeriodFactory.findAll().get(0);
		Integer mulNO = PayPeriodFactory.findCountRunOndate().size();
		
		List<PayrollDate> payrollDates = new ArrayList<PayrollDate>();
		List<EmpPayrollMap> employees = new ArrayList<EmpPayrollMap>();
		
		List<PayrollDate> pds = PayrollFactory.findPayrollDateByID(Integer.parseInt(payRunDate));
		
		payrollDates.addAll(PayrollFactory.findPayrollDateByBranch(pds.get(0).getBranchId().getId()));
		
		List<Payroll> prd = EmpPayTaxFactroy.findPayrollByIDAndBranch(payRunDate, pds.get(0).getBranchId().getId());
		employees.addAll(EmpPayTaxFactroy.findTaxMonthlyForEmployeeByDate(prd.get(0)));
		
		List<Payroll> payRuns = PayrollFactory.findAllPayrollRuns();
		Integer divNo = PayhumUtil.remainingPaycycles(payrollDates, payRuns);
		
		boolean monthly = false;
		List<PayPeriodData> payPeriods = PayPeriodFactory.findAll();
		for(PayPeriodData ppd : payPeriods) {
			if(ppd.getPeriodName().equalsIgnoreCase("Monthly")) {
				monthly = true;
				break;
			}
		}
		
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"PayStubs.zip\"");

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
