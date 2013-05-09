/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.user.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.openhr.Config;
import com.openhr.common.OpenHRAction;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.data.Users;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;
import com.openhr.factories.UsersFactory;
import com.openhr.user.form.LoginForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Mekbib
 */
public class ForgotPasswordAction extends OpenHRAction {

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
        JSONObject json = JSONObject.fromObject(sb.toString());
        
        String userName = json.getString("changeUserName");
        String passWord = json.getString("changeNewPassword");
        String   dob     = json.getString("dob");
        Date dateOf      = new Date(dob);
        int a[] = {10,20};
        List<Users> user = UsersFactory.findByUserName(userName);
        if(user.size() != 0){
        	if(user.get(0).getEmployeeId() != null){
	        	if(dateOf.equals(user.get(0).getEmployeeId().getBirthdate())){
	        		user.get(0).setPassword(passWord);
	            	UsersFactory.update(user.get(0));
	        	}
	        	else{
	        		a[1] = 1;
	        	}
        	}
        }
        else{
        	a[0] = 0;
        }
 		result = JSONArray.fromObject(a);
     	response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result == null) {
			out.print("");
		} else {
			out.print(result.toString());
		}
		out.flush();
     	return null;
 	}
}
