package com.openhr.employee.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.Roles;
import com.openhr.data.Users;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.UsersFactory;

public class GetEmpAllAction extends Action {
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
	BufferedReader bf = request.getReader();
	StringBuffer sb = new StringBuffer();
	String line = null;
	List<Users> lu=null;
	JSONArray result = null;
	long start=0,end=0,diff=0;
	try
	{
	while ((line = bf.readLine()) != null) {
		sb.append(line);
	}
	JSONArray json = JSONArray.fromObject(sb.toString());
	

	Collection<EmployeeForm> aCollection = JSONArray.toCollection(json, EmployeeForm.class);
	
	System.out.println("Employee JSON "+json.toString());
	

	for (EmployeeForm eFromJSON : aCollection) {
		Users u=new Users();
		Roles r=new Roles();
		System.out .println(u.hashCode());
		System.out.println(eFromJSON.getEmployeeId());
		
		r.setId(Integer.parseInt(eFromJSON.getEmployeeId()));
		u.setRoleId(r);
		//Roles rid=eFromJSON.getRoleID();
		
		//u.setRoleId(rid);
		lu=UsersFactory.findByRoleId(u);
		 result = JSONArray.fromObject(lu);
	}
	}
	catch(Exception e)
	{
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
