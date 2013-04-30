package com.openhr.user.action;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Users;
import com.openhr.factories.UsersFactory;

public class UpdateUserAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONArray json,result;
		json = JSONArray.fromObject(sb.toString());
		System.out.println("EMployee JSON "+json.toString());
		
		/*Collection<UserForm> aCollection = JSONArray.toCollection(json, UserForm.class);
		
		Users u = new Users();
		for (UserForm uFromJSON : aCollection) {
			u.setId(uFromJSON.getId());
			u.setEmployeeId(uFromJSON.getEmployeeId());
			u.setUsername(uFromJSON.getUsername());
			u.setPassword(uFromJSON.getPassword()); 
			u.setRoleId(uFromJSON.getRoleId());
			UsersFactory.update(u);
		}*/
		int a[] = {10,20};
		Users u = new Users();
		for(int i = 0; i < json.size(); i++) {
			JSONObject jObj = json.getJSONObject(i);
			
			if ( UsersFactory.isCredsValid(jObj.getString("oldUserName"), 
								jObj.getString("oldPassword")) ) {
				
				if(jObj.getString("newPassword").equals(jObj.getString("confirmPassword"))) {
					u.setUsername(jObj.getString("oldUserName"));
					u.setPassword(jObj.getString("newPassword"));
					UsersFactory.update(u);
				}
				else {
					a[0] = 0;
 				}
			}
			else {
				a[1] = 1;
 			}
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
