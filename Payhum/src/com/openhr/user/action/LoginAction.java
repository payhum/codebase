/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.user.action;

import com.openhr.Config;
import com.openhr.common.OpenHRAction;
import com.openhr.data.Employee;
import com.openhr.data.Users;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.UsersFactory;
import com.openhr.user.form.LoginForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author Mekbib
 */
public class LoginAction extends OpenHRAction {

	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		
		LoginForm loginForm = (LoginForm) form;
		Users user = null;
		if (UsersFactory.findByUserName(loginForm.getUsername()).size() > 0) {
			user = UsersFactory.findByUserName(loginForm.getUsername()).get(0);
		}
		if (null != user) {			
			if (user.getPassword().equalsIgnoreCase(loginForm.getPassword())) {
				
				request.getSession().setAttribute("loggedUser",
						loginForm.getUsername());
				Users u = UsersFactory.findByUserName(loginForm.getUsername())
						.get(0);
				request.getSession().setAttribute("employeeId",
						u.getEmployeeId().getId());
				
				request.getSession().setAttribute("loggedEmployee",
						u.getEmployeeId());
				
				
				if (loginForm.getRole().equalsIgnoreCase("Administrator")) {
					return map.findForward("admin");
				}
				else if (loginForm.getRole().equalsIgnoreCase("HR")) {
					return map.findForward("hr");
				}
				else if (loginForm.getRole().equalsIgnoreCase("Employee")) {
					return map.findForward("member");
				}
				else if (loginForm.getRole().equalsIgnoreCase("Accountant")) {
					return map.findForward("finance");
				}
				else if (loginForm.getRole().equalsIgnoreCase("MasterAdmin")) {
					return map.findForward("masteradmin");
				}
			}
		}else{
			request.getSession().setAttribute("loggedUser", null);
			return map.findForward("login");
		}
		return map.findForward("login");
	}
}
