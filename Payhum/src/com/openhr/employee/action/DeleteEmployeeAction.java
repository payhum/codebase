/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.openhr.employee.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.EmployeeForm; 
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
		
		boolean flag=false;
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		Integer ids= json.getInt("id");	
		//Collection<EmployeeForm> aCollection = JSONArray.toCollection(json, EmployeeForm.class);
		
		System.out.println("Employee JSON "+json.toString());
		flag=EmployeeFactory.deleteGridId(ids);
		
		PrintWriter out = response.getWriter();
		  out.println(flag);
		
    return map.findForward("");    
	}
}
