package com.openhr.employee.action;

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
import com.openhr.data.Users;
import com.openhr.employee.form.EmployeeDepartFrom;
import com.openhr.employeeadrs.action.EmployeeAdressForm;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.UsersFactory;

public class EmployeeDepartAction extends Action{

	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// EmployeeForm EmployeeForm = (EmployeeForm) form;
	 List<EmployeeDepartFrom> employees1 = new ArrayList<EmployeeDepartFrom>();
		JSONArray result = null;
		long start=0,end=0,diff=0;
		try {
			
			List employees = UsersFactory.findEmpDepart();
			start=System.currentTimeMillis();
			Iterator it = employees.iterator();
			while(it.hasNext())
			{
			Object b[] =(Object[])it.next();
			EmployeeDepartFrom ef=new EmployeeDepartFrom();
				ef.setDepname(b[0].toString());
				ef.setTotal(b[1].toString());
				ef.setId(b[2].toString());
				//System.out.println(b[0]);
				employees1.add(ef);
			}
		
		
		/*
			for(Object[] b:employees)
			{
				EmployeeDepartFrom ef=new EmployeeDepartFrom();
				ef.setDepname(b[0].toString());
				ef.setTotal(b[1].toString());
				ef.setId(b[2].toString());
				//System.out.println(b[0]);
				employees1.add(ef);
			}
			
			*/
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
