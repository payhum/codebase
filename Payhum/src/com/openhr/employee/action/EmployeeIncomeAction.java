/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Department;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.data.Position;
import com.openhr.data.TypesData;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;
import com.openhr.factories.PositionFactory;

/**
 * 
 * @author Mekbib
 */
public class EmployeeIncomeAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray result = null;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}

		int empId = Integer.parseInt(""
				+ request.getSession().getAttribute("employeeId"));
		Employee emp = EmployeeFactory.findById(empId).get(0);
		EmployeePayroll empPayRoll = EmpPayTaxFactroy
				.findEmpPayrollbyEmpID(emp);

		if (empPayRoll != null) {
			double a[] = { 10, 20 };
			a[0] = empPayRoll.getTaxableOverseasIncome();
			a[1] = empPayRoll.getOtherIncome();
			result = JSONArray.fromObject(a);

			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			if (result == null) {
				out.print("");
			} else {
				out.print(result.toString());
			}
			out.flush();
		}

		return null;
	}

}