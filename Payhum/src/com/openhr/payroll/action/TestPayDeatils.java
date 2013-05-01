package com.openhr.payroll.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;

public class TestPayDeatils  extends DispatchAction{
	
    public ActionForward create(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
    	BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		JSONArray result = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<EmployeeForm> aCollection = JSONArray.toCollection(json, EmployeeForm.class);
		for (EmployeeForm eFromJSON : aCollection) {
			// System.out.println(eFromJSON.getId());
			Employee emp = new Employee(eFromJSON.getId(), eFromJSON.getEmployeeId(), eFromJSON.getFirstname(),
										eFromJSON.getMiddlename(), eFromJSON.getLastname(),eFromJSON.getSex(),
										eFromJSON.getBirthdate(), eFromJSON.getHiredate());
			EmployeePayroll ePayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);
			List<DeductionsDone> eptx=EmpPayTaxFactroy.deductionsDone(ePayroll);
			
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			result = JSONArray.fromObject(eptx, config);
		}
		
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();
		
        return map.findForward("");    
	}
    
    public ActionForward getExption(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		 JSONArray result = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<EmployeeForm> aCollection = JSONArray.toCollection(json, EmployeeForm.class);
		for (EmployeeForm eFromJSON1 : aCollection) {
			// System.out.println(eFromJSON1.getId());
			Employee emp = new Employee(eFromJSON1.getId(), eFromJSON1.getEmployeeId(), eFromJSON1.getFirstname(),
					eFromJSON1.getMiddlename(), eFromJSON1.getLastname(),eFromJSON1.getSex(),
					eFromJSON1.getBirthdate(), eFromJSON1.getHiredate());
			EmployeePayroll ePayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);

			List<ExemptionsDone> eptx1=EmpPayTaxFactroy.exemptionsDone(ePayroll.getId());
			
			JsonConfig config = new JsonConfig();
			config.setIgnoreDefaultExcludes(false);
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			
			result = JSONArray.fromObject(eptx1, config);
		}
		
		response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(result.toString());
        out.flush();
		
        return map.findForward("");    
	}
}