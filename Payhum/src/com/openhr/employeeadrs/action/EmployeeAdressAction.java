package com.openhr.employeeadrs.action;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.factories.EmployeeFactory;


public class EmployeeAdressAction extends Action{
	private static List<EmployeeAdressForm> employees1 = new ArrayList<EmployeeAdressForm>();
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
			Iterator<Employee> it = employees.iterator();
			while (it.hasNext()) {
				
				EmployeeAdressForm tm=new EmployeeAdressForm();
				Employee e = it.next();
				
				tm.setEmployeeId(e.getEmployeeId());
				tm.setFirstname(e.getFirstname()+"   "+e.getMiddlename());
				tm.setId(e.getId());
				
				tm.setAdress("NONE");
				employees1.add(tm);
				
			}
			
			
			result = JSONArray.fromObject(employees1);
			end=System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("It took " + diff +" milli seconds to read the results");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(result.toString());
		out.flush();

		return map.findForward("");
	}

}
