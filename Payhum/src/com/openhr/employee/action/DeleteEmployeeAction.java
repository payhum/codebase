/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import java.io.BufferedReader;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.employee.form.EmployeeForm;
import com.openhr.factories.EmployeeFactory;

/**
 *
 * @author Mekbib
 */
public class DeleteEmployeeAction extends Action{
	@Override
    public ActionForward execute(ActionMapping map,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        //EmployeeForm EmployeeForm = (EmployeeForm) form;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json = JSONArray.fromObject(sb.toString());
		Collection<EmployeeForm> aCollection = JSONArray.toCollection(json, EmployeeForm.class);
		
		System.out.println("Employee JSON "+json.toString());
		
		Employee e = new Employee();
		for (EmployeeForm eFromJSON : aCollection) {
			e.setId(eFromJSON.getId()); 
			EmployeeFactory.delete(e);
		}
    return map.findForward("");    
	}
}
