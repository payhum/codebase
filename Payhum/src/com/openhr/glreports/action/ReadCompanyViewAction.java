package com.openhr.glreports.action;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
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
import com.openhr.data.GLEmployee;

import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.GLEmployeeFactory;
import com.openhr.glreports.form.GlReportForm;

public class ReadCompanyViewAction extends Action {
	static Double sumc = 0.0;
	static Double sumd = 0.0;

	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {

			start = System.currentTimeMillis();

			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			// List<GlReportForm> glemployees = new ArrayList<GlReportForm>();
			List<Object[]> empPay = GLEmployeeFactory.findCompanyView();
			start = System.currentTimeMillis();
			List<GlReportForm> empFrm = new ArrayList<GlReportForm>();

			System.out.println("Designation\tTotal Salary");
			GlReportForm glf = null;

			for (Object[] obj : empPay) {
				for (int i = 1; i < 6; i++)

				{

					glf = new GlReportForm();

					switch (i) {
					case 1:
						glf.setEname("Total NetPay");

						glf.setCredt((Double) obj[0]);
						break;
					case 2:
						glf.setEname("Tax Amount");
						glf.setCredt((Double) obj[1]);
						break;
					case 3:
						glf.setEname("Total Deductions");

						glf.setCredt((Double) obj[2]);
						break;
					case 4:
						glf.setEname("Total Earnings");

						glf.setDebit((Double) obj[3]);
						break;
					case 5:
						glf.setEname("Total Allowance");

						glf.setDebit((Double) obj[4]);
					}

					empFrm.add(glf);
				}

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
