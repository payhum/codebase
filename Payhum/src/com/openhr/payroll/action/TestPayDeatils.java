package com.openhr.payroll.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;

public class TestPayDeatils  extends DispatchAction{
	
    public ActionForward create(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //EmployeeForm EmployeeForm = (EmployeeForm) form;
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
			System.out.println(eFromJSON.getId());
			List<DeductionsDone> eptx=EmpPayTaxFactroy.deductionsDone(eFromJSON.getId());
		
			
				result = JSONArray.fromObject(eptx);
				
				//DeductionsDone deductionsDone= epr.getDeductionsDone().get(0);
				
				///ExemptionsDone exemptionsDone=epr.getExemptionsDone().get(0);
				//System.out.println("deductionsDeclared"+deductionsDeclared.getEmployeeId()+"--"+deductionsDeclared.getAmount());
				//System.out.println("exemptionsDone"+exemptionsDone.getEmployeeId()+"--"+exemptionsDone.getAmount());
			
			//result = JSONArray.fromObject(eptx);
		}
        //System.out.print(result.toString());
        
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
        //EmployeeForm EmployeeForm = (EmployeeForm) form;
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
			System.out.println(eFromJSON1.getId());
			List<ExemptionsDone> eptx1=EmpPayTaxFactroy.exemptionsDone(eFromJSON1.getId());
		
			
			
				result = JSONArray.fromObject(eptx1);
				
				//DeductionsDone deductionsDone= epr.getDeductionsDone().get(0);
				
				///ExemptionsDone exemptionsDone=epr.getExemptionsDone().get(0);
				//System.out.println("deductionsDeclared"+deductionsDeclared.getEmployeeId()+"--"+deductionsDeclared.getAmount());
				//System.out.println("exemptionsDone"+exemptionsDone.getEmployeeId()+"--"+exemptionsDone.getAmount());
			
			
			//result = JSONArray.fromObject(eptx);
		}
        //System.out.print(result.toString());
        
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
      out.print(result.toString());
        out.flush();
		
    return map.findForward("");    
	}
}