package com.openhr.employee.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.EmployeePayroll;
import com.openhr.employee.form.PieChartForm;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;

public class PieChartCommanActions extends DispatchAction {
	
	
	public ActionForward getPieChart(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		JSONArray result = new JSONArray();
		try {
	
			List<PieChartForm> l=new ArrayList<PieChartForm>();
		List <Object[]> lob=EmployeeFactory.findAllDepartEmpChart();
		PieChartForm p=null;
		for(Object[] b:lob)
			
		{
			p=new PieChartForm();
			p.setCategory((String)b[1]);
			
			p.setValue((Long)b[0]);
			l.add(p);
		}
			result = JSONArray.fromObject(l);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print(result.toString());

		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}

}
