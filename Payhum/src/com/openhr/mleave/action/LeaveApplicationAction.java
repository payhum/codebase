package com.openhr.mleave.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.openhr.mleave.LeaveRequestForm;

public class LeaveApplicationAction extends Action {
	@SuppressWarnings("deprecation")
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
 
		LeaveRequestForm leaveReqfrom = (LeaveRequestForm) form;
		long startDate = new Date(request.getParameter("leaveDate")).getTime();
		long endDate = new Date(request.getParameter("returnDate")).getTime();
		long diffTime = endDate - startDate;
		int diffDays = (int) diffTime / (1000 * 60 * 60 * 24);
		Employee emp = EmployeeFactory.findById(Integer.parseInt(leaveReqfrom.getEmployeeId())).get(0);
		LeaveType leaveType = LeaveTypeFactory.findById(leaveReqfrom.getLeaveTypeId()).get(0);
		LeaveRequest leaveRequest = new LeaveRequest();
		leaveRequest.setDescription(leaveReqfrom.getDescription());
		leaveRequest.setEmployeeId(emp);
		leaveRequest.setLeaveDate(new Date(request.getParameter("leaveDate")));
		leaveRequest.setReturnDate(new Date(request.getParameter("returnDate")));
		leaveRequest.setLeaveTypeId(leaveType);
		leaveRequest.setNoOfDays(diffDays);
		leaveRequest.setStatus(0);
		LeaveRequestFactory.insert(leaveRequest);
		return map.findForward("continue");
	}
}
