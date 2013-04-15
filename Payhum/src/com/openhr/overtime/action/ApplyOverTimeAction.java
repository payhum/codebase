package com.openhr.overtime.action;

import java.io.BufferedReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.OverTime;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.OverTimeFactory;

public class ApplyOverTimeAction extends Action {
	@SuppressWarnings("deprecation")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
 		
		String requestOnDate    = json.getString("requestOnDate");
		Double noOfHours     	= json.getDouble("noOfHours");	
		int employeeId      = json.getInt("employeeId");	
 		
		OverTime overTime = new OverTime();
		Employee employee = EmployeeFactory.findById(employeeId).get(0);
 		overTime.setNoOfHours(noOfHours);
		overTime.setOverTimeDate(new Date(requestOnDate));
		overTime.setEmployeeId(employee);
		overTime.setStatus(0);
		overTime.setApprovedBy("PayHum");
		overTime.setApprovedDate(new Date());
		OverTimeFactory.insert(overTime);	 
		return null;
	}
}
