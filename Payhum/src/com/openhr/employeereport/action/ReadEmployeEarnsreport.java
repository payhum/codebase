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

import com.openhr.data.Department;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;

public class ReadEmployeEarnsreport extends DispatchAction {

	public ActionForward readEarns(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray result = null;
		long start = 0, end = 0, diff = 0;
		try {
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			List<EmployeePayroll> empPayroll = EmpPayTaxFactroy.findAllEmpPayroll();
			start = System.currentTimeMillis();
			result = JSONArray.fromObject(empPayroll, config);
			end = System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

 		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result == null) {
			out.print("");
		} else {
			out.print(result.toString());
		}
		out.flush();

		return map.findForward("");
	}
	public ActionForward getDepartAllEmp(ActionMapping map, ActionForm form,
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
			List<Employee> empList = EmployeeFactory.findAllEmpPerDepart(dep);
			List<EmployeeForm> emplist =new ArrayList<EmployeeForm>();
			EmployeeForm empFrm=null;
			
			
			for (Employee emp : empList) {
				/*System.out.println("Path to look photos for: "
						+ getServlet().getServletContext().getRealPath(
								emp.getPhoto()));*/
				empFrm=new EmployeeForm();
				empFrm.setPayrol(EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp));
			//empFrm.setEmpsal(EmployeeFactory.getEmpsalry(emp));
				empFrm.setBirthdate(emp.getBirthdate());
				empFrm.setEmerContactName(emp.getEmerContactName());
				empFrm.setEmerContactNo(emp.getEmerContactNo());
				empFrm.setContNumber(emp.getEmerContactNo());
				empFrm.setDeptId(emp.getDeptId());
				empFrm.setEmployeeId(emp.getEmployeeId());
				empFrm.setEmpNationalID(emp.getEmpNationalID());
				empFrm.setFirstname(emp.getFirstname());
				empFrm.setMiddlename(emp.getMiddlename());
				empFrm.setLastname(emp.getLastname());
				empFrm.setSex(emp.getSex());
				empFrm.setStatus(emp.getStatus());
				empFrm.setResidentType(emp.getResidentType());
				empFrm.setPositionIds(emp.getPositionId());
				empFrm.setMarried(emp.isMarried());
				empFrm.setId(emp.getId());
				empFrm.setHiredate(emp.getHiredate());
				
		
				
				File photo = new File(getServlet().getServletContext()
						.getRealPath(emp.getPhoto()));
				if (!photo.exists()) {
					
					empFrm.setPhoto("/data/photo/placeholder-pic.png");
					
				}
				//System.out.println("Birthdate " + new Date(emp.getBirthdate()));
				emplist.add(empFrm);
				
				
			
			}
			
			result = JSONArray.fromObject(emplist, config);
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