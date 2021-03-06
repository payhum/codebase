/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import com.openhr.data.Employee;
import com.openhr.factories.EmployeeFactory;
import com.openhr.employee.form.EmployeeForm;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Mekbib
 */
public class ReadEmployeeDetails extends Action {

	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;

		JSONArray result = null;
		long start=0,end=0,diff=0;
		try {
			
			List<Employee> employees = EmployeeFactory.findAll();
			start=System.currentTimeMillis();
			for (Employee emp : employees) {
				/*System.out.println("Path to look photos for: "
						+ getServlet().getServletContext().getRealPath(
								emp.getPhoto()));*/
				emp.setFirstname(emp.getFirstname()+" "+emp.getMiddlename()+" "+emp.getLastname());
				
				File photo = new File(getServlet().getServletContext()
						.getRealPath(emp.getPhoto()));
				if (!photo.exists()) {
					emp.setPhoto("/data/photo/placeholder-pic.png");
				}
				//System.out.println("Birthdate " + new Date(emp.getBirthdate()));
			}

			JsonConfig config = new JsonConfig();
            config.setIgnoreDefaultExcludes(false);
            config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
            
			result = JSONArray.fromObject(employees, config);
			
			
			end=System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("It took " + diff +" milli seconds to read the results");
		System.out.print(result.toString());
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result == null) {
			out.print("");
		} else {
			out.print(result.toString());
		}
		out.flush();

		return map.findForward("");
	}
}