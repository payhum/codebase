package com.openhr.glreports.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
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
import com.openhr.data.GLEmployee;
import com.openhr.employeeadrs.action.EmployeeAdressForm;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.GLEmployeeFactory;

public class ReadEmployeeViewAction extends Action{
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;

		JSONArray result = null;
		long start=0,end=0,diff=0;
		try {
			
			List<GLEmployee> glemployees = GLEmployeeFactory.findAll();
			start=System.currentTimeMillis();
		
			for (GLEmployee emp : glemployees) {
				
				
				//System.out.println("Birthdate " + new Date(emp.getDate()));
			}
			
			
			result = JSONArray.fromObject(glemployees);
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
