/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;


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
public class ReadEmployeeAction extends Action {

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
			List<EmployeeForm> emplist =new ArrayList<EmployeeForm>();
			EmployeeForm empFrm=null;
			start=System.currentTimeMillis();
			for (Employee emp : employees) {
				/*System.out.println("Path to look photos for: "
						+ getServlet().getServletContext().getRealPath(
								emp.getPhoto()));*/
				empFrm=new EmployeeForm();
				empFrm.setPayrol(EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp));
			empFrm.setEmpsal(EmployeeFactory.getEmpsalry(emp));
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
				empFrm.setPassExpDate(emp.getPpExpDate());
				empFrm.setPassPlace(emp.getPpIssuePlace());
				empFrm.setPassNo(emp.getPpNumber());
				empFrm.setPhNo(emp.getPhoto());
				
				empFrm.setEmpAddrss(emp.getAddress());
				
				
				File photo = new File(getServlet().getServletContext()
						.getRealPath(emp.getPhoto()));
				if (!photo.exists()) {
					
					empFrm.setPhoto("/data/photo/placeholder-pic.png");
					
				}
				//System.out.println("Birthdate " + new Date(emp.getBirthdate()));
				emplist.add(empFrm);
				
				
			
			}

			//result = JSONArray.fromObject(emplist, config);
			result = JSONArray.fromObject(emplist, config);

			end=System.currentTimeMillis();
			diff = end - start;
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("It took " + diff +" milli seconds to read the results");
		System.out.print(result.toString());
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
			out.print(result.toString());
		
		out.flush();

		return map.findForward("");
	}
}