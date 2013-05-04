package com.openhr.mleave.action;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;

public class LeaveApplicationAction extends Action {
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
 		
		String leaveFrom   = json.getString("leaveFrom");
		String leaveTo     = json.getString("leaveTo");
		int typeLeave 		= json.getInt("leaveType");
		String description = json.getString("description");
		int employeeId     = json.getInt("employeeId");
		
 		long startDate = new Date(leaveFrom).getTime();
		long endDate = new Date(leaveTo).getTime();
		long diffTime = endDate - startDate;
		int diffDays = (int) diffTime / (1000 * 60 * 60 * 24);
		
		Employee emp = EmployeeFactory.findById(employeeId).get(0);
		LeaveType leaveType = LeaveTypeFactory.findById(typeLeave).get(0);
		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setDescription(description);
		leaveRequest.setEmployeeId(emp);
		leaveRequest.setLeaveDate(new Date(leaveFrom));
		leaveRequest.setReturnDate(new Date(leaveTo));
		leaveRequest.setLeaveTypeId(leaveType);
		leaveRequest.setNoOfDays(diffDays);
		leaveRequest.setStatus(0);
		LeaveRequestFactory.insert(leaveRequest);
			
		return null;
	}
}
