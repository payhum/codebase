/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Department;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Position;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.PositionFactory;

/**
 * 
 * @author Mekbib
 */
public class EmployeeIncomeUpdate extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 throws Exception{
 		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		boolean flag=false;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
 		
		 System.out.println("UPDATE Employee JSON " + json.toString());
		 
		 int empId = Integer.parseInt(""+request.getSession().getAttribute("employeeId"));
	     Employee emp = EmployeeFactory.findById(empId).get(0);
	     System.out.println("........"+empId);
	     EmployeePayroll empPayRoll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);
		 double overseas = json.getDouble("overseas");
		 double other = json.getDouble("other");
	     empPayRoll.setTaxableOverseasIncome(overseas);
	     empPayRoll.setOtherIncome(other);
	     EmpPayTaxFactroy.update(empPayRoll);

		return map.findForward(""); 
	}
}
