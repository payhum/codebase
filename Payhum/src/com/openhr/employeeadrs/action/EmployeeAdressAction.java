package com.openhr.employeeadrs.action;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.openhr.data.Employee;
import com.openhr.factories.EmployeeFactory;


public class EmployeeAdressAction extends Action{
	
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;

		JSONArray result = null;
		long start=0,end=0,diff=0;
		try {
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			List<Employee> employees = EmployeeFactory.findAll();
			start=System.currentTimeMillis();
	
			result = JSONArray.fromObject(employees, config);

			end=System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}

}
