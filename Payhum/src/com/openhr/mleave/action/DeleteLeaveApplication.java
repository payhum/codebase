package com.openhr.mleave.action;

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

import com.openhr.data.LeaveRequest;
import com.openhr.factories.LeaveRequestFactory;

public class DeleteLeaveApplication extends Action {
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
 		
		int employeeId   = json.getInt("employeeId");
		String leaveDate = json.getString("leaveDate");
  		List<LeaveRequest> leaveRequestList = LeaveRequestFactory.findByEmployeeId(employeeId);
  		LeaveRequest leaveReq = null;
  		for(LeaveRequest leaveRequest : leaveRequestList){
 			Date date = new Date(leaveRequest.getLeaveDate());
		    Format format = new SimpleDateFormat("MMM, dd yyyy");
		    String d = format.format(date).toString();
 			if(d.equals(leaveDate)){
 				leaveReq = leaveRequest;
				break;
			}
 		}
   		
   		LeaveRequestFactory.delete(leaveReq);
		return null;
	}
}
