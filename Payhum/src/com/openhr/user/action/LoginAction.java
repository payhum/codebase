/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.user.action;

import java.util.List;



import com.openhr.Config;
import com.openhr.common.OpenHRAction;
import com.openhr.common.PayhumConstants;
import com.openhr.data.ConfigData;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.LeaveType;
import com.openhr.data.Users;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveTypeFactory;
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
		
		ConfigData payhumConfig = ConfigDataFactory.findByName(PayhumConstants.EMODE);
		
		if (null != user) {			
			if (user.getPassword().equalsIgnoreCase(loginForm.getPassword())) {
				
				request.getSession().setAttribute("loggedUser",
						loginForm.getUsername());
				request.getSession().setAttribute("loggedRole",
						loginForm.getRole());
				Users u = UsersFactory.findByUserName(loginForm.getUsername())
						.get(0);
				String s = u.getEmployeeId().getEmployeeId();
				request.getSession().setAttribute("employeeId",
						u.getEmployeeId().getId());
				
				request.getSession().setAttribute("loggedEmployee",
						u.getEmployeeId());
				//Employee e=u.getEmployeeId();
				EmployeePayroll epay=	EmpPayTaxFactroy.findEmpPayrollbyEmpID(u.getEmployeeId()); 
				if(epay!=null)
				{
					request.getSession().setAttribute("empPay",
							epay.getId());
				}
				
				if (loginForm.getRole().equalsIgnoreCase("PageAdmin")) {
					recordLoggedUserComp(user);
					return map.findForward("admin");
				}
				else if (loginForm.getRole().equalsIgnoreCase("HumanResource")) {
					recordLoggedUserComp(user);
					return map.findForward("hr");
				}
				else if (loginForm.getRole().equalsIgnoreCase("Employee")) {
 					 List<LeaveType> leaveTypes = LeaveTypeFactory.findAll();
					 request.setAttribute("leaveTypes", leaveTypes);
					 Employee nn = user.getEmployeeId();
					 String n = nn.getFirstname();
					 request.setAttribute("employeeId", n);
					 recordLoggedUserComp(user);
					return map.findForward("member");
				}
				else if (loginForm.getRole().equalsIgnoreCase("Accountant")) {
					recordLoggedUserComp(user);
					return map.findForward("finance");
				}
				else if (loginForm.getRole().equalsIgnoreCase("MasterAdmin")) {
					if(payhumConfig.getConfigValue().equalsIgnoreCase(PayhumConstants.MMODE)) {
						recordLoggedUserComp(user);
						return map.findForward("masteradmin");
					} else {
						request.getSession().setAttribute("loggedUser", null);
						recordLoggedUserComp(user);
						return map.findForward("login");
					}
				}
			}
		}else{
			request.getSession().setAttribute("loggedUser", null);
			return map.findForward("login");
		}
		return map.findForward("login");
	}
	
	private void recordLoggedUserComp(Users user) {
		// Once the user logs in record his company ID in the database for usage.
		Integer compId = user.getEmployeeId().getDeptId().getBranchId().getCompanyId().getId();
		if(compId == null) {
			throw new RuntimeException("Unable to get the user's company details");
		} else {
			ConfigData config = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP);
			config.setConfigValue(compId.toString());
			
			ConfigDataFactory.update(config);
		}
	}
}
