package com.openhr.glreports.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.EmployeePayroll;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.glreports.form.GlReportForm;

public class ReadEmployeeViewAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;

		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			// List<GlReportForm> glemployees = new ArrayList<GlReportForm>();
			List<EmployeePayroll> empPay = EmpPayTaxFactroy.findAllEmpPayroll();
			start = System.currentTimeMillis();
			List<GlReportForm> empFrm = new ArrayList<GlReportForm>();

			GlReportForm glf = null;

			for (EmployeePayroll emp : empPay) {

				for (int i = 1; i < 6; i++)

				{

					glf = new GlReportForm();

					switch (i) {
					case 1:
						glf.setEmpName(emp.getFullName());
						glf.setEname("NetPay");
						glf.setCredt(emp.getNetPay());
						break;
					case 2:
						glf.setEname("TaxAmount");
						glf.setEmpName(emp.getFullName());

						glf.setCredt(emp.getTaxableIncome());
						break;
					case 3:
						glf.setEname("Deductions");

						glf.setEmpName(emp.getFullName());
						glf.setCredt(emp.getTotalDeductions());
						break;
					case 4:
						glf.setEname("Earnings");

						glf.setEmpName(emp.getFullName());
						glf.setDebit(emp.getBaseSalary());
						break;
					case 5:
						glf.setEname("Allowance");

						glf.setEmpName(emp.getFullName());
						glf.setDebit(emp.getAllowancesAmount());
					}

					empFrm.add(glf);
				}

				// System.out.println("Birthdate " + new Date(emp.getDate()));

			}

			result = JSONArray.fromObject(empFrm, config);
			end = System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("It took " + diff
				+ " milli seconds to read the results");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}

}
