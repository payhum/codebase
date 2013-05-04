package com.openhr.employeereport.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.Branch;
import com.openhr.data.Department;
import com.openhr.data.EmpPayTax;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.LeaveRequest;
import com.openhr.employee.form.AcumilateForm;
import com.openhr.factories.BranchFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;

public class ReadEmployeeAccumulators extends DispatchAction {
	
	
	public ActionForward readAccumulators(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		
		
	
		
		try {
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			List <Object[]> ls=LeaveRequestFactory.findByStatusNodays(1);
			List<EmployeeForm> acl=new ArrayList<EmployeeForm>();
			EmployeeForm acfm=null;
	for(Object[] obj:ls)
	{
		acfm =new EmployeeForm();
	Employee e=(Employee)obj[0];
	acfm.setEmployeeId(e.getEmployeeId());
	acfm.setFirstname(e.getFirstname()+"."+e.getMiddlename());
		acfm.setBirthdate(e.getBirthdate());
		acfm.setHiredate(e.getBirthdate());
		acfm.setPayrol(EmpPayTaxFactroy.findEmpPayrollbyEmpID(e));
		acfm.setDeptId(e.getDeptId());
		acfm.setCount((Long)obj[1]);
		acl.add(acfm);
	}
			start = System.currentTimeMillis();
			result = JSONArray.fromObject(acl, config);
			end = System.currentTimeMillis();
			diff = end - start;
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
	public ActionForward getDepartAllAcum(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

	JSONArray result = new JSONArray();
	
	BufferedReader bf = request.getReader();
    StringBuffer sb = new StringBuffer();
    String line = null;
    while ((line = bf.readLine()) != null) {
        sb.append(line);
    }
    
    
		try {
			JSONObject json = JSONObject.fromObject(sb.toString());
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			Integer ids= json.getInt("Id");	
			Department dep=new Department();
			dep.setId(ids);
			
			List <Object[]> ls=LeaveRequestFactory.findByStatusNodaysJion(1,dep);
			
			List<EmployeeForm> acl=new ArrayList<EmployeeForm>();
			EmployeeForm acfm=null;
	for(Object[] obj:ls)
	{
		acfm =new EmployeeForm();
	Employee e=(Employee)obj[0];
	acfm.setEmployeeId(e.getEmployeeId());
	acfm.setFirstname(e.getFirstname()+"."+e.getMiddlename());
		acfm.setBirthdate(e.getBirthdate());
		acfm.setHiredate(e.getBirthdate());
		acfm.setPayrol(EmpPayTaxFactroy.findEmpPayrollbyEmpID(e));
		acfm.setDeptId(e.getDeptId());
		acfm.setCount((Long)obj[1]);
		acl.add(acfm);
	}
			
			result = JSONArray.fromObject(acl, config);
			
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
