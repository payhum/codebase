package com.openhr.employee.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.Dtest;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Etest;
import com.openhr.data.Roles;
import com.openhr.data.Users;
import com.openhr.employee.form.EmployeeDepartFrom;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.UsersFactory;

public class EmployeeCommanAction extends DispatchAction
{

	
	 public ActionForward create(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     
			BufferedReader bf = request.getReader();
			StringBuffer sb = new StringBuffer();
			String line = null;
			 JSONArray result = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line);
			}
			try
			
			{
			JSONArray json = JSONArray.fromObject(sb.toString());
			Collection<EmployeeDepartFrom> aCollection = JSONArray.toCollection(json, EmployeeDepartFrom.class);
			for (EmployeeDepartFrom eFromJSON : aCollection) {
				System.out.println(eFromJSON.getId());
				
				Roles r=new Roles();
				r.setId(Integer.valueOf(eFromJSON.getId()));
				Users u=new Users();
				u.setRoleId(r);
				List<Users> eptx=UsersFactory.findByRoleId(u);
				result = JSONArray.fromObject(eptx);
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			 response.setContentType("application/json; charset=utf-8");
		        PrintWriter out = response.getWriter();
		        out.print(result.toString());
		        out.flush();
	        return mapping.findForward("success");
	    }
	 
	 
	 
	 public ActionForward test(ActionMapping mapping, ActionForm form,
	            HttpServletRequest request, HttpServletResponse response)
	            throws Exception {
	     String s=null;
	     try
			
			{
				List<Dtest>  eptx=UsersFactory.findBy();
				//result = JSONArray.fromObject(eptx);
			for(Dtest d:eptx)
			{
				Set<Etest> employees=d.getEmployees();
				for(Etest e:employees)
					
				{	System.out.println(e.getFirstname());}
			
			}
			//JSONArray result1 = JSONArray.fromObject(eptx);
			//System.out.println(result1.toString());
	       
	    }
	 	catch(Exception e)
		{
			e.printStackTrace();
		}
	     
	     return mapping.findForward("success");
	 }
}
