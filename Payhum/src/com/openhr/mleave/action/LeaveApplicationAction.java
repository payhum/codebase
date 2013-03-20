package com.openhr.mleave.action;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.openhr.data.Leave;
import com.openhr.data.LeaveRequest;
import com.openhr.factories.EmployeeFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.LeaveTypeFactory;
import com.openhr.mleave.LeaveRequestForm;

public class LeaveApplicationAction extends Action {
	@Override
	public ActionForward execute(ActionMapping map, ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		BufferedReader reader = request.getReader();
		StringBuffer buffer = new StringBuffer();
		String line=null;
		while((line=reader.readLine()) != null){
			buffer.append(line);			
		}
		System.out.println("About to print json");
		System.out.println(buffer.toString());
		
		JSONArray json = JSONArray.fromObject(buffer.toString());
		LeaveRequest leaveRequest =new LeaveRequest();
		Collection<LeaveRequestForm> aCollection = JSONArray.toCollection(json, LeaveRequestForm.class);
		for(LeaveRequestForm lrFromJSON : aCollection){
			leaveRequest.setEmployeeId(EmployeeFactory.findByEmployeeId(lrFromJSON.getEmployeeId()).get(0));
			leaveRequest.setLeaveTypeId(LeaveTypeFactory.findById(lrFromJSON.getLeaveTypeId()).get(0));
			leaveRequest.setLeaveDate(new Date(lrFromJSON.getLeaveDate()));
			leaveRequest.setReturnDate(new Date(lrFromJSON.getReturnDate()));
			leaveRequest.setNoOfDays(lrFromJSON.getNoOfDays());
			leaveRequest.setDescription(lrFromJSON.getDescription());
			LeaveRequestFactory.insert(leaveRequest);
		}
		return map.findForward("");
	}
}
