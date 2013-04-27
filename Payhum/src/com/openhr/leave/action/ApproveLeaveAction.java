package com.openhr.leave.action;

import java.io.BufferedReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Employee;
import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;

public class ApproveLeaveAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws Exception {
		BufferedReader bf = request.getReader();
		StringBuffer sb = new StringBuffer();
		String line = null;
		while ((line = bf.readLine()) != null) {
			sb.append(line);
		}
		JSONObject json = JSONObject.fromObject(sb.toString());
		String approverId = json.getString("approverId");
		String stat = json.getString("status");
		int status = Integer.parseInt(stat);
		
		List<LeaveRequest> list = LeaveRequestFactory.findByEmployeeId(json.getInt("requestId"));
		LeaveRequest leaveRequest = null;
 		String str_date =  json.getString("leaveDate");
 		for(LeaveRequest l : list){
			Date date = new Date(l.getLeaveDate());
		    Format format = new SimpleDateFormat("MMM, dd yyyy");
		    String d = format.format(date).toString();
 			if(d.equals(str_date)){
 				leaveRequest = l;
				break;
			}
		}
 		
  		Employee approveEmployee = EmployeeFactory.findById(Integer.parseInt(approverId)).get(0);
 		leaveRequest.setStatus(status);
		LeaveRequestFactory.update(leaveRequest);
		if(status == 1){
			LeaveApproval leaveApprove = new LeaveApproval();
			leaveApprove.setApprovedbydate(new Date((Long) json
					.get("approvedbydate")));
	 		leaveApprove.setApproverId(approveEmployee);
			leaveApprove.setRequestId(leaveRequest);
			LeaveRequestFactory.insert(leaveApprove);
		}
		return null;
	}
}
